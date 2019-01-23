package com.imedical.mobiledoctor.activity.round;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.RisManager;
import com.imedical.mobiledoctor.XMLservice.SettingManager;
import com.imedical.mobiledoctor.adapter.AdapterRis;
import com.imedical.mobiledoctor.base.BaseRoundActivity;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.entity.RisReportList;
import com.imedical.mobiledoctor.entity.SeeDoctorRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RisActivity extends BaseRoundActivity implements View.OnClickListener {
    public List<RisReportList> mListData;
    private ListView risList;
    private AdapterRis mAdapter;

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
        setInfos(Const.curPat.patName, (Const.curPat.bedCode == null ? "" : Const.curPat.bedCode) + "床(" + (Const.curPat.patRegNo == null ? "" : Const.curPat.patRegNo) + ")");//更新姓名，床号
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void OnPatientSelected(PatientInfo p) {
        Intent it0 = new Intent(RisActivity.this, PatientListActivity.class);
        startActivityForResult(it0, SWITHC_CODE);
    }

    @Override
    public void OnRecordSelected(SeeDoctorRecord sr) {
        loadDataThread();
    }

    private void InitViews() {
        mListData = new ArrayList<RisReportList>();
        risList = (ListView) findViewById(R.id.lv_data);
        mAdapter = new AdapterRis(this);
        risList.setAdapter(mAdapter);
        setTitle("检查报告");
        loadDataThread();
    }

    private void loadDataThread() {
        showProgress();
        final String netWorkType = SettingManager.getNetWorkType(this);
        new Thread() {
            String msg = "";
            List<RisReportList> list;

            public void run() {
                try {
                    Map map = new HashMap();
                    map.put("userCode", Const.loginInfo.userCode);
                    map.put("admId", Const.curPat.admId);
                    map.put("admId", Const.curPat.admId);
                    map.put("netWorkType", netWorkType);
                    list = RisManager.listRisReportList(map);
                } catch (Exception e) {
                    msg = e.getMessage();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mListData.clear();
                        if (list != null) {
                            mListData.addAll(list);
                            if (mListData.size() > 0) {
                                showNodataInListView(false);
                            } else {
                                showNodataInListView(true);
                            }
                        } else {
                            showToast(msg);
                        }
                        mAdapter.notifyDataSetChanged();
                        dismissProgress();
                    }
                });
            }
        }.start();
    }

    @Override
    public void onClick(View view) {

    }
}
