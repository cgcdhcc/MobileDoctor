package com.imedical.mobiledoctor.activity.frg_1;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;


import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.BusyManager;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.entity.LoginInfo;
import com.imedical.mobiledoctor.entity.ReportData;
import com.imedical.mobiledoctor.util.DateTimePickDialogUtil;
import com.imedical.mobiledoctor.util.DateUtil;
import com.imedical.mobiledoctor.util.MyCallback;
import com.imedical.mobiledoctor.widget.ListViewPull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@SuppressLint("ValidFragment")
public class CriticalValueActivity extends BaseActivity implements OnClickListener {
    private String conStatus = "0";
    private String mInfo = " test ";
    private long mIndexSelected = -1;
    private ListViewPull mListView;
    public ArrayAdapter<String> adapter;
    private List<ReportData> mListData = new ArrayList<ReportData>();
    private String mConLoad_4_pages = "";
    private AdapterPat mAdapterPat;
    private TextView btn_startDate;
    private TextView btn_endDate,radio_V,radio_V_line,radio_D,radio_D_line;
    private ImageView iv_left;
    private LoginInfo mLogin;
    private String today, pretoday;
    private View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.critical_value_activity);
        this.setTitle("危急值管理");
        mLogin = Const.loginInfo;
        if (mLogin == null) {
            return;
        }
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
        if (mLogin != null) {
            v = findViewById(R.id.ll_nodata);
            v.setVisibility(View.VISIBLE);
            initUI();
            resetData();
            loadData();
        }

    }

    @Override
    public void onClick(View v) {
        int diff = getDatediff();
        if (diff == 0) {
            diff = 1;
        }
        switch (v.getId()) {
            case R.id.radio_V:
                radio_V.setTextColor(getResources().getColor(R.color.mobile_blue));
                radio_V_line.setBackground(this.getDrawable(R.color.mobile_blue));
                radio_D.setTextColor(getResources().getColor(R.color.gray));
                radio_D_line.setBackground(this.getDrawable(R.color.white));
                conStatus = "0";// 在用
                doQuery();
                break;
            case R.id.radio_D:
                radio_V.setTextColor(getResources().getColor(R.color.gray));
                radio_V_line.setBackground(this.getDrawable(R.color.white));
                radio_D.setTextColor(getResources().getColor(R.color.mobile_blue));
                radio_D_line.setBackground(this.getDrawable(R.color.mobile_blue));
                conStatus = "1";// 在用
                doQuery();
                break;
        }

    }
//
    private boolean isMax(String startDate, String endDate) {
        String dateStr1 = startDate;
        String dateStr2 = endDate;
        Calendar time1 = DateTimePickDialogUtil.getCalendarByInintData(dateStr1, "yyyy-MM-dd");
        Calendar time2 = DateTimePickDialogUtil.getCalendarByInintData(dateStr2, "yyyy-MM-dd");
        if (time2.compareTo(time1) < 0) {
            return true;
        } else {
            return false;
        }
    }
    private int getDatediff() {
        String dateStr1 = btn_startDate.getText().toString();
        String dateStr2 = btn_endDate.getText().toString();
        long time1 = DateTimePickDialogUtil.getCalendarByInintData(dateStr1, "yyyy-MM-dd").getTimeInMillis();
        long time2 = DateTimePickDialogUtil.getCalendarByInintData(dateStr2, "yyyy-MM-dd").getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        int diff = 0;
        if (between_days >= 0 && between_days <= 7) {
            diff = (int) between_days;
        } else {
            diff = 7;
        }
        return diff;
    }

    private void resetData() {
        mConLoad_4_pages = "";
        mListView.resetStatu();
        mListView.startLoading();
        mListData.clear();
        mAdapterPat.notifyDataSetChanged();
    }

    public void initUI() {
        btn_startDate = (TextView) findViewById(R.id.btn_startDate);
        btn_endDate = (TextView) findViewById(R.id.btn_endDate);
        radio_V_line=(TextView) findViewById(R.id.radio_V_line);
        radio_D_line=(TextView) findViewById(R.id.radio_D_line);
        btn_startDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new DateTimePickDialogUtil(CriticalValueActivity.this).datePicKDialoBefor(btn_startDate.getText().toString(), today, new MyCallback() {
                    @Override
                    public void onCallback(Object date) {
                        btn_startDate.setText(date.toString());
                        String endDate = btn_endDate.getText().toString();
                        if (isMax(date.toString(), endDate)) {
                            showCustom("时间区错误");
                            btn_endDate.setText(DateUtil.getDateStr("yyyy-MM-dd", btn_startDate.getText().toString(), getDatediff()));
                        }
                    }
                }).show();
            }
        });
        btn_endDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new DateTimePickDialogUtil(CriticalValueActivity.this).datePicKDialoBefor(btn_endDate.getText().toString(), today, new MyCallback() {
                    @Override
                    public void onCallback(Object date) {
                        btn_endDate.setText(date.toString());
                        String startDate = btn_startDate.getText().toString();
                        if (isMax(startDate, date.toString())) {
                            btn_startDate.setText(DateUtil.getDateStr("yyyy-MM-dd", btn_endDate.getText().toString(), -1 * getDatediff()));
                            showCustom("时间区错误");
                        }else {
                            doQuery();
                        }
                    }
                }).show();
            }
        });
        today = DateUtil.getDateToday("yyyy-MM-dd");
        pretoday = DateUtil.getDateTodayBefore("yyyy-MM-dd", -7);
        btn_startDate.setText(pretoday);
        btn_endDate.setText(today);
        radio_V = (TextView) findViewById(R.id.radio_V);radio_V.setOnClickListener(this);
        radio_D = (TextView) findViewById(R.id.radio_D);radio_D.setOnClickListener(this);
        mListView = (ListViewPull) findViewById(R.id.lv_data);
        mAdapterPat = new AdapterPat();
        mListView.setAdapter(mAdapterPat);
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
                mIndexSelected = pos;
