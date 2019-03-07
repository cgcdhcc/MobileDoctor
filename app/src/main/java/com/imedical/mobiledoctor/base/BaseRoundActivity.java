package com.imedical.mobiledoctor.base;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
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
import com.imedical.mobiledoctor.activity.WardRoundActivity;
import com.imedical.mobiledoctor.adapter.HisRecordsAdapter;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.entity.SeeDoctorRecord;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRoundActivity extends BaseActivity {
    private ListView mListViewRecord;
    private View ll_top;
    public BaseRoundActivity Instance;
    private HisRecordsAdapter mHisRecordsAdapter;
    private PopupWindow hisRecordPopWin;
    private List<SeeDoctorRecord> list = new ArrayList<SeeDoctorRecord>();
    private List<SeeDoctorRecord> listtemp = null;
    public int selectPos = 0;//WardRoundActivity和基类专用
    public TextView tv_record,tv_patSwitch;
    public int SWITHC_CODE = 101;
    public int Type=0;//默认是住院查房，1是视频门诊跳转

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Type = this.getIntent().getIntExtra("Type",0);
    }

    private void showWindow(View parent) {
        if (hisRecordPopWin != null) {
            WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            int xPos =(windowManager.getDefaultDisplay().getWidth() - hisRecordPopWin.getWidth())/2;
            hisRecordPopWin.showAsDropDown(parent, xPos, 0);
        }
    }


    protected void InitRecordListAndPatientList(BaseRoundActivity activity){
        this.Instance=activity;
        setInfos(Const.curPat.patName,(Const.curPat.bedCode==null?"":Const.curPat.bedCode)+"("+(Const.curPat.patRegNo==null?"":Const.curPat.patRegNo)+")");
        tv_record=(TextView) findViewById(R.id.tv_record);
        tv_patSwitch=(TextView) findViewById(R.id.tv_patSwitch);
        tv_patSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Instance.OnPatientSelected(null);
            }
        });
        if(Type==1)tv_patSwitch.setVisibility(View.GONE);
        ll_top= findViewById(R.id.ll_top);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.his_record_list_2, null);
        mListViewRecord = (ListView) view.findViewById(R.id.lv_data_list);
        mHisRecordsAdapter = new HisRecordsAdapter(BaseRoundActivity.this, list);
        mListViewRecord.setAdapter(mHisRecordsAdapter);
        mListViewRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                View ll_dialog=findViewById(R.id.ll_dialog);
                if(ll_dialog!=null){ll_dialog.setVisibility(View.GONE);}
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
                Instance.OnRecordSelected(null);
            }

        });
        InitHisRecord();
        tv_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View ll_dialog=findViewById(R.id.ll_dialog);
                if(ll_dialog!=null){ll_dialog.setVisibility(View.VISIBLE);}
                showWindow(ll_top);
            }
        });
        tv_record.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                tv_record.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int width=getWindowManager().getDefaultDisplay().getWidth() - 200;
                hisRecordPopWin = new PopupWindow(view, width, LinearLayout.LayoutParams.WRAP_CONTENT);
                hisRecordPopWin.setFocusable(true);
                hisRecordPopWin.setOutsideTouchable(true);
                hisRecordPopWin.setBackgroundDrawable(new BitmapDrawable());
                hisRecordPopWin.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        View ll_dialog=findViewById(R.id.ll_dialog);
                        if(ll_dialog!=null){ll_dialog.setVisibility(View.GONE);}
                    }
                });
            }
        });
    }

    private void InitHisRecord(){

        if(Const.SRecorderList!=null){//加载WordRoundActivit读取的数据。
            if(list.size()>0){
                tv_record.setText(SysManager.getAdmTypeDesc(list.get(0).admType) +list.get(0).admDate);
            }
            list.addAll(Const.SRecorderList);
            mHisRecordsAdapter = new HisRecordsAdapter(BaseRoundActivity.this, list);
            mListViewRecord.setAdapter(mHisRecordsAdapter);
            mHisRecordsAdapter.notifyDataSetChanged();
            if(Const.curSRecorder!=null){
                tv_record.setText(SysManager.getAdmTypeDesc(Const.curSRecorder.admType) +Const.curSRecorder.admDate);
                int i=0;
                for(SeeDoctorRecord temp:Const.SRecorderList){  //同步选择标签
                    if(temp.admId.equals(Const.curSRecorder.admId)){
                        selectPos=i;
                    }
                    i++;
                }
            }else if(list.size()>0){ //默认
                tv_record.setText(SysManager.getAdmTypeDesc(list.get(0).admType) +list.get(0).admDate);
            }else {
                showCustom("就诊历史记录NULL");
                tv_record.setText("无就诊历史记录");
        }

        }
    }


    public abstract void OnPatientSelected(PatientInfo p);
    public abstract void OnRecordSelected(SeeDoctorRecord sr);

}
