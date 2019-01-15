package com.imedical.mobiledoctor.activity.round;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.BusyManager;
import com.imedical.mobiledoctor.XMLservice.SysManager;
import com.imedical.mobiledoctor.adapter.HisRecordsAdapter;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.entity.SeeDoctorRecord;
import com.imedical.mobiledoctor.util.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRoundActivity extends BaseActivity {
    private ListView mListViewRecord;
    public BaseRoundActivity Instance;
    private HisRecordsAdapter mHisRecordsAdapter;
    private PopupWindow hisRecordPopWin;
    private List<SeeDoctorRecord> list = new ArrayList<SeeDoctorRecord>();
    private List<SeeDoctorRecord> listtemp = null;
    public int selectPos = 0;//WardRoundActivity和基类专用
    public View ll_record;
    public TextView tv_record;
    private SeeDoctorRecord mCurrectRecord = new SeeDoctorRecord();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void showWindow(View parent) {
        if (hisRecordPopWin != null) {
            WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            hisRecordPopWin.showAsDropDown(parent, 0, 0);
        }
    }

    protected void InitRecordList(BaseRoundActivity activity){
        this.Instance=activity;
        setInfos(Const.curPat.patName,Const.curPat.bedCode+"床("+Const.curPat.patRegNo+")");
        ll_record=findViewById(R.id.ll_record);
        tv_record=(TextView) findViewById(R.id.tv_record);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.his_record_list, null);
        mListViewRecord = (ListView) view.findViewById(R.id.lv_data_list);
        mHisRecordsAdapter = new HisRecordsAdapter(BaseRoundActivity.this, list);
        mListViewRecord.setAdapter(mHisRecordsAdapter);
        mListViewRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                SeeDoctorRecord sr = list.get(position);
                if (hisRecordPopWin != null) {
                    hisRecordPopWin.dismiss();
                }
                Const.curPat.inDate=sr.admDate;
                Const.curPat.admId = sr.admId;
                Const.curPat.admType= sr.admType;
                Const.curSRecorder=sr;
                tv_record.setText(SysManager.getAdmTypeDesc(sr.admType) +sr.admDate);
                selectPos = position;
                mHisRecordsAdapter.notifyDataSetChanged();
                Instance.OnRecordSelected(sr);
            }

        });
        mCurrectRecord.admDate =  Const.curPat.inDate;
        mCurrectRecord.admDept =  Const.curPat.departmentName;
        mCurrectRecord.admId =  Const.curPat.admId;
        mCurrectRecord.admType =  Const.curPat.admType;
        Const.curSRecorder=new SeeDoctorRecord();
        Const.curSRecorder.admId=  Const.curPat.admId;
        list.add(mCurrectRecord);
        ll_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWindow(v);
            }
        });
        LoadHisRecord();
        ll_record.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // TODO Auto-generated method stub
                ll_record.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int width=ll_record.getWidth();
                hisRecordPopWin = new PopupWindow(view, width, LinearLayout.LayoutParams.WRAP_CONTENT);
                hisRecordPopWin.setFocusable(true);
                hisRecordPopWin.setOutsideTouchable(true);
                hisRecordPopWin.setBackgroundDrawable(new BitmapDrawable());
            }
        });
    }
    private void LoadHisRecord() {
        new Thread() {
            @Override
            public void run() {
                try {
                    listtemp = BusyManager.listSeeDoctorRecord(Const.curPat.admId);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (listtemp == null) {
                        listtemp = new ArrayList<SeeDoctorRecord>();
                    } else {
                        list.addAll(listtemp);
                    }
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            //		mListData.clear();
                            mHisRecordsAdapter = new HisRecordsAdapter(BaseRoundActivity.this, list);
                            mListViewRecord.setAdapter(mHisRecordsAdapter);
                            mHisRecordsAdapter.notifyDataSetChanged();
                            if(list.size()>0){
                                tv_record.setText(SysManager.getAdmTypeDesc(list.get(0).admType) +list.get(0).admDate);
                            }
                        }

                    });
                }
            }
        }.start();
    }

    public abstract void OnPatientSelected(PatientInfo p);
    public abstract void OnRecordSelected(SeeDoctorRecord sr);

}
