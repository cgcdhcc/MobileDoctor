package com.imedical.mobiledoctor.activity.round;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.DiagnosisManager;
import com.imedical.mobiledoctor.adapter.DiagnosisAdapter;
import com.imedical.mobiledoctor.base.BaseRoundActivity;
import com.imedical.mobiledoctor.entity.Diagnosis;
import com.imedical.mobiledoctor.entity.LoginInfo;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.entity.SeeDoctorRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiagnosisActivity extends BaseRoundActivity {
    private String mInfo = " test ";
    private TextView tv_hisline;
    private DiagnosisAdapter mAdapter_now,mAdapter_his;
    private List<Diagnosis> mListData_now = new ArrayList<Diagnosis>();
    private List<Diagnosis> mListData_his = new ArrayList<Diagnosis>();
    private ListView mListView_now,mListView_his;
    private LoginInfo mLogin = null;
    private View RootView=null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.page2_diagnosis_activity);
        InitViews();
        loadData();
        InitRecordListAndPatientList(DiagnosisActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == SWITHC_CODE) {
            loadData();
        }
        setInfos(Const.curPat.patName,(Const.curPat.bedCode==null?"":Const.curPat.bedCode)+"("+(Const.curPat.patRegNo==null?"":Const.curPat.patRegNo)+")");//更新姓名，床号
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void OnPatientSelected(PatientInfo p) {
        Intent it0 =new Intent(DiagnosisActivity.this,PatientListActivity.class);
        startActivityForResult(it0, SWITHC_CODE);
    }
    @Override
    public void OnRecordSelected(SeeDoctorRecord sr) {
        loadData();
    }
    private void InitViews() {
        tv_hisline=(TextView) findViewById(R.id.tv_hisline);
        RootView=this.findViewById(R.id.rootView);
        mLogin = Const.loginInfo;
        setTitle("诊断记录");
        mListView_now = (ListView) findViewById(R.id.lv_data_now);
        mAdapter_now = new DiagnosisAdapter(DiagnosisActivity.this, mListData_now);
        mListView_now.setAdapter(mAdapter_now);
        mListView_now.setCacheColorHint(Color.TRANSPARENT);
        mListView_his = (ListView) findViewById(R.id.lv_data_his);
        mAdapter_his = new DiagnosisAdapter(DiagnosisActivity.this, mListData_his);
        mListView_his.setAdapter(mAdapter_his);
        mListView_his.setCacheColorHint(Color.TRANSPARENT);
        mListView_his.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> view, View arg1,int position, long arg3) {
                Diagnosis d = mListData_his.get(position);
//                toDiagnosisActivity(d, false);
            }
        });
    }

    private void loadData() {
        if (Const.curPat == null) {
            return;
        }
        showNodata(false,RootView);
        mListData_now.clear();
        mListData_his.clear();
        loadDataThreadCurr();
        loadDataThreadHis();
    }

    private void loadDataThreadCurr() {
        new Thread() {
            public void run() {
                Message message = new Message();
                try {
                    Looper.prepare();
                    Map map = new HashMap();
                    map.put("userCode", mLogin.userCode);
                    map.put("admId", Const.curPat.admId);
                    // 缓存查询的数据
                    List<Diagnosis> list = DiagnosisManager.listDiagnosisCurr(map);
                    mListData_now.clear();
                    if(list!=null) {
                        message.obj = list;
                        message.what = 0;
                    }else{
                        message.what = -1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mInfo = e.getMessage();
                    message.what = -1;
                }
                finally {
                    myHandler.sendMessage(message);
                }
            }
        }.start();
    }

    private void loadDataThreadHis() {
        new Thread() {
            public void run() {
                Message message = new Message();
                try {
                    Looper.prepare();
                    Map map = new HashMap();
                    map.put("userCode", mLogin.userCode);
                    map.put("admId", Const.curPat.admId);
                    // 缓存查询的数据
                    List<Diagnosis> list = DiagnosisManager.listDiagnosisHis(map);
                    if(list!=null) {
                        message.obj = list;
                        message.what = 1;
                    }else{
                        message.what = -1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mInfo = e.getMessage();
                    message.what = -1;
                } finally {
                    myHandler.sendMessage(message);
                }
            }
        }.start();
    }

    Handler myHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:

                    List tempList = (List) msg.obj;
                    mListData_now.clear();
                    mListData_now.addAll(tempList);
                    mAdapter_now = new DiagnosisAdapter(DiagnosisActivity.this, mListData_now);
                    mListView_now.setAdapter(mAdapter_now);
                    mListView_now.invalidate();
                    mAdapter_now.notifyDataSetChanged();
                    break;
                case 1:
                    List list = (List) msg.obj;
                    mListData_his.clear();
                    mListData_his.addAll(list);
                    mAdapter_his = new DiagnosisAdapter(DiagnosisActivity.this, mListData_his);
                    mListView_his.setAdapter(mAdapter_his);
                    mListView_his.invalidate();
                    mAdapter_his.notifyDataSetChanged();
                    if(list.size()>0){
                        tv_hisline.setVisibility(View.VISIBLE);
                    }
                    dismissProgress();
                    break;
                default:
                    showNodata(true,RootView);
                    dismissProgress();
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
