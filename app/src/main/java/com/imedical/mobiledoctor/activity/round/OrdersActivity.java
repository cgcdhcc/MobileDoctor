package com.imedical.mobiledoctor.activity.round;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.OrderItemManager;
import com.imedical.mobiledoctor.adapter.OrderItemAdapter;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.LabelValue;
import com.imedical.mobiledoctor.entity.OrderItem;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.util.DateUtil;
import com.imedical.mobiledoctor.util.LogMe;
import com.imedical.mobiledoctor.widget.ListViewPullExp;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends BaseActivity implements
        View.OnClickListener, ListViewPullExp.IXListViewListener{
    public PatientInfo mPatientCurrSelected=null;
    private List<LabelValue> mLlistDataGroup = new ArrayList();//一级
    private List<List<OrderItem>> mListChildren = new ArrayList<List<OrderItem>>();//二级菜单
    public static OrderItemAdapter mAadapter = null;
    private String mConLoad_4_pages = "";//本次请求的参数
    private boolean beToLoad = true;
    private String mInfo = " ";
    private String mOrderState = "V";
    public static boolean isLongItem = true;
    private boolean isDocItem = true;
    private String mGroupId = "";

    private ListViewPullExp mListView = null;
    private TextView tv_startDate, tv_endDate;
    private RadioGroup rg_state;
    private RadioGroup radio_orderType;
    private RadioGroup radio_isDoc;
    private RadioButton radio_D, radio_V, radio_long, radio_temp, radio_isDoc_Y, radio_isDoc_N;
    private ImageButton btn_previous;
    private ImageButton btn_next;
    private View rootView;
    private String mLastYear = "";
    private View view_tab,view_add;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.page3_orders_activity);
        InitViews();
    }

    private void InitViews() {
        rootView=this.findViewById(R.id.rootView);
        mPatientCurrSelected=Const.curPat;
        setTitle("医嘱信息");
        setInfos(mPatientCurrSelected.patName,mPatientCurrSelected.bedCode+"床("+mPatientCurrSelected.patRegNo+")");
        List<LabelValue> listDataGroup = new ArrayList<LabelValue>();
        mGroupId = Const.loginInfo.defaultGroupId;
        mListView = (ListViewPullExp) findViewById(R.id.elv_data);
        mListView.setCacheColorHint(Color.TRANSPARENT);
        mListView.setXListViewListener(this);
        mAadapter = new OrderItemAdapter(OrdersActivity.this, mLlistDataGroup, mListChildren);
        mListView.setAdapter(mAadapter);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        if (view.getLastVisiblePosition() == (view.getCount() - 1)//滚动到底部
                                && mConLoad_4_pages != null) {//还有新的回复
                            ///Log.i("###SCROLL_STATE_IDLE", "beRepliedNum:"+note.beRepliedNum);
                            checkAndLoadDataThread();
                        }
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        // Log.v("开始滚动：SCROLL_STATE_FLING");
                        break;
                }
            }
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
            }

        });

        // 查询条件
//        final View ll_query_condition = findViewById(R.id.ll_query_condition);
//        btn_previous = (ImageButton) findViewById(R.id.btn_previous);
//        btn_previous.setOnClickListener(this);
//        tv_startDate = (TextView) findViewById(R.id.tv_startDate);
//        tv_startDate.setOnClickListener(this);
//        tv_endDate = (TextView) findViewById(R.id.tv_endDate);
//        tv_endDate.setOnClickListener(this);
//
//        String e = DateUtil.getDateToday("yyyy-MM-dd");
//        String s = DateUtil.getDateTodayBefore("yyyy-MM-dd", -2);
//        tv_startDate.setText(s);
//        tv_endDate.setText(e);

        // 在用已经停
        rg_state = (RadioGroup) findViewById(R.id.rg_state);
        radio_V = (RadioButton)findViewById(R.id.radio_V);
        radio_D = (RadioButton) findViewById(R.id.radio_D);
        rg_state.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_V) {
                    radio_V.setTextColor(getResources().getColor(R.color.white));
                    radio_D.setTextColor(getResources().getColor(R.color.new_font_blue));
                    mOrderState = "V";// 在用
                } else {
                    radio_V.setTextColor(getResources().getColor(R.color.new_font_blue));
                    radio_D.setTextColor(getResources().getColor(R.color.white));
                    mOrderState = "D";// 停用
                }
            }
        });
        radio_V.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderState = "V";// 在用
                doQuery();
            }
        });
        radio_D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderState = "D";// 停用
                doQuery();
            }
        });
        // 类型：长期、临时
