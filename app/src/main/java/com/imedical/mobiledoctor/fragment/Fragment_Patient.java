package com.imedical.mobiledoctor.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.BusyManager;
import com.imedical.mobiledoctor.activity.WardRoundActivity;
import com.imedical.mobiledoctor.activity.round.OrdersActivity;
import com.imedical.mobiledoctor.adapter.AdapterOutPat;
import com.imedical.mobiledoctor.adapter.AdapterPat;
import com.imedical.mobiledoctor.entity.AdmInfo;
import com.imedical.mobiledoctor.entity.DepartmentInfo;
import com.imedical.mobiledoctor.entity.LoginInfo;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.util.DateTimePickDialogUtil;
import com.imedical.mobiledoctor.util.DateUtil;
import com.imedical.mobiledoctor.util.MyCallback;
import com.imedical.mobiledoctor.util.ScreenUtils;
import com.imedical.mobiledoctor.widget.Dic;
import com.imedical.mobiledoctor.widget.DropDownMenu;
import com.imedical.mobiledoctor.widget.ListViewPull;
import com.imedical.mobiledoctor.widget.Madapter;
import com.imedical.mobiledoctor.widget.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Patient extends Fragment implements View.OnClickListener {
    private MainActivity ctx;
    private String mInfo=null;
    private View mView=null,ll_patient,listItem,listView,ll_outPatient,ll_type,ll_noHandle,ll_Handled,ll_inPatient;
    private List<AdmInfo> outPat_ListDataPatient = new ArrayList<AdmInfo>();
    private List<PatientInfo> inPat_ListDataPatient = new ArrayList<PatientInfo>();
    private List<PatientInfo> mListDataSave = new ArrayList<PatientInfo>();
    private ListViewPull inpat_ListViewPat = null,outPat_ListViewPat=null;
    private AdapterPat inPat_AdapterPat = null;
    private AdapterOutPat outPat_AdapterPat = null;
    private SearchAdapter inPat_SearchAPT=null;
    private SearchAdapter outPat_SearchAPT=null;
    private View ll_nodata;
    public String isMy = "0";
    private String mConLoad = "";
    private String mLastConLoad;
    private LoginInfo mLogin;
    private Intent intent;
    private DepartmentInfo info;
    private TextView tv_mydep,tv_my,tv_my_line,tv_mydep_line,tv_patient,tv_type,tv_startDate,tv_endDate,tv_noHandle,tv_Handled,tv_noHandle_line,tv_Handled_line;
    private boolean flag =false;
    private String outPat_type ="N";//医生接诊类型:N正常门诊 T图文咨询 V视频咨询 默认为空是所有
    private String outPat_handle ="N";//接诊标识，已接诊/未接诊Y/N
    private EditText et_search;
    private DropDownMenu dropDownMenu,dropDownMenu_outPat;
    public void onAttach(Activity activity) {
        this.ctx = (MainActivity) activity;
        super.onAttach(activity);
    }
    private void resetData(View listView) {
        ll_nodata.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_patient, null);
        InitViews();
        OutPatInitViews();
        loadSecondLevelData();
        return mView;
    }

    private void OutPatInitViews() {
        outPat_ListViewPat=mView.findViewById(R.id.lv_outpat);
        outPat_AdapterPat=new AdapterOutPat(ctx,outPat_ListDataPatient);
        outPat_ListViewPat.setAdapter(outPat_AdapterPat);
        ll_outPatient=mView.findViewById(R.id.ll_outPatient);
        ll_type=mView.findViewById(R.id.ll_type);
        ll_noHandle=mView.findViewById(R.id.ll_noHandle);
        ll_noHandle.setOnClickListener(this);
        ll_Handled=mView.findViewById(R.id.ll_Handled);
        ll_Handled.setOnClickListener(this);
        tv_type=(TextView) mView.findViewById(R.id.tv_type);
        tv_startDate=(TextView) mView.findViewById(R.id.tv_startDate);
        tv_endDate=(TextView) mView.findViewById(R.id.tv_endDate);
        tv_noHandle=(TextView) mView.findViewById(R.id.tv_noHandle);
        tv_Handled=(TextView) mView.findViewById(R.id.tv_Handled);
        tv_noHandle_line=(TextView) mView.findViewById(R.id.tv_noHandle_line);
        tv_Handled_line=(TextView) mView.findViewById(R.id.tv_Handled_line);
        String e = DateUtil.getDateToday("yyyy-MM-dd");
        String s = DateUtil.getDateToday("yyyy-MM-dd");
        tv_startDate.setText(s);
        tv_startDate.setOnClickListener(this);
        tv_endDate.setText(e);
        tv_endDate.setOnClickListener(this);
        ll_type.setOnClickListener(this);
        dropDownMenu_outPat = DropDownMenu.getInstance(ctx, new DropDownMenu.OnListCkickListence() {
            @Override
            public void search(String code, String type) {
                outPat_type=code;////医生接诊类型:N正常门诊 T图文咨询 V视频咨询 默认为空是所有
//                ctx.showCustom(outPat_type);
                resetOutPateintUI();
                loadOutPatient();
            }
            @Override
            public void changeSelectPanel(Madapter madapter, View view) {

            }

        });
        dropDownMenu_outPat.setIndexColor(R.color.mobile_gray);
        dropDownMenu_outPat.setShowShadow(true);
        dropDownMenu_outPat.setShowName("name");
        dropDownMenu_outPat.setSelectName("code");
        outPat_SearchAPT = new SearchAdapter(ctx);  //真实项目里，适配器初始化一定要写在这儿 不然如果new出来的设配器里面没有值，会报空指针
        List<Dic> sexResult = new ArrayList<>();
        sexResult.add(new Dic("N","正常门诊"));
        sexResult.add(new Dic("T","图文咨询"));
        sexResult.add(new Dic("V","视频咨询"));
        sexResult.add(new Dic("", "所有状态"));
        outPat_SearchAPT.setItems(sexResult);
        outPat_ListViewPat.setXListViewListener(new ListViewPull.IXListViewListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onClickLoadMore() {
                loadSecondLevelData();
            }
        });
        outPat_ListViewPat.setOnScrollListener(new AbsListView.OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        if (view.getLastVisiblePosition() == (view.getCount() - 1)// 滚动到底部
                                && mConLoad != null) {// 还有新的回复
                            loadSecondLevelData();
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
        outPat_ListViewPat.resetStatu();
        outPat_ListViewPat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int pos,
                                    long arg3) {
                AdmInfo p = outPat_ListDataPatient.get(pos);
                if (p != null) {
                    Const.curPat=null;
                    Const.SRecorderList=null;
                    Const.curSRecorder=null;
                    PatientInfo fake_p=new PatientInfo(p);
                    Const.curPat=fake_p;
                }
                outPat_AdapterPat.notifyDataSetChanged();
                if(Const.curPat!=null){
                    Intent it =new Intent(ctx,WardRoundActivity.class);
                    startActivity(it);
                }
            }
        });

    }

    private void InitViews(){
        mLogin = Const.loginInfo;
        ll_nodata=mView.findViewById(R.id.ll_nodata);
        ll_inPatient=mView.findViewById(R.id.ll_inPatient);
        ll_patient=mView.findViewById(R.id.ll_patient);
        ll_patient.setOnClickListener(this);
        et_search=(EditText) mView.findViewById(R.id.et_search);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchlisten(s.toString(),inPat_ListDataPatient,mListDataSave);
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        listItem = getLayoutInflater().inflate(R.layout.item_listview, null, false);
        listView = getLayoutInflater().inflate(R.layout.pup_selectlist, null, false);
        dropDownMenu = DropDownMenu.getInstance(ctx, new DropDownMenu.OnListCkickListence() {
            @Override
            public void search(String code, String type) {
                if(code.equals("0")){
                    flag=false;
                }else{
                    flag=true;
                }
                resetUI();
                loadSecondLevelData();
            }
            @Override
            public void changeSelectPanel(Madapter madapter, View view) {

            }

        });
        dropDownMenu.setIndexColor(R.color.mobile_gray);
        dropDownMenu.setShowShadow(true);
        dropDownMenu.setShowName("name");
        dropDownMenu.setSelectName("code");
        inPat_SearchAPT = new SearchAdapter(ctx);  //真实项目里，适配器初始化一定要写在这儿 不然如果new出来的设配器里面没有值，会报空指针
        List<Dic> sexResult = new ArrayList<>();
        sexResult.add(new Dic("0","我的病人"));
        sexResult.add(new Dic("1","科室病人"));
        inPat_SearchAPT.setItems(sexResult);
        tv_patient=(TextView)mView.findViewById(R.id.tv_patient);
        tv_mydep=(TextView)mView.findViewById(R.id.tv_mydep);
//        tv_nodata=(TextView) mView.findViewById(R.id.tv_nodata);
        tv_mydep_line=(TextView)mView.findViewById(R.id.tv_mydep_line);
        tv_my=(TextView)mView.findViewById(R.id.tv_my);
        tv_my_line=(TextView)mView.findViewById(R.id.tv_my_line);
        tv_mydep.setOnClickListener(this);
        tv_my.setOnClickListener(this);
        inpat_ListViewPat = (ListViewPull) mView.findViewById(R.id.lv_pat);
        inPat_AdapterPat = new AdapterPat(ctx, inPat_ListDataPatient);
        inpat_ListViewPat.setAdapter(inPat_AdapterPat);
        inpat_ListViewPat.setXListViewListener(new ListViewPull.IXListViewListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onClickLoadMore() {
                loadSecondLevelData();
            }
        });
        inpat_ListViewPat.setOnScrollListener(new AbsListView.OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        if (view.getLastVisiblePosition() == (view.getCount() - 1)// 滚动到底部
                                && mConLoad != null) {// 还有新的回复
                            loadSecondLevelData();
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
        inpat_ListViewPat.resetStatu();
        inpat_ListViewPat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int pos,
                                    long arg3) {
                PatientInfo p = inPat_ListDataPatient.get(pos);
                if (p != null) {
                    Const.curPat=null;
                    Const.SRecorderList=null;
                    Const.curSRecorder=null;
                    Const.curPat=p;
                }
                inPat_AdapterPat.notifyDataSetChanged();
                if(Const.curPat!=null){
                    Intent it =new Intent(ctx,WardRoundActivity.class);
                    startActivity(it);
                }
            }
        });
    }

    private  void loadOutPatient(){
        ctx.showProgress();
        new Thread(){
            @Override
            public void run() {
                super.run();
                Message msg = myHandler.obtainMessage();
                try {
                    String stDate=tv_startDate.getText().toString();
                    String edDate=tv_endDate.getText().toString();
                    List<AdmInfo> tmpList = BusyManager.listOutPatInfo(mLogin.userCode, outPat_type, outPat_handle, stDate,edDate);
                    msg.what = 2;
                    if(tmpList!=null) {
                        msg.obj = tmpList;
                    }
                } catch (Exception e) {
                    mInfo = e.getMessage();
                    msg.what = -1;
                    e.printStackTrace();
                } finally {
                    msg.sendToTarget();
                }
            }
        }.start();
    }


    public void loadSecondLevelData() {
        String deptId = "";
        if(flag){
            deptId=  mLogin.defaultDeptId;
            isMy = "0";
        }else {
            isMy = "1";
        }
        if (!inpat_ListViewPat.isThereMore()) {
            inpat_ListViewPat.endLoad(false);
            return;
        }
        if (isReLoad()) {
            return;
        }
        mLastConLoad = mConLoad;
        final String departmentId = deptId;
        ctx.showProgress();
        new Thread() {
            public void run() {
                Message msg = myHandler.obtainMessage();
                try {
                    List<PatientInfo> list2 = BusyManager.listPatientInfo(mLogin.userCode, departmentId, isMy, mConLoad);// mLogin.userCode
                    msg.what = 0;
                    if(list2!=null) {
                        msg.obj = list2;
                    }
                } catch (Exception e) {
                    mInfo = e.getMessage();
                    msg.what = -1;
                    e.printStackTrace();
                } finally {
                    msg.sendToTarget();
                }
            };
        }.start();
    }
    private void searchlisten(String str, List<PatientInfo> mListDataPatient, List<PatientInfo> mListDataSave) {
        if ("".equals(str)) {
            mListDataPatient.clear();
            if (mListDataSave != null && mListDataSave.size() > 0) {
                mListDataPatient.addAll(mListDataSave);
//                tv_nodata.setVisibility(View.GONE);
                inpat_ListViewPat.setVisibility(View.VISIBLE);
                inPat_AdapterPat.notifyDataSetChanged();
            } else {
//                tv_nodata.setVisibility(View.VISIBLE);
                inpat_ListViewPat.setVisibility(View.GONE);
            }
            inPat_AdapterPat.notifyDataSetChanged();
            inpat_ListViewPat.endLoad(false);
        } else {
            List<PatientInfo> list = new ArrayList<PatientInfo>();
            for (PatientInfo orderReg : mListDataSave) {
                if (orderReg.patName.contains(str)) {
                    list.add(orderReg);
                } else {
                    continue;
                }
            }
            mListDataPatient.clear();
            if (list != null && list.size() > 0) {
                mListDataPatient.addAll(list);
//                tv_nodata.setVisibility(View.GONE);
                inpat_ListViewPat.setVisibility(View.VISIBLE);
                inPat_AdapterPat.notifyDataSetChanged();
                inpat_ListViewPat.endLoad(false);
            } else {
//                tv_nodata.setVisibility(View.VISIBLE);
                inpat_ListViewPat.setVisibility(View.GONE);
            }
            // getSearch();
        }

    }


    private boolean isReLoad() {
        if (mConLoad == mLastConLoad || (mConLoad != null && mConLoad.equals(mLastConLoad))) {
            return true;
        }
        return false;
    }

    Handler myHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    updateOutPatUI(msg);
                    break;
                case 0:
                    updateUI(msg);
                    break;
                default:
                    if (mInfo == null)
                        mInfo = "";
                    ctx.showCustom( mInfo);
                    break;
            }
            ctx.dismissProgress();
            super.handleMessage(msg);
        }

        private void updateUI(Message msg) {
            inPat_ListDataPatient.clear();
            inPat_AdapterPat.notifyDataSetChanged();
            List<PatientInfo> tempList = (List) msg.obj;
            if (tempList != null && tempList.size() > 0) {
                ll_nodata.setVisibility(View.GONE);
                inpat_ListViewPat.setVisibility(View.VISIBLE);
                mConLoad = tempList.get(tempList.size() - 1).conLoad;
                inPat_ListDataPatient.addAll(tempList);// 追加内容
                mListDataSave.addAll(tempList);
                inPat_AdapterPat.notifyDataSetChanged();
            }else{
                inpat_ListViewPat.setVisibility(View.GONE);
//               tv_nodata.setVisibility(View.VISIBLE);
                ll_nodata.setVisibility(View.VISIBLE);
            }
            if ("".equals(mLastConLoad)) {
                //  默认选中第一个,不知道有什么作用
//                    PatientInfo p = tempList.get(0);
            }
            if (mConLoad == null || tempList == null || tempList.size() == 0) {
                inpat_ListViewPat.endLoad(false);// 加载完毕
            } else {
                inpat_ListViewPat.endLoad(true);
            }
        }

        private void updateOutPatUI(Message msg) {
            outPat_ListDataPatient.clear();
            outPat_AdapterPat.notifyDataSetChanged();
            List<AdmInfo> tempList = (List) msg.obj;
            if (tempList != null && tempList.size() > 0) {
                ll_nodata.setVisibility(View.GONE);
                outPat_ListViewPat.setVisibility(View.VISIBLE);
                outPat_ListDataPatient.addAll(tempList);// 追加内容
                outPat_AdapterPat.notifyDataSetChanged();
            }else{
                ll_nodata.setVisibility(View.VISIBLE);
                outPat_ListViewPat.setVisibility(View.GONE);
//                tv_nodata.setVisibility(View.VISIBLE);
            }
            outPat_ListViewPat.endLoad(false);// 加载完毕
        }

    };

    @Override
    public void onClick(View v) {
        int vid=v.getId();
        switch (vid){
            case R.id.ll_noHandle:
                tv_noHandle.setTextColor(getResources().getColor(R.color.mobile_blue));
                tv_noHandle_line.setBackground(getResources().getDrawable(R.color.mobile_blue));
                tv_Handled.setTextColor(getResources().getColor(R.color.gray));
                tv_Handled_line.setBackground(getResources().getDrawable(R.color.white));
                outPat_handle="N";
                resetOutPateintUI();
                loadOutPatient();
                break;
            case R.id.ll_Handled:
                tv_Handled.setTextColor(getResources().getColor(R.color.mobile_blue));
                tv_Handled_line.setBackground(getResources().getDrawable(R.color.mobile_blue));
                 tv_noHandle.setTextColor(getResources().getColor(R.color.gray));
                 tv_noHandle_line.setBackground(getResources().getDrawable(R.color.white));
                outPat_handle="Y";
                resetOutPateintUI();
                loadOutPatient();
                break;
            case R.id.ll_type:
                dropDownMenu_outPat.showSelectList(ScreenUtils.getScreenWidth(ctx),
                        ScreenUtils.getScreenHeight(ctx), outPat_SearchAPT,
                        listView, listItem,ll_type, tv_type, "outPatient", false);
                break;
            case R.id.tv_startDate:
                new DateTimePickDialogUtil(ctx).datePicKDialoBefor(tv_startDate.getText().toString(), "", new MyCallback() {
                    @Override
                    public void onCallback(Object date) {
                        tv_startDate.setText(date.toString());
                        String endDate = tv_endDate.getText().toString();
                        if (DateUtil.isMax(date.toString(), endDate)) {
                            tv_endDate.setText(DateUtil.getDateStr("yyyy-MM-dd", tv_startDate.getText().toString(), DateUtil.getDatediff(date.toString(),endDate)));
                        }
                        resetOutPateintUI();
                        loadOutPatient();
                    }
                }).show();
                break;
            case R.id.tv_endDate:
                    new DateTimePickDialogUtil(ctx).datePicKDialoBefor(tv_endDate.getText().toString(), "", new MyCallback() {
                        @Override
                        public void onCallback(Object date) {
                            tv_endDate.setText(date.toString());
                            String startDate = tv_startDate.getText().toString();
                            if (DateUtil.isMax(startDate, date.toString())) {
                                tv_startDate.setText(DateUtil.getDateStr("yyyy-MM-dd", tv_endDate.getText().toString(), -1 * DateUtil.getDatediff(startDate,date.toString())));
                            }
                            resetOutPateintUI();
                            loadOutPatient();
                        }
                    }).show();
                break;
            //=============↑↑↑↑=========以上是门诊患者操作=====↑↑↑↑======================↑↑↑↑======================↑↑↑↑=====
            case R.id.ll_patient:
                dropDownMenu.showSelectList(ScreenUtils.getScreenWidth(ctx),
                        ScreenUtils.getScreenHeight(ctx), inPat_SearchAPT,
                        listView, listItem,ll_patient, tv_patient, "orders", false);
                break;
            case R.id.tv_mydep:
                tv_mydep_line.setBackground(getResources().getDrawable(R.color.mobile_blue));
                tv_mydep.setTextColor(getResources().getColor(R.color.mobile_blue));
                tv_my_line.setBackground(getResources().getDrawable(R.color.white));
                tv_my.setTextColor(getResources().getColor(R.color.gray));
                ll_outPatient.setVisibility(View.GONE);
                ll_inPatient.setVisibility(View.VISIBLE);
                inpat_ListViewPat.setVisibility(View.VISIBLE);
                outPat_ListViewPat.setVisibility(View.GONE);
                resetUI();
                loadSecondLevelData();
                break;
            case R.id.tv_my:
                tv_my_line.setBackground(getResources().getDrawable(R.color.mobile_blue));
                tv_my.setTextColor(getResources().getColor(R.color.mobile_blue));
                tv_mydep_line.setBackground(getResources().getDrawable(R.color.white));
                tv_mydep.setTextColor(getResources().getColor(R.color.gray));
                ll_inPatient.setVisibility(View.GONE);
                ll_outPatient.setVisibility(View.VISIBLE);
                inpat_ListViewPat.setVisibility(View.GONE);
                outPat_ListViewPat.setVisibility(View.VISIBLE);
                resetOutPateintUI();
                loadOutPatient();
                break;
            default:break;
        }
    }

    public void loadQrData( final String depCode,
                            final String bracelet) {
        ctx.showProgress();
        new Thread() {

            public void run() {
                Message msg = myHandler.obtainMessage();
                try {
                    List<PatientInfo> tempList = BusyManager.listQrPatientInfo(mLogin.userCode, depCode, bracelet);
                    msg.what = 1;
                    msg.obj = tempList;
                } catch (Exception e) {
                    mInfo = e.getMessage();
                    msg.what = -1;
                    e.printStackTrace();
                } finally {
                    msg.sendToTarget();
                }
            };
        }.start();
    }


    public void resetUI(){
//        tv_nodata.setVisibility(View.GONE);
        mConLoad = "";
        mLastConLoad=null;
        inpat_ListViewPat.resetStatu();
        inpat_ListViewPat.startLoading();
        inPat_ListDataPatient.clear();
        mListDataSave.clear();
        inPat_AdapterPat.notifyDataSetChanged();
    }

    private void resetOutPateintUI(){
//        tv_nodata.setVisibility(View.GONE);
        outPat_ListViewPat.resetStatu();
        outPat_ListViewPat.startLoading();
        outPat_ListDataPatient.clear();
        outPat_AdapterPat.notifyDataSetChanged();
    }

}