//				view.setBackgroundResource(Color.parseColor("#F7F7F7"));
                mAdapterPat.notifyDataSetChanged();
                ReportData reportdata = mListData.get(pos);
                postDataAsyn(view, reportdata);
            }

        });
        mListView.setXListViewListener(new ListViewPull.IXListViewListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onClickLoadMore() {
                loadData();
            }

        });
        mListView.setOnScrollListener(new OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case OnScrollListener.SCROLL_STATE_IDLE:
                        if (view.getLastVisiblePosition() == (view.getCount() - 1)//滚动到底部
                                && mConLoad_4_pages != null) {//还有新的回复
                            loadData();
                        }
                        break;
                    case OnScrollListener.SCROLL_STATE_FLING:
                        // Log.v("开始滚动：SCROLL_STATE_FLING");
                        break;
                }
            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
            }

        });

    }


private void loadData() {
    if( mConLoad_4_pages==null || !mListView.isThereMore() ){
        mListView.endLoad(false);
        return ;
    }
    final String startDate = btn_startDate.getText().toString();
    final String endDate   = btn_endDate.getText().toString();
    showProgress();
    new Thread(){
        List<ReportData> tempList ;
        public void run() {
            try {
                tempList = BusyManager.listReportData(mLogin.userCode, mLogin.defaultDeptId, startDate, endDate, conStatus, mConLoad_4_pages);
            } catch (Exception e) {
                mInfo = e.getMessage();
                e.printStackTrace();
            }finally {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        if(tempList!=null){
                            if(tempList.size()>0){
                                v.setVisibility(View.GONE);
                                mConLoad_4_pages = tempList.get(tempList.size()-1).conLoad ;
                                mListData.addAll(tempList);
                                mAdapterPat.notifyDataSetChanged();
                                if(mConLoad_4_pages==null){
                                    mListView.endLoad(false);
                                }else{
                                    mListView.endLoad(true);
                                }
                            }else{
                                mListView.endLoad(false);
                            }
                        }else{
                            showCustom( "内部错误"+mInfo);
                        }
                    }
                });
            }
        };
    }.start();

}

    public class AdapterPat extends BaseAdapter {
        private LayoutInflater mInflater;
        ViewHolder holder = null;

        public AdapterPat() {
            this.mInflater = LayoutInflater.from(CriticalValueActivity.this);
        }

        public int getCount() {
            return mListData.size();
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ReportData p = mListData.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(CriticalValueActivity.this).inflate(R.layout.critical_value_adpter, null);

                holder = new ViewHolder();
                holder.tv_appDocName = (TextView) convertView.findViewById(R.id.tv_appDocName);
                holder.tv_arcimDesc = (TextView) convertView.findViewById(R.id.tv_arcimDesc);
                holder.tv_authDateTime = (TextView) convertView.findViewById(R.id.tv_authDateTime);
                holder.tv_patName = (TextView) convertView.findViewById(R.id.tv_patName);
                holder.tv_reportMemo = (TextView) convertView.findViewById(R.id.tv_reportMemo);
                holder.tv_specName = (TextView) convertView.findViewById(R.id.tv_specName);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == mIndexSelected) {
                convertView.setBackgroundResource(R.color.light_gray);
            } else {
//                convertView.setBackgroundResource(R.drawable.bg_white_gray_add);
            }

            holder.tv_appDocName.setText(p.appDocName);
            holder.tv_arcimDesc.setText(p.arcimDesc);
            holder.tv_authDateTime.setText(p.authDateTime);
            holder.tv_patName.setText(p.patName);
            holder.tv_reportMemo.setText(p.reportMemo);
            holder.tv_specName.setText(p.specName);

            return convertView;
        }


        public class ViewHolder {

            public TextView tv_specName;
            public TextView tv_reportMemo;
            public TextView tv_labNo;
            public TextView tv_authDateTime;
            public TextView tv_arcimDesc;
            public TextView tv_appDocName;
            public TextView tv_patName;
        }
    }
    private void postDataAsyn(final View v, final ReportData r) {
        new Thread() {
            BaseBean b;
            public void run() {
                try {
                    b = BusyManager.postReadInfo(mLogin.userCode, mLogin.defaultDeptId, r.ordItemId);
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (b == null || !"0".equals(b.getResultCode())) {
                                showCustom("标记危急值为已读的操作失败，再次点击可重新标记！");
                            }
                        }
                    });
                }
            };
        }.start();
//        showLisDig(r);
    }
//
//    public void showLisDig(ReportData r) {
//        Intent intent = new Intent(PageFragReportDataActivity.this, PopupLisViewActivity.class);
//        intent.putExtra("reportdata", r);
//        startActivity(intent);
//    }
//
    private void doQuery() {
        resetData();
        loadData();
    }



}