//        radio_orderType = (RadioGroup) findViewById(R.id.rg_orderitem);
//        radio_long = (RadioButton) findViewById(R.id.radio_long);
//        radio_temp = (RadioButton)findViewById(R.id.radio_temp);
//        radio_orderType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup arg0, int arg1) {
//                if (arg1 == R.id.radio_long) {
//                    radio_long.setTextColor(getResources().getColor(R.color.white));
//                    radio_temp.setTextColor(getResources().getColor(R.color.new_font_blue));
//                    ll_query_condition.setVisibility(View.GONE);
//                    rg_state.setVisibility(View.VISIBLE);
//                    isLongItem = true;
//                } else {
//                    radio_long.setTextColor(getResources().getColor(R.color.new_font_blue));
//                    radio_temp.setTextColor(getResources().getColor(R.color.white));
//                    ll_query_condition.setVisibility(View.VISIBLE);
//                    rg_state.setVisibility(View.GONE);
//                    isLongItem = false;
//                }
//            }
//        });
//        radio_long.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isLongItem = true;
//                doQuery();
//            }
//        });
//
//        radio_temp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isLongItem = false;
//                doQuery();
//            }
//        });
        //医嘱与护嘱
//        radio_isDoc = (RadioGroup)findViewById(R.id.rg_orderitem_isDoc);
//        radio_isDoc_Y = (RadioButton)findViewById(R.id.radio_doc_Y);
//        radio_isDoc_N = (RadioButton) findViewById(R.id.radio_doc_N);
//        radio_isDoc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup arg0, int arg1) {
//                if (arg1 == R.id.radio_doc_Y) {
//                    radio_isDoc_N.setTextColor(getResources().getColor(R.color.new_font_blue));
//                    radio_isDoc_Y.setTextColor(getResources().getColor(R.color.white));
//                    isDocItem = true;
//                } else {
//                    radio_isDoc_Y.setTextColor(getResources().getColor(R.color.new_font_blue));
//                    radio_isDoc_N.setTextColor(getResources().getColor(R.color.white));
//                    isDocItem = false;
//                }
//            }
//        });
//        radio_isDoc_Y.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isDocItem = true;
//                doQuery();
//            }
//        });
//
//        radio_isDoc_N.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isDocItem = false;
//                doQuery();
//            }
//        });
        doQuery();
    }

    private synchronized void checkAndLoadDataThread() {
        if (mPatientCurrSelected == null) {
            showNoPatDialog(OrdersActivity.this,null,null);
            return;
        }
        if (mListView == null) {
            return;
        }
        LogMe.d("beToLoad:<<<<<<" + beToLoad);
        if (isReLoad()) {
            LogMe.d("checkAndLoadDataThread: 请求被忽略....");
            return;
        }
        LogMe.d(">>>>>>>checkAndLoadDataThread: 开始加载....");
        beToLoad = false;//不再接受新的加载请求
        mListView.startLoading();
        mListView.invalidate();
        String isDoc = null;
        if (isDocItem) {
            isDoc = "Y";
        } else {
            isDoc = "N";
        }
        if (isLongItem) {//长期
            loadOrderitemLongThread(isDoc, mPatientCurrSelected.admId, mConLoad_4_pages);
        } else {
//            loadOrderitemTempThread(isDoc, mPatientCurrSelected.admId, mConLoad_4_pages);
        }
    }

    private boolean isReLoad() {
        if (mConLoad_4_pages == null || beToLoad == false) {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onLoadMore() {
        beToLoad = true;//手动请求时，强制加载下一页
        checkAndLoadDataThread();
    }

    public void doQuery() {
        Const.curPat=mPatientCurrSelected;
        resetData();
        refreshDataOnUI();
        checkAndLoadDataThread();
    }

    private void resetData() {
        mLastYear = "";
        mConLoad_4_pages = "";
        beToLoad = true;
        mLlistDataGroup.clear();
        mListChildren.clear();
        if (mAadapter != null) {
            mAadapter.notifyDataSetChanged();
        }
    }

    private void refreshDataOnUI() {
       runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAadapter.notifyDataSetChanged();
            }
        });
    }

    private synchronized void loadOrderitemLongThread(final String isDoc, final String admId, final String conLoad) {
        new Thread() {
            public void run() {
                Message message = new Message();
                try {
                    List<OrderItem> list = OrderItemManager.listOrderItemLong(isDoc,
                            Const.loginInfo.userCode, mPatientCurrSelected.admId, mOrderState,
                            mGroupId, Const.loginInfo.defaultDeptId, mConLoad_4_pages);
                    if (list.size() > 0) {
                        mConLoad_4_pages = list.get(list.size() - 1).conLoad;
                        beToLoad = true;
                    }
                    List<LabelValue> tempLlistDataGroup = new ArrayList();//一级
                    List<List<OrderItem>> tempListChildren = new ArrayList<List<OrderItem>>();//二级菜单
                    parseDataToLists(tempLlistDataGroup, tempListChildren, list);
                    if (!admId.equals(mPatientCurrSelected.admId)) {
                        message.what = -1;
                        mInfo = "患者已切换!数据加载中...";
                    } else {
                        message.obj = new Object[]{tempLlistDataGroup, tempListChildren};
                        message.what = 0;
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

    private void parseDataToLists(List<LabelValue> tempLlistDataGroup, List<List<OrderItem>> tempListChildren, List<OrderItem> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        List<OrderItem> currListChildren = null;
        if (mListChildren.size() > 0) {//最后一项
            currListChildren = mListChildren.get(mListChildren.size() - 1);
        }
        for (OrderItem o : list) {
            String thisYear = o.orderDate;
            if (!thisYear.equals(mLastYear)) {
                //新的分组
                tempLlistDataGroup.add(new LabelValue(thisYear, thisYear));
                currListChildren = new ArrayList();
                //一个分组对应一个新的集合
                tempListChildren.add(currListChildren);
            }
            mLastYear = o.orderDate;
            // 创建新的组成员列表
            currListChildren.add(o);
        }// end for
    }

    Handler myHandler = new Handler() {
        // 子类必须重写此方法,接受数据
        public void handleMessage(Message msg) {
            try {
                dismissProgress();
                switch (msg.what) {
                    case 0:
                        Object[] arr = (Object[]) msg.obj;
                        List<LabelValue> listGroup = (List<LabelValue>) arr[0];
                        List<List<OrderItem>> listChild = (List<List<OrderItem>>) arr[1];
                        if (mConLoad_4_pages == null || listChild == null || listChild.size() == 0) {
                            mListView.stopLoadMore(false);
                        } else {
                            mListView.stopLoadMore(true);
                        }
                        mListChildren.addAll(listChild);
                        mLlistDataGroup.addAll(listGroup);
                        showListData();
                        break;
                    default:
                        showListData();
                        break;
                }
                super.handleMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
                mListView.stopLoadMore(true);
            }
        }

    };

    private void showListData() {
        if (mLlistDataGroup.size() == 0) {
            showNodata(true, rootView);
        } else {
            showNodata(false, rootView);
            refreshDataOnUI();
            for (int i = 0; i < mLlistDataGroup.size(); i++) {
                mListView.expandGroup(i);
            }
        }
    }
}
