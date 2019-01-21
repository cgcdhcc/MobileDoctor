package com.imedical.mobiledoctor.activity.round;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.RisManager;
import com.imedical.mobiledoctor.XMLservice.SettingManager;
import com.imedical.mobiledoctor.adapter.AdapterRis;
import com.imedical.mobiledoctor.base.BaseRoundActivity;
import com.imedical.mobiledoctor.entity.LoginInfo;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.entity.RisReport;
import com.imedical.mobiledoctor.entity.RisReportList;
import com.imedical.mobiledoctor.entity.SeeDoctorRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RisActivity extends BaseRoundActivity implements View.OnClickListener {
    private LoginInfo mLogin;
    public List<RisReportList> mListData ;
    public List<List<RisReport>> mListData2;
    private String mInfo = "test";
    private ListView risList;
    private AdapterRis mAdapter;
    private ImageView ivNoInfo;
    private LinearLayout llNoInfo;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.page5_ris_activity);
        InitViews();
        InitRecordListAndPatientList(RisActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == SWITHC_CODE) {
            loadDataThread();
        }
        setInfos(Const.curPat.patName,(Const.curPat.bedCode==null?"":Const.curPat.bedCode)+"床("+(Const.curPat.patRegNo==null?"":Const.curPat.patRegNo)+")");//更新姓名，床号
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void OnPatientSelected(PatientInfo p) {
        Intent it0 =new Intent(RisActivity.this,PatientListActivity.class);
        startActivityForResult(it0, SWITHC_CODE);
    }
    @Override
    public void OnRecordSelected(SeeDoctorRecord sr) {
        loadDataThread();
    }

    private void InitViews() {
        mLogin = Const.loginInfo;
        mListData = new ArrayList<RisReportList>();
        mListData2 = new ArrayList<List<RisReport>>();
        llNoInfo = (LinearLayout)findViewById(R.id.ll_no_re);
        ivNoInfo = (ImageView)findViewById(R.id.iv_no_re);
        risList = (ListView)findViewById(R.id.ris_list);
        mAdapter = new AdapterRis(this);
        risList.setAdapter(mAdapter);
        setTitle("检查报告");
        loadDataThread();
    }

    private void loadDataThread() {
        mListData.clear();
        mAdapter.notifyDataSetChanged();
        new Thread() {
            public void run() {
                Message message = new Message();
                try {
                    Map map = new HashMap();
                    map.put("userCode", mLogin.userCode);
                    map.put("admId",Const.curPat.admId);
                    List<RisReportList> list = RisManager.listRisReportList(map);
                    if (list==null||list.size() == 0) {
                        message.what = 3;
                        myHandler.sendMessage(message);
                    } else {
                        mListData.clear();
                        mListData.addAll(list);
                        loadDataThread2();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mInfo = e.getMessage();
                    message.what = 3;
                    myHandler.sendMessage(message);
                }
            }
        }.start();
    }

    private void loadDataThread2() {
        mListData2.clear();
        final String netWorkType = SettingManager.getNetWorkType(this);
        new Thread() {
            public void run() {
                Message message = new Message();
                final List<List<RisReport>> tmpList = new ArrayList<List<RisReport>>();
                try {
                    for (int i = 0; i < mListData.size(); i ++) {
                        Map map = new HashMap();
                        map.put("userCode", mLogin.userCode);
                        map.put("admId", Const.curPat.admId);
                        map.put("studyId", mListData.get(i).studyId);
                        map.put("netWorkType", netWorkType);
                        try {
                            List<RisReport> list = RisManager.listRisReport(map);
                            if (list == null) {
                                RisReport ris = new RisReport();
                                List<RisReport> l = new ArrayList<RisReport>();
                                l.add(ris);
                                tmpList.add(l);
                            } else {
                                tmpList.add(list);
                            }
                        }catch (Exception e) {
                            mInfo = e.getMessage();
                            if(mInfo!=null&&mInfo.equals("检查报告不存在!")) {
                                RisReport ris = new RisReport();
                                List<RisReport> l = new ArrayList<RisReport>();
                                l.add(ris);
                                tmpList.add(l);
                            } else {
                                message.what = 3;
                            }
                        }
                    }
                    message.what = 2;
                    message.obj = tmpList;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    myHandler.sendMessage(message);
                }
            }
        }.start();

    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case 1:
                    llNoInfo.setVisibility(View.VISIBLE);
                    ivNoInfo.setVisibility(View.VISIBLE);
                    risList.setVisibility(View.GONE);
                    break;
                case 2:
                    List tempList2 = (ArrayList<List<RisReport>>)msg.obj;
                    mListData2.addAll(tempList2);
                    if(mListData!=null&&mListData.size()>0){
                        llNoInfo.setVisibility(View.GONE);
                        ivNoInfo.setVisibility(View.GONE);
                        risList.setVisibility(View.VISIBLE);
                    }else{
                        llNoInfo.setVisibility(View.VISIBLE);
                        ivNoInfo.setVisibility(View.VISIBLE);
                        risList.setVisibility(View.GONE);
                    }
                    mAdapter.notifyDataSetChanged();
                    dismissProgress();
                    break;

                case 3:
                    llNoInfo.setVisibility(View.VISIBLE);
                    ivNoInfo.setVisibility(View.VISIBLE);
                    risList.setVisibility(View.GONE);
                    dismissProgress();
                    break;
                default :
                    showCustom(mInfo);
                    dismissProgress();
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {

    }
}
