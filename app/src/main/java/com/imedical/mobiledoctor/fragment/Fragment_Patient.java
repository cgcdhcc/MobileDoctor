package com.imedical.mobiledoctor.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.BusyManager;
import com.imedical.mobiledoctor.adapter.AdapterPat;
import com.imedical.mobiledoctor.entity.DepartmentInfo;
import com.imedical.mobiledoctor.entity.LoginInfo;
import com.imedical.mobiledoctor.entity.PatientInfo;
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
    private View mView=null,ll_patient,listItem,listView;
    private List<PatientInfo> mListDataPatient = new ArrayList<PatientInfo>();
    private List<PatientInfo> mListDataSave = new ArrayList<PatientInfo>();
    private ListViewPull mListViewPat = null;
    private AdapterPat mAdapterPat = null;
    private SearchAdapter StatusAdapter=null;
    public String isMy = "0";
    private String mConLoad = "";
    private String mLastConLoad;
    private LoginInfo mLogin;
    private Intent intent;
    private DepartmentInfo info;
    private TextView tv_mydep,tv_my,tv_my_line,tv_mydep_line,tv_patient;
    private boolean flag =false;
    private TextView tv_nodata;
    private DropDownMenu dropDownMenu;
    public void onAttach(Activity activity) {
        this.ctx = (MainActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_patient, null);
        InitViews();
        loadSecondLevelData();
        return mView;
    }

    private void InitViews(){
        mLogin = Const.loginInfo;
        ll_patient=mView.findViewById(R.id.ll_patient);
        ll_patient.setOnClickListener(this);
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
        StatusAdapter = new SearchAdapter(ctx);  //真实项目里，适配器初始化一定要写在这儿 不然如果new出来的设配器里面没有值，会报空指针
        List<Dic> sexResult = new ArrayList<>();
        sexResult.add(new Dic("0","我的病人"));
        sexResult.add(new Dic("1","科室病人"));
        StatusAdapter.setItems(sexResult);
        tv_patient=(TextView)mView.findViewById(R.id.tv_patient);
        tv_mydep=(TextView)mView.findViewById(R.id.tv_mydep);
        tv_nodata=(TextView) mView.findViewById(R.id.tv_nodata);
        tv_mydep_line=(TextView)mView.findViewById(R.id.tv_mydep_line);
        tv_my=(TextView)mView.findViewById(R.id.tv_my);
        tv_my_line=(TextView)mView.findViewById(R.id.tv_my_line);
        tv_mydep.setOnClickListener(this);
        tv_my.setOnClickListener(this);
        mListViewPat = (ListViewPull) mView.findViewById(R.id.lv_pat);
        mAdapterPat = new AdapterPat(ctx, mListDataPatient);
        mListViewPat.setAdapter(mAdapterPat);
        mListViewPat.setXListViewListener(new ListViewPull.IXListViewListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onClickLoadMore() {
                loadSecondLevelData();
            }
        });
        mListViewPat.setOnScrollListener(new AbsListView.OnScrollListener() {

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
        mListViewPat.resetStatu();
        mListViewPat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int pos,
                                    long arg3) {
                PatientInfo p = mListDataPatient.get(pos);
                if (p != null) {
                    Const.curPat=null;
                    Const.SRecorderList=null;
                    Const.curSRecorder=null;
                    Const.curPat=p;
                }
                mAdapterPat.notifyDataSetChanged();
            }
        });
    }

    private void loadSecondLevelData() {
        String deptId = "";
        if(flag){
            deptId=  mLogin.defaultDeptId;
            isMy = "0";
        }else {
            isMy = "1";
        }
        if (!mListViewPat.isThereMore()) {
            mListViewPat.endLoad(false);
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

    private boolean isReLoad() {
        if (mConLoad == mLastConLoad || (mConLoad != null && mConLoad.equals(mLastConLoad))) {
            return true;
        }
        return false;
    }

    Handler myHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    updateUI(msg);
                    break;
                case 1: // qr码
                    mListDataPatient.clear();
                    mAdapterPat.notifyDataSetChanged();
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
            List<PatientInfo> tempList = (List) msg.obj;
            // 数据是否有更新
            if (tempList != null && tempList.size() > 0) {
                mListViewPat.setVisibility(View.VISIBLE);
                mConLoad = tempList.get(tempList.size() - 1).conLoad;
                mListDataPatient.addAll(tempList);// 追加内容
                mListDataSave.addAll(tempList);
                mAdapterPat.notifyDataSetChanged();
            }else{
                mListViewPat.setVisibility(View.GONE);
               tv_nodata.setVisibility(View.VISIBLE);
            }
            if ("".equals(mLastConLoad)) {
                //  默认选中第一个,不知道有什么作用
//                    PatientInfo p = tempList.get(0);
            }
            if (mConLoad == null || tempList == null || tempList.size() == 0) {
                mListViewPat.endLoad(false);// 加载完毕
            } else {
                mListViewPat.endLoad(true);
            }
        }
    };

    @Override
    public void onClick(View v) {
        int vid=v.getId();
        switch (vid){
            case R.id.ll_patient:
                dropDownMenu.showSelectList(ScreenUtils.getScreenWidth(ctx),
                        ScreenUtils.getScreenHeight(ctx), StatusAdapter,
                        listView, listItem,ll_patient, tv_patient, "orders", false);

                break;
            case R.id.tv_mydep:
                tv_mydep_line.setBackground(getResources().getDrawable(R.color.mobile_blue));
                tv_mydep.setTextColor(getResources().getColor(R.color.mobile_blue));
                tv_my_line.setBackground(getResources().getDrawable(R.color.white));
                tv_my.setTextColor(getResources().getColor(R.color.gray));

                break;
            case R.id.tv_my:
                tv_my_line.setBackground(getResources().getDrawable(R.color.mobile_blue));
                tv_my.setTextColor(getResources().getColor(R.color.mobile_blue));
                tv_mydep_line.setBackground(getResources().getDrawable(R.color.white));
                tv_mydep.setTextColor(getResources().getColor(R.color.gray));

                break;
            default:break;
        }
    }



    private void resetUI(){
        tv_nodata.setVisibility(View.GONE);
        mConLoad = "";
        mLastConLoad=null;
        mListViewPat.resetStatu();
        mListViewPat.startLoading();
        mListDataPatient.clear();
        mListDataSave.clear();
        mAdapterPat.notifyDataSetChanged();
    }

}
