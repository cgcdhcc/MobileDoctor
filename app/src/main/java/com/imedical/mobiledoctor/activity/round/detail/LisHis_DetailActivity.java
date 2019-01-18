package com.imedical.mobiledoctor.activity.round.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.LisReportItemManager;
import com.imedical.mobiledoctor.adapter.AdapterLisDetail;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.LisReportItem;
import com.imedical.mobiledoctor.entity.LoginInfo;
import com.imedical.mobiledoctor.entity.PatientInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lis_DetailActivity extends BaseActivity implements
        View.OnClickListener, AdapterView.OnItemClickListener {

    public PatientInfo mPatientCurrSelected;
    private String mInfo = "程序错误！";
    private LoginInfo mLogin = null;
    // 检验明细
    private ListView risListView;
    public List<LisReportItem> mDataListLis = new ArrayList<LisReportItem>();
    private AdapterLisDetail mAdapter = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page4_lis_detail);
        mLogin= Const.loginInfo;
        if(mLogin==null){
            return;
        }
        View iv_left=findViewById(R.id.iv_left);
        final TextView tv_record=(TextView) findViewById(R.id.tv_record);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent i = getIntent();
        final String ordLabNo = i.getStringExtra("ordLabNo");
        if (mPatientCurrSelected == null) {
            mPatientCurrSelected = (PatientInfo)i.getSerializableExtra("PatientInfo");
            tv_record.setText(mPatientCurrSelected.patName+"-检验结果");
        }
        risListView = (ListView) findViewById(R.id.lv_lis_detail);
        mAdapter = new AdapterLisDetail(this);
        risListView.setAdapter(mAdapter);
        risListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                try {
                    final LisReportItem lr = mDataListLis.get(pos);
                    String range = lr.naturalRange;
                    String[] array = range.split("-");
                    Float maxY = null, bottom = null;
                    if (array.length == 2) {
                        maxY = Float.parseFloat(array[1]);
                        bottom = Float.parseFloat(array[0]);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putFloat("top", maxY);
                    bundle.putFloat("bottom", bottom);
                    bundle.putString("userCode", mLogin.userCode);
                    bundle.putString("admId", mPatientCurrSelected.admId);
                    bundle.putString("ordLabNo", ordLabNo);
                    bundle.putString("itemCode",mDataListLis.get(pos).itemCode);
                    // 显示坐标图
//                    Intent intent = new Intent(LisDetailListActivity.this,CurvedLineActivity.class);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });

        loadLisReportItemsThread(ordLabNo);

    }

    private void loadLisReportItemsThread(final String no){
        showProgress();
        new Thread() {
            private String mInfo;
            List<LisReportItem> tmpList ;
            public void run() {
                try {
                    Map map = new HashMap();
                    map.put("userCode",mLogin.userCode);
                    map.put("admId",mPatientCurrSelected.admId);
                    map.put("ordLabNo", no);
                    tmpList = LisReportItemManager.listLisReportItem(map);
                } catch (Exception e) {
                    e.printStackTrace();
                    mInfo = e.getMessage();
                } finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dismissProgress();
                            if(tmpList!=null){
                                mDataListLis.clear();
                                mDataListLis.addAll(tmpList);
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        }.start();
    }
    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
