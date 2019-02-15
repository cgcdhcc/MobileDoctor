package com.imedical.mobiledoctor.activity.round;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.OrderItemManager;
import com.imedical.mobiledoctor.adapter.OrderItemAdapter;
import com.imedical.mobiledoctor.base.BaseRoundActivity;
import com.imedical.mobiledoctor.entity.LabelValue;
import com.imedical.mobiledoctor.entity.OrderItem;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.entity.SeeDoctorRecord;
import com.imedical.mobiledoctor.util.DateTimePickDialogUtil;
import com.imedical.mobiledoctor.util.DateUtil;
import com.imedical.mobiledoctor.util.LogMe;
import com.imedical.mobiledoctor.util.MyCallback;
import com.imedical.mobiledoctor.util.ScreenUtils;
import com.imedical.mobiledoctor.widget.Dic;
import com.imedical.mobiledoctor.widget.DropDownMenu;
import com.imedical.mobiledoctor.widget.ListViewPullExp;
import com.imedical.mobiledoctor.widget.Madapter;
import com.imedical.mobiledoctor.widget.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends BaseRoundActivity implements
        View.OnClickListener, ListViewPullExp.IXListViewListener{
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
    private SearchAdapter StatusAdapter=null;
    private ListViewPullExp mListView = null;
    private TextView tv_time,tv_startDate, tv_endDate,tv_isDoc,tv_noDoc,tv_isDoc_line,tv_noDoc_line;
    private RadioGroup rg_state;
    private RadioButton radio_D, radio_V;
    private View rootView,ll_time,listItem,listView,ll_longview,ll_tempview,ll_isDoc,ll_noDoc;
    private String mLastYear = "";
    private DropDownMenu dropDownMenu;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.page3_orders_activity);
        InitViews();
        InitRecordListAndPatientList(OrdersActivity.this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == SWITHC_CODE) {
            doQuery();
        }
        setInfos(Const.curPat.patName,(Const.curPat.bedCode==null?"":Const.curPat.bedCode)+"("+(Const.curPat.patRegNo==null?"":Const.curPat.patRegNo)+")");//更新姓名，床号
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void OnPatientSelected(PatientInfo p) {
        Intent it0 =new Intent(OrdersActivity.this,PatientListActivity.class);
        startActivityForResult(it0, SWITHC_CODE);
    }
    @Override
    public void OnRecordSelected(SeeDoctorRecord sr) {
        doQuery();
    }

    private void InitViews() {
        tv_isDoc=(TextView) findViewById(R.id.tv_isDoc);
        tv_noDoc=(TextView) findViewById(R.id.tv_noDoc);
        tv_isDoc_line=(TextView) findViewById(R.id.tv_isDoc_line);
        tv_noDoc_line=(TextView) findViewById(R.id.tv_noDoc_line);
        tv_time=(TextView) findViewById(R.id.tv_time);
        ll_noDoc=this.findViewById(R.id.ll_noDoc);
        ll_isDoc=this.findViewById(R.id.ll_isDoc);
        ll_noDoc.setOnClickListener(this);
        ll_isDoc.setOnClickListener(this);
        ll_longview=this.findViewById(R.id.ll_longview);
        ll_tempview=this.findViewById(R.id.ll_tempview);
        ll_time=this.findViewById(R.id.ll_time);
        ll_time.setOnClickListener(this);
        listItem = getLayoutInflater().inflate(R.layout.item_listview, null, false);
        listView = getLayoutInflater().inflate(R.layout.pup_selectlist, null, false);
        dropDownMenu = DropDownMenu.getInstance(this, new DropDownMenu.OnListCkickListence() {
            @Override
            public void search(String code, String type) {
                // 类型：长期、临时
                if(code.equals("001")){
                    isLongItem = true;
                    ll_tempview.setVisibility(View.GONE);
                    ll_longview.setVisibility(View.VISIBLE);
               }else{
                    isLongItem=false;
                    ll_tempview.setVisibility(View.VISIBLE);
                    ll_longview.setVisibility(View.GONE);
               }
                doQuery();
            }

            @Override
            public void changeSelectPanel(Madapter madapter, View view) {

            }

        });
        dropDownMenu.setIndexColor(R.color.mobile_gray);
        dropDownMenu.setShowShadow(true);
        dropDownMenu.setShowName("name");
        dropDownMenu.setSelectName("code");
        StatusAdapter = new SearchAdapter(this);  //真实项目里，适配器初始化一定要写在这儿 不然如果new出来的设配器里面没有值，会报空指针
        List<Dic> sexResult = new ArrayList<>();
        sexResult.add(new Dic("001","长期"));
        sexResult.add(new Dic("002","临时"));
        StatusAdapter.setItems(sexResult);
        rootView=this.findViewById(R.id.rootView);
        setTitle("医嘱信息");
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
        tv_startDate = (TextView) findViewById(R.id.tv_startDate);
        tv_startDate.setOnClickListener(this);
        tv_endDate = (TextView) findViewById(R.id.tv_endDate);
        tv_endDate.setOnClickListener(this);
        String e = DateUtil.getDateToday("yyyy-MM-dd");
        String s = DateUtil.getDateTodayBefore("yyyy-MM-dd", -2);
        tv_startDate.setText(s);
        tv_endDate.setText(e);

        // 在用已经停
        rg_state = (RadioGroup) findViewById(R.id.rg_state);
        radio_V = (RadioButton)findViewById(R.id.radio_V);
        radio_D = (RadioButton) findViewById(R.id.radio_D);
        rg_state.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_V) {
                    mOrderState = "V";// 在用
                } else {
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
        doQuery();
//        InitRecordList(OrdersActivity.this);
    }

    private synchronized void checkAndLoadDataThread() {
        if (Const.curPat == null) {
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
            loadOrderitemLongThread(isDoc, Const.curPat.admId, mConLoad_4_pages);
        } else {
            loadOrderitemTempThread(isDoc, Const.curPat.admId, mConLoad_4_pages);
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
        switch (view.getId()) {
            case R.id.ll_isDoc:
                    tv_isDoc.setTextColor(getResources().getColor(R.color.mobile_blue));
                    tv_isDoc_line.setBackground(getDrawable(R.color.mobile_blue));
                    tv_noDoc.setTextColor(getResources().getColor(R.color.gray));
                    tv_noDoc_line.setBackground(getDrawable(R.color.white));
                    isDocItem = true;
                    doQuery();
                break;
            case R.id.ll_noDoc:
                    tv_noDoc.setTextColor(getResources().getColor(R.color.mobile_blue));
                    tv_noDoc_line.setBackground(getDrawable(R.color.mobile_blue));
                    tv_isDoc.setTextColor(getResources().getColor(R.color.gray));
                    tv_isDoc_line.setBackground(getDrawable(R.color.white));
                    isDocItem = false;
                    doQuery();
                break;
            case R.id.ll_time:
                dropDownMenu.showSelectList(ScreenUtils.getScreenWidth(this),
                        ScreenUtils.getScreenHeight(this), StatusAdapter,
                        listView, listItem,ll_time, tv_time, "orders", false);
                break;
            case R.id.tv_startDate:
                new DateTimePickDialogUtil(OrdersActivity.this).datePicKDialoBefor(tv_startDate.getText().toString(), "", new MyCallback() {
                    @Override
                    public void onCallback(Object date) {
                        tv_startDate.setText(date.toString());
                        String endDate = tv_endDate.getText().toString();
                        if (DateUtil.isMax(date.toString(), endDate)) {
                            tv_endDate.setText(DateUtil.getDateStr("yyyy-MM-dd", tv_startDate.getText().toString(), DateUtil.getDatediff(date.toString(),endDate)));
                        }
                        resetData();
                        checkAndLoadDataThread();
                    }
                }).show();
                break;
            case R.id.tv_endDate:
                new DateTimePickDialogUtil(OrdersActivity.this).datePicKDialoBefor(tv_endDate.getText().toString(), "", new MyCallback() {
                    @Override
                    public void onCallback(Object date) {
                        tv_endDate.setText(date.toString());
                        String startDate = tv_startDate.getText().toString();
                        if (DateUtil.isMax(startDate, date.toString())) {
                            tv_startDate.setText(DateUtil.getDateStr("yyyy-MM-dd", tv_endDate.getText().toString(), -1 * DateUtil.getDatediff(startDate,date.toString())));
                        }
                        resetData();
                        checkAndLoadDataThread();
                    }
                }).show();
        }


    }

    @Override
    public void onLoadMore() {
        beToLoad = true;//手动请求时，强制加载下一页
        checkAndLoadDataThread();
    }

    public void doQuery() {
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
        showProgress();
        new Thread() {
            public void run() {
                Message message = new Message();
                try {
                    List<OrderItem> list = OrderItemManager.listOrderItemLong(isDoc,
                            Const.loginInfo.userCode, Const.curPat.admId, mOrderState,
                            mGroupId, Const.loginInfo.defaultDeptId, mConLoad_4_pages);
                    if (list.size() > 0) {
                        mConLoad_4_pages = list.get(list.size() - 1).conLoad;
                        beToLoad = true;
                    }
                    List<LabelValue> tempLlistDataGroup = new ArrayList();//一级
                    List<List<OrderItem>> tempListChildren = new ArrayList<List<OrderItem>>();//二级菜单
                    parseDataToLists(tempLlistDataGroup, tempListChildren, list);
                    if (!admId.equals(Const.curPat.admId)) {
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

    private synchronized void loadOrderitemTempThread(final String isDoc, final String admId, final String conLoad) {
        showProgress();
        final String startDate = tv_startDate.getText().toString();
        final String endDate = tv_endDate.getText().toString();
        new Thread() {
            public void run() {
                Message message = new Message();
                try {
                    List<OrderItem> list = OrderItemManager.listOrderItemTemp(
                            isDoc, Const.loginInfo.userCode, admId, startDate, endDate,
                            mGroupId, Const.loginInfo.defaultDeptId, conLoad);

                    if (list.size() > 0) {
                        mConLoad_4_pages = list.get(list.size() - 1).conLoad;
                        beToLoad = true;
                        LogMe.d("beToLoad:<<<<<<" + beToLoad);
                        LogMe.d("mConLoad_4_pages:<<<<<<" + mConLoad_4_pages);
                    }
                    List<LabelValue> tempLlistDataGroup = new ArrayList();//一级
                    List<List<OrderItem>> tempListChildren = new ArrayList<List<OrderItem>>();//二级菜单
                    parseDataToLists(tempLlistDataGroup, tempListChildren, list);
                    if (!admId.equals(Const.curPat.admId)) {
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
