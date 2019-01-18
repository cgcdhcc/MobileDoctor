package com.imedical.mobiledoctor.activity.round.detail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.LisReportItemManager;
import com.imedical.mobiledoctor.adapter.AdapterLisCompare;
import com.imedical.mobiledoctor.adapter.AdapterLisDetail;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.LisReportItem;
import com.imedical.mobiledoctor.entity.LoginInfo;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.util.LogMe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LisHis_DetailActivity extends BaseActivity
{

    private PatientInfo mPatientCurrSelected;
    private String mInfo = "程序错误！";
    private ListView mListViewLisCompare;
    public List<LisReportItem> mDataListLisCompare = new ArrayList<LisReportItem>();
    private AdapterLisCompare mAdapterLisCompare;
    public List<String> dateList = new ArrayList<String>();
    private ImageView iv_no_re ;
    private  TextView  tv_title;
    private LoginInfo mLogin = null;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page4_lis_his);
        InitViews();

    }
     private void InitViews() {
         Window window = this.getWindow();
         WindowManager m = getWindowManager();
         WindowManager.LayoutParams params = window.getAttributes();
         Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
         params.width=d.getWidth();
         this.getWindow().setAttributes(params);
         tv_title=(TextView)findViewById(R.id.tv_title);
         mLogin= Const.loginInfo;
         if(mLogin==null){
             return;
         }
         //数据
         Intent i = getIntent();
         String arcItemId = i.getStringExtra("arcItemId");
         mPatientCurrSelected = (PatientInfo)i.getSerializableExtra("PatientInfo");
         tv_title.setText("历次记录 ("+mPatientCurrSelected.patName+")");
         //对比列表
         View tv_back=findViewById(R.id.iv_left);
         tv_back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });
         iv_no_re = (ImageView)findViewById(R.id.iv_no_re);
         mListViewLisCompare = (ListView)findViewById(R.id.compareList);
         mAdapterLisCompare = new AdapterLisCompare(LisHis_DetailActivity.this);
         mListViewLisCompare.setAdapter(mAdapterLisCompare);
         loadDataThreadCompare(mLogin.userCode,arcItemId,mPatientCurrSelected.admId);
     }

    private void loadDataThreadCompare(final String userCode,
                                       final String arcItemId, final String admId) {
        showProgress();
        mDataListLisCompare.clear();
        dateList.clear();
        mAdapterLisCompare.notifyDataSetChanged();
        new Thread() {
            public void run() {
                try {
                    Looper.prepare();
                    Map map = new HashMap();
                    map.put("userCode", userCode);
                    map.put("admId",admId);
                    map.put("arcItemId", arcItemId);
                    List<LisReportItem> list = LisReportItemManager.listLisReportItemCompare(map);
                    mDataListLisCompare.addAll(list);
                } catch (Exception e) {
                    e.printStackTrace();
                    mInfo = e.getMessage();
                } finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(mDataListLisCompare.size()==0){
                                mListViewLisCompare.setVisibility(View.GONE);
                                iv_no_re.setVisibility(View.VISIBLE);
                            }else{
                                iv_no_re.setVisibility(View.GONE);
                                mListViewLisCompare.setVisibility(View.VISIBLE);
                                createHeader();
                                mAdapterLisCompare.notifyDataSetChanged();
                            }
                            dismissProgress();

                        }
                    });

                }
            }
        }.start();
    }

    private void createHeader() {
        LinearLayout headLayout = (LinearLayout)findViewById(R.id.header);
        headLayout.removeAllViews();
        //根据数据返回的日期创建表头
        for (int i = 0; i < mDataListLisCompare.size(); i ++) {
            for (int j = 0; j < mDataListLisCompare.get(i).resultList.size(); j ++) {
                String date = mDataListLisCompare.get(i).resultList.get(j).reportDate;
                if(!dateList.contains(date) && !date.equals("参考范围")){
                    dateList.add(date);
                }else{
                    continue;
                }
            }
        }
        dateList.add("参考范围");
        LogMe.d("mark","日期列表的长度为" + dateList.size());
        TextView tv_name = createItemTextView("项目名称");
        tv_name.setTextColor(getResources().getColor(R.color.bg_commom));
        tv_name.getPaint().setFakeBoldText(true);
        headLayout.addView(tv_name);
        for(int i = 0; i < dateList.size(); i ++) {
            String txt = dateList.get(i) ;
            TextView tv_date =  createItemTextView(txt);
            tv_date.setTextColor(getResources().getColor(R.color.bg_commom));
            headLayout.addView(tv_date);
        }
    }

    private TextView createItemTextView(String txt) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(1, 0, 1, 0);
        TextView tv_name = new TextView(this);
//		tv_name.setTextSize(18);
        tv_name.setText(txt);
        tv_name.setWidth(300);
        tv_name.setLayoutParams(lp);
        tv_name.setGravity(Gravity.CENTER);
        tv_name.setBackgroundColor(Color.WHITE);
        return tv_name;
    }
}
