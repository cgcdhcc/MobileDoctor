package com.imedical.mobiledoctor.activity.frg_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.BusyManager;
import com.imedical.mobiledoctor.activity.frg_1.detail.FormAntiDrugDetailActivity;
import com.imedical.mobiledoctor.activity.frg_1.detail.FormConsultInfoActivity;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.AntAppData;
import com.imedical.mobiledoctor.entity.ArcimItem;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.util.DateTimePickDialogUtil;
import com.imedical.mobiledoctor.util.DateUtil;
import com.imedical.mobiledoctor.util.DialogUtil;
import com.imedical.mobiledoctor.util.MyCallback;
import com.imedical.mobiledoctor.widget.ListViewPull;

import java.util.ArrayList;
import java.util.List;

public class AntActivity extends BaseActivity implements OnClickListener {
    private String mInfo = " test ";
    private ListViewPull mListView;
    private List<AntAppData> mListData = new ArrayList<AntAppData>();
    // 点击加载更多
    private String mConLoad_4_pages = "";
    private AdapterPat mAdapterPat;
    private TextView tv_startDate;
    private TextView tv_endDate;
    private View btn_reject, btn_agree, view_operation;
    private String stutaString = "A";
    private View ll_nodata;
    private TextView tv_checkall;
    public TextView tv_isgoing, tv_isgoing_line, tv_hasfinish, tv_hasfinish_line;
    public boolean isCheckAll = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frg1_ant);
        setTitle("抗生素管理");
        if (Const.loginInfo == null) {
            return;
        }
        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        doCheck();
    }

    // 数据重置
    private void resetData() {
        mConLoad_4_pages = "";
        mListView.resetStatu();
        mListView.startLoading();
        mListData.clear();
        mAdapterPat.notifyDataSetChanged();
    }

    public void initUI() {
        ll_nodata = findViewById(R.id.ll_nodata);
        tv_isgoing = (TextView) findViewById(R.id.tv_isgoing);
        tv_isgoing.setOnClickListener(this);

        tv_isgoing_line = (TextView) findViewById(R.id.tv_isgoing_line);

        tv_hasfinish = (TextView) findViewById(R.id.tv_hasfinish);
        tv_hasfinish.setOnClickListener(this);

        view_operation = findViewById(R.id.view_operation);

        tv_hasfinish_line = (TextView) findViewById(R.id.tv_hasfinish_line);
        tv_endDate = (TextView) findViewById(R.id.tv_endDate);
        tv_startDate = (TextView) findViewById(R.id.tv_startDate);
        tv_startDate.setText(DateUtil.getDateTodayBefore("yyyy-MM-dd", -3));
        tv_endDate.setText(DateUtil.getDateToday("yyyy-MM-dd"));
        tv_startDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new DateTimePickDialogUtil(AntActivity.this).datePicKDialoBefor(tv_startDate.getText().toString(), DateUtil.getDateToday(null), new MyCallback() {
                    @Override
                    public void onCallback(Object date) {
                        tv_startDate.setText(date.toString());
                        doQuery();
                    }
                }).show();
            }
        });
        tv_endDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new DateTimePickDialogUtil(AntActivity.this).datePicKDialoBefor(tv_endDate.getText().toString(), DateUtil.getDateToday(null), new MyCallback() {
                    @Override
                    public void onCallback(Object date) {
                        tv_endDate.setText(date.toString());
                        doQuery();
                    }
                }).show();
            }
        });
        mListView = (ListViewPull) findViewById(R.id.lv_data);
        mAdapterPat = new AdapterPat();
        mListView.setAdapter(mAdapterPat);
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
                Intent i = new Intent(AntActivity.this, FormAntiDrugDetailActivity.class);
                i.putExtra("antAppData", mListData.get(pos));
                startActivity(i);
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

        btn_reject = findViewById(R.id.btn_reject);
        btn_agree = findViewById(R.id.btn_agree);
        btn_reject.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                verify("R");
            }
        });
        btn_agree.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                verify("U");
            }
        });


        tv_checkall = (TextView) findViewById(R.id.tv_checkall);
        // 全选按钮的回调接口
        tv_checkall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 遍历list的长度，将OPAdmInfoAdapter中的map值全部设为true
                if (!isCheckAll) {
                    setAllCheckBox(true);
                    tv_checkall.setText("取消全选");
                    isCheckAll = true;
                } else {
                    tv_checkall.setText("全选");
                    setAllCheckBox(false);
                    isCheckAll = false;
                }
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_isgoing:
                if (!"A".equals(stutaString)) {
                    stutaString = "A";// 在用
                    doCheck();
                }
                break;
            case R.id.tv_hasfinish:
                if (!"U".equals(stutaString)) {
                    stutaString = "U";// 停用
                    doCheck();
                }
                break;
        }
    }

    public void doCheck() {
        if ("A".equals(stutaString)) {
            tv_isgoing.setTextColor(getResources().getColor(R.color.text_base));
            tv_isgoing.getPaint().setFakeBoldText(true);
            tv_isgoing_line.setVisibility(View.VISIBLE);

            tv_hasfinish.setTextColor(getResources().getColor(R.color.text_grayblack));
            tv_hasfinish.getPaint().setFakeBoldText(false);
            tv_hasfinish_line.setVisibility(View.INVISIBLE);
        } else {
            tv_hasfinish.setTextColor(getResources().getColor(R.color.text_base));
            tv_hasfinish.getPaint().setFakeBoldText(true);
            tv_hasfinish_line.setVisibility(View.VISIBLE);

            tv_isgoing.setTextColor(getResources().getColor(R.color.text_grayblack));
            tv_isgoing.getPaint().setFakeBoldText(false);
            tv_isgoing_line.setVisibility(View.INVISIBLE);
        }
        view_operation.setVisibility(View.GONE);
        tv_checkall.setVisibility(View.GONE);
        doQuery();
    }

    // 按时间分组、按类型分组监听事件
    private void setAllCheckBox(boolean b) {
        for (int i = 0; i < mListData.size(); i++) {
            mListData.get(i).isChecked = b;
        }
        mAdapterPat.notifyDataSetChanged();
    }

    private void doQuery() {
        resetData();
        loadData();
    }

    private void loadData() {
        if (mConLoad_4_pages == null || !mListView.isThereMore()) {
            mListView.endLoad(false);
            return;
        }
        final String startDate = tv_startDate.getText().toString();
        final String endDate = tv_endDate.getText().toString();

        showProgress();
        new Thread() {
            List<AntAppData> tempList;

            public void run() {
                try {
                    tempList = BusyManager.listAntAppData(Const.loginInfo.userCode,
                            Const.loginInfo.defaultDeptId, startDate, endDate,
                            stutaString, mConLoad_4_pages);
                } catch (Exception e) {
                    mInfo = e.getMessage();
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        mListView.endLoad(false);
                        if (tempList != null) {
                            int size = tempList.size();
                            if (size > 0) {
                                // 是否分员加载的标识
                                mConLoad_4_pages = tempList.get(size - 1).conLoad;
                                // 分页
                                mListData.addAll(tempList);
                                mAdapterPat.notifyDataSetChanged();
                            }
                            if (mListData.size() > 0) {
                                ll_nodata.setVisibility(View.GONE);
                                if ("A".equals(stutaString)) {
                                    view_operation.setVisibility(View.VISIBLE);
                                    tv_checkall.setVisibility(View.VISIBLE);
                                }
                            } else {
                                ll_nodata.setVisibility(View.VISIBLE);
                            }
                        } else {
                            showToast("内部错误" + mInfo);
                        }
                    }
                });
            }

            ;
        }.start();

    }

    public class AdapterPat extends BaseAdapter {
        ViewHolder holder = null;

        public AdapterPat() {
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

        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            AntAppData p = mListData.get(position);
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_ant_data, null);

                holder = new ViewHolder();
                holder.tv_appDateTime = (TextView) convertView
                        .findViewById(R.id.tv_appDateTime);
                holder.tv_appDocName = (TextView) convertView
                        .findViewById(R.id.tv_appDocName);
                holder.tv_arcimDesc = (TextView) convertView
                        .findViewById(R.id.tv_arcimDesc);
                holder.tv_patBed = (TextView) convertView
                        .findViewById(R.id.tv_patBed);
                holder.tv_patName = (TextView) convertView
                        .findViewById(R.id.tv_patName);
                holder.tv_appStatus = (TextView) convertView
                        .findViewById(R.id.tv_appStatus);
                holder.cb = (CheckBox) convertView.findViewById(R.id.cb);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if ("A".equals(stutaString)) {
                holder.cb.setVisibility(View.VISIBLE);
            } else {
                holder.cb.setVisibility(View.GONE);
            }

            holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton arg0,
                                             boolean isChecked) {
                    mListData.get(position).isChecked = isChecked;
                    notifyDataSetChanged();
                }

            });

            holder.cb.setChecked(mListData.get(position).isChecked);
            holder.tv_appDateTime.setText("申请时间：" + p.appDateTime);
            holder.tv_appDocName.setText(p.appDocName);
            holder.tv_arcimDesc.setText(p.arcimDesc);
            holder.tv_patBed.setText(p.patBed + "床");
            holder.tv_patName.setText("申请人：" + p.patName);
            holder.tv_appStatus.setText(p.appStatus);
            if ("拒绝".equals(p.appStatus)) {
                holder.tv_appStatus.setTextColor(getResources().getColor(R.color.orange));
            } else {
                holder.tv_appStatus.setTextColor(getResources().getColor(R.color.green));
            }
            return convertView;
        }

        /**
         *
         */
        public class ViewHolder {
            public CheckBox cb;
            public TextView tv_appDateTime;
            public TextView tv_appDocName;
            public TextView tv_arcimDesc;
            public TextView tv_patBed;
            public TextView tv_patName;
            public TextView tv_appStatus;
        }
    }

    //跳转到详情页面

    public void redirectToDetail(AntAppData antAppData) {
        Intent intent = new Intent(this, AntActivity.class);
        intent.putExtra("antAppData", antAppData);
        startActivity(intent);
    }

    // TODO 申请抗菌药物
    private void veryfy(String antAppId, String userReasonId, String arcimId,
                        String admId, Boolean tobeVerify) {
        ArcimItem arc = new ArcimItem();
        arc.antAppId = antAppId;
        arc.userReasonId = userReasonId;
        arc.arcimId = arcimId;
        Intent i = new Intent(this, AntActivity.class);
        i.putExtra("antAppId", antAppId);
        i.putExtra("arc", arc);
        i.putExtra("tobeVerify", tobeVerify);
        startActivityForResult(i, 10);
    }


    //抗生素申请页面结束后刷新
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                doQuery();
        }
    }

    /**
     * 返回需要处理的所有记录的ids 组织形式: 12,3,45,6
     *
     * @return
     */
    private String getCheckedIds() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mListData.size(); i++) {
            if (mListData.get(i).isChecked) {
                AntAppData a = mListData.get(i);
                sb.append(",");
                sb.append(a.antAppId);
            }
        }

        String str = null;
        if (sb.length() > 1) {
            str = sb.toString().substring(1);
        } else {
            return "";
        }
        return str;
    }


    private void verify(final String appStatus) {
        showProgress();
        new Thread() {
            BaseBean b = null;

            public void run() {
                try {
                    String antAppIds = getCheckedIds();
                    b = BusyManager.verifyApplyAntAppData(Const.loginInfo.userCode,
                            Const.loginInfo.defaultDeptId, antAppIds, appStatus);
                    mInfo = b.getResultDesc();
                } catch (Exception e) {
                    mInfo = "操作失败!\n" + e.getMessage();
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();

                        if (b != null) {
                            DialogUtil.showAlert(AntActivity.this,
                                    mInfo, new MyCallback() {
                                        @Override
                                        public void onCallback(Object value) {
                                            if ("0".equals(b.getResultCode())) {
                                                doQuery();
                                            }

                                        }

                                    });
                        } else {
                            showToast(mInfo);
                        }
                    }
                });
            }

            ;
        }.start();
    }
}