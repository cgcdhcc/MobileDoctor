package com.imedical.mobiledoctor.activity.round;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imedical.mobiledoctor.AppConfig;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.LisManager;
import com.imedical.mobiledoctor.adapter.LisReportAdapter;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.LisReportList;
import com.imedical.mobiledoctor.entity.LoginInfo;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.widget.ListViewPullExp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LisActivity extends BaseActivity implements
        View.OnClickListener, AdapterView.OnItemClickListener,
        ListViewPullExp.IXListViewListener {
    private View rootView,ll_type,ll_time;
    private TextView btn_time,btn_type,btn_time_line,btn_type_line;
    private String mInfo = " test ";
    private PatientInfo mPatientCurrSelected;
    private ListViewPullExp expandList;
    private LinearLayout ll_nodata;
    private LinearLayout ll_class;
    public List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
    public List<List<Map<String, LisReportList>>> childDataList = new ArrayList<List<Map<String, LisReportList>>>();
    private LisReportAdapter groupAdapter;
    public static int mIndexSelectedGroup = -1;
    public static int mIndexSelectedPat = -1;
    private String mConLoad_4_pages = "";
    private String mLastConLoad = null;// 上次请求的参数，控制重复加载 ，上次与本次相同时不重复加载
    public String sort = "Item";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.page4_lis_activity);
        InitViews();
        resetData();
        loadDataThread(mPatientCurrSelected.admId, sort);
        InitRecordList();
    }

    private void InitViews() {
        mPatientCurrSelected = Const.curPat;
        setTitle("检验信息");
        ll_type=findViewById(R.id.ll_type);
        ll_time=findViewById(R.id.ll_time);
        ll_type.setOnClickListener(this);
        ll_time.setOnClickListener(this);
        btn_type = (TextView) findViewById(R.id.btn_type);
        btn_time = (TextView) findViewById(R.id.btn_time);
        btn_time_line = (TextView) findViewById(R.id.btn_time_line);
        btn_type_line = (TextView) findViewById(R.id.btn_type_line);
        expandList = (ListViewPullExp) findViewById(R.id.expandList);
        ll_nodata = (LinearLayout)findViewById(R.id.ll_nodata);
        ll_class = (LinearLayout)findViewById(R.id.ll_class);
        groupAdapter = new LisReportAdapter(LisActivity.this, groupData,
                R.layout.adapter_lis_group, new String[]{"group"},
                new int[]{R.id.groupto}, childDataList, R.layout.adapter_lis_child,
                new String[]{"child"}, new int[]{R.id.tv_patName});
        expandList.setAdapter(groupAdapter);
        expandList.setXListViewListener(this);
        expandList.setGroupIndicator(null);
        expandList.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        if (view.getLastVisiblePosition() == (view.getCount() - 1)// 滚动到底部
                                && mConLoad_4_pages != null) {// 还有新的内容
                            loadDataThread(mPatientCurrSelected.admId, sort);
                        }
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        // Log.v("开始滚动：SCROLL_STATE_FLING");
                        break;
                }
            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }

        });
    }
    private void resetData() {
        mConLoad_4_pages = "";
        mLastConLoad = null;
        expandList.resetStatu();
        groupData.clear();
        childDataList.clear();
        if (groupAdapter != null) {
            groupAdapter.notifyDataSetChanged();
        }
    }

    private void refreshDataOnUI() {
       runOnUiThread(new Runnable() {
            @Override
            public void run() {
                groupAdapter.notifyDataSetChanged();
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_type:
                resetData();
                refreshDataOnUI();
                sort = "Item";
                btn_time.setTextColor(getResources().getColor(R.color.gray));
                btn_time_line.setBackground(getResources().getDrawable(R.color.white));
                btn_type.setTextColor(getResources().getColor(R.color.mobile_blue));
                btn_type_line.setBackground(getResources().getDrawable(R.color.mobile_blue));
                loadDataThread(mPatientCurrSelected.admId, sort);
                break;
            case R.id.ll_time:
                resetData();
                refreshDataOnUI();
                sort = "Date";
                btn_type.setTextColor(getResources().getColor(R.color.gray));
                btn_time.setTextColor(getResources().getColor(R.color.mobile_blue));
                btn_time_line.setBackground(getResources().getDrawable(R.color.mobile_blue));
                btn_type_line.setBackground(getResources().getDrawable(R.color.white));
                loadDataThread(mPatientCurrSelected.admId, sort);
                break;
            default:break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onLoadMore() {

    }

    private boolean isReLoad() {
        if (mConLoad_4_pages == mLastConLoad
                || (mConLoad_4_pages != null && mConLoad_4_pages
                .equals(mLastConLoad))) {
            return true;
        }
        return false;
    }
    private void loadDataThread(final String admId, final String sor) {
        if (!expandList.isThereMore()) {
            return;
        }
        if (isReLoad()) {
            return;
        }
        expandList.startLoading();
        expandList.invalidate();
        mLastConLoad = mConLoad_4_pages;
        if (mLastConLoad != null && !mLastConLoad.equals("")) {
            showProgress();
        }
        new Thread() {
            public void run() {
                Message message = new Message();
                LoginInfo mLogin = Const.loginInfo;
                try {
                    Map map = new HashMap();
                    map.put("userCode", mLogin.userCode);
                    map.put("admId", admId);
                    map.put("conLoad", mConLoad_4_pages);
                    map.put("sort", sor);
                    List<LisReportList> list = LisManager.listLisReportList(map);
                    if (list != null && list.size() > 0) {
                        mConLoad_4_pages = list.get(list.size() - 1).conLoad;
                        List<Map<String, String>> tempGroupData = new ArrayList<Map<String, String>>();
                        List<List<Map<String, LisReportList>>> tempChildDataList = new ArrayList<List<Map<String, LisReportList>>>();
                        parseListData(tempGroupData, tempChildDataList, list);
                        message.what = 0;
                        message.obj = new Object[]{tempGroupData, tempChildDataList};
                        if (!admId.equals(mPatientCurrSelected.admId)) {// 如果已经切人了
                            mInfo = "已切换患者！数据加载中...";
                            message.what = -1;
                        }
                    } else {
                        message.what = -1;
                        mInfo = "无数据！";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mInfo = e.getMessage();
                    message.what = -2;
                } finally {
                    mHandlerLeft.sendMessage(message);
                }
            }
        }.start();
    }

    private void showListDate() {
        expandList.setVisibility(View.VISIBLE);
        ll_nodata.setVisibility(View.GONE);
        ll_class.setVisibility(View.VISIBLE);
        for (int i = 0; i < groupData.size(); i++) {
            expandList.expandGroup(i);
        }
        if (groupData.size() > 0 && childDataList.size() > 0) {
            mIndexSelectedPat = 0;
            mIndexSelectedGroup = 0;
            groupAdapter.notifyDataSetChanged();
        }
    }
    Handler mHandlerLeft = new Handler() {

        public void handleMessage(Message msg) {
            dismissProgress();
            switch (msg.what) {
                case 0:
                    Object[] tempList = ((Object[]) msg.obj);
                    List group = (List) tempList[0];
                    List children = (List) tempList[1];
                    if (children.size() == 0 || mConLoad_4_pages == null) {
                        expandList.stopLoadMore(false);
                    } else {
                        expandList.stopLoadMore(true);
                    }
                    groupData.addAll(group);
                    childDataList.addAll(children);
                    groupAdapter.notifyDataSetChanged();
                    showListDate();
                    break;
                case -1:
                    expandList.stopLoadMore(false);
                    expandList.setVisibility(View.GONE);
                    ll_class.setVisibility(View.GONE);
                    ll_nodata.setVisibility(View.VISIBLE);
                    break;
                default:// 加载出错
//				showResultToast(mInfo);
                    expandList.stopLoadMore(false);
                    expandList.setVisibility(View.GONE);
                    ll_class.setVisibility(View.GONE);
                    ll_nodata.setVisibility(View.VISIBLE);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void parseListData(List<Map<String, String>> tempGroupData,
                               List<List<Map<String, LisReportList>>> tempChildDataList,
                               List<LisReportList> list) {
        String currArcItemId = null;
        String lastArcItemId = null;
        String currOrdDate = null;
        String lastOrdDate = null;
        boolean flag = false;
        List currChildList = null;
        if (sort.equals("Item")) {
            for (LisReportList l : list) {
                lastArcItemId = currArcItemId;
                currArcItemId = l.arcItemId;
                mConLoad_4_pages = l.conLoad;
                if (!currArcItemId.equals(lastArcItemId)) {
                    flag = true;
                    Map<String, String> group = new HashMap<String, String>();
                    group.put("group", l.ordItemDesc);

                    tempGroupData.add(group);
                    currChildList = new ArrayList();
                    tempChildDataList.add(currChildList);
                } else if (currArcItemId.equals(lastArcItemId) && flag) {
                    // 历次记录
                    flag = false;
                    LisReportList empList = new LisReportList();
                    empList.ordDate = "历次记录";
                    empList.reportException = "2";
                    empList.arcItemId = currArcItemId;
                    Map<String, LisReportList> empChild = new HashMap<String, LisReportList>();
                    empChild.put("child", empList);
                    currChildList.add(0, empChild);
                }
                Map<String, LisReportList> child = new HashMap<String, LisReportList>();
                child.put("child", l);
                currChildList.add(child);
            }// end for
        } else if (sort.equals("Date")) {
            for (LisReportList l : list) {
                lastOrdDate = currOrdDate;
                currOrdDate = l.ordDate;
                mConLoad_4_pages = l.conLoad;
                if (!currOrdDate.equals(lastOrdDate)) {
                    Map<String, String> group = new HashMap<String, String>();
                    group.put("group", l.ordDate);
                    tempGroupData.add(group);
                    currChildList = new ArrayList();
                    tempChildDataList.add(currChildList);
                }
                Map<String, LisReportList> child = new HashMap<String, LisReportList>();
                child.put("child", l);
                currChildList.add(child);
            }
        }

    }
}
