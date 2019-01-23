package com.imedical.mobiledoctor.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.imedical.im.activity.ImMainActivity;
import com.imedical.jpush.activity.MessageActivity;
import com.imedical.jpush.bean.MessageNoRead;
import com.imedical.jpush.service.MessageService;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.BusyManager;
import com.imedical.mobiledoctor.XMLservice.PageGridManager;
import com.imedical.mobiledoctor.XMLservice.SysManager;
import com.imedical.mobiledoctor.activity.WardRoundActivity;
import com.imedical.mobiledoctor.activity.frg_1.AntActivity;
import com.imedical.mobiledoctor.activity.frg_1.ConsultActivity;
import com.imedical.mobiledoctor.activity.frg_1.CriticalValueActivity;
import com.imedical.mobiledoctor.activity.round.PatientListActivity;
import com.imedical.mobiledoctor.activity.visit.DoctorScheduleActivity;
import com.imedical.mobiledoctor.adapter.DepartmentAdapter;
import com.imedical.mobiledoctor.adapter.HisRecordsAdapter;
import com.imedical.mobiledoctor.adapter.WorkAdapter;
import com.imedical.mobiledoctor.entity.BaseBeanMy;
import com.imedical.mobiledoctor.entity.DepartmentInfo;
import com.imedical.mobiledoctor.entity.Item;
import com.imedical.mobiledoctor.entity.SeeDoctorRecord;
import com.imedical.mobiledoctor.entity.homegrid.funcCode;
import com.imedical.mobiledoctor.util.DateUtil;
import com.imedical.mobiledoctor.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import static com.imedical.mobiledoctor.util.DialogUtil.dismissProgress;

public class Fragment_work extends Fragment implements View.OnClickListener {
    private MainActivity ctx;
    private String mInfo = null;
    private DepartmentAdapter departAdapter;
    private RecyclerView mRecyView_wait;
    private WorkAdapter mAdapter_wait;
    private List<Item> mList_wait = new ArrayList<>();
    private List<DepartmentInfo> RoomData = new ArrayList<DepartmentInfo>();
    private ImageView iv_top_bg;
    public LinearLayout ll_head;
    private ImageView iv_scan, iv_word;
    private PopupWindow dePatPopWin;
    ListView mListViewDepart;
    View mView = null;
    View ll_ward, ll_Onlineinquiry,ll_width,view_department;
    TextView tv_name, tv_department, tv_title, tv_date;
    private String code_wait[] = new String[]{
//            "10001",//"我的病人",
////            "10002",//"我的科室",
            "10018",//出诊信息(青医及各大通用版本)
            "10003",//"危急值管理",
            "10004",//"会诊管理",
            "10005",//"抗生素审核",
            //"10006"//日历预约协和专用
            "10008"//手术申请

    };
    private int images_wait[] = new int[]{//wait结尾的是个人工作平台info结尾的是患者资料

            R.drawable.work_1,
            R.drawable.work_2,
            R.drawable.work_3,
            R.drawable.work_4,
            R.drawable.work_5
    };
    private String names_wait[] = new String[]{
            "出诊信息",
            "危急值管理",
            "会诊管理",
            "抗生素审核",
            "手术申请"
    };

    public void onAttach(Activity activity) {
        this.ctx = (MainActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queryMsgNoRead();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_work, null);
        InitView();
        new LoadDataTask().execute();
        return mView;
    }

    private void InitView() {
        tv_date = mView.findViewById(R.id.tv_date);
        tv_date.setText(DateUtil.getDateToday(null));
        iv_scan = (ImageView) mView.findViewById(R.id.iv_scan);
        iv_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, CaptureActivity.class);
                ctx.startActivityForResult(intent, 100);
            }
        });
        iv_word = (ImageView) mView.findViewById(R.id.iv_word);
        iv_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, MessageActivity.class);
                startActivity(intent);
            }
        });
        ll_width=mView.findViewById(R.id.ll_width);
        mRecyView_wait = (RecyclerView) mView.findViewById(R.id.list_wait);
        mAdapter_wait = new WorkAdapter(mList_wait);
        mRecyView_wait.setAdapter(mAdapter_wait);
        GridLayoutManager mGridLayout_wait = new GridLayoutManager(ctx, 4);
        mRecyView_wait.setLayoutManager(mGridLayout_wait);
        mRecyView_wait.setNestedScrollingEnabled(false);
        mRecyView_wait.setHasFixedSize(true);
//        mRecyView_wait.addItemDecoration(new MyItemDecoration(ctx));
        mAdapter_wait.setOnRecyclerItemClickListener(new WorkAdapter.onRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = null;
                switch (mList_wait.get(position).getId()) {
                    case 0:
                        intent = new Intent(ctx, DoctorScheduleActivity.class);
                        break;
                    case 1:
                        intent = new Intent(ctx, CriticalValueActivity.class);
                        break;
                    case 2:
                        intent = new Intent(ctx, ConsultActivity.class);
                        break;
                    case 3:
                        intent = new Intent(ctx, AntActivity.class);
                        break;
                    case 4:
                        intent = new Intent(ctx, DoctorScheduleActivity.class);
                        break;
                    default:
                        break;
                }
                if (intent != null) {
                    startActivity(intent);
                }

            }
        });
        ll_ward = mView.findViewById(R.id.ll_ward);
        ll_ward.setOnClickListener(this);
        ll_Onlineinquiry = mView.findViewById(R.id.ll_Onlineinquiry);
        ll_Onlineinquiry.setOnClickListener(this);
        tv_name = (TextView) mView.findViewById(R.id.tv_name);
        tv_department = (TextView) mView.findViewById(R.id.tv_department);
        view_department=mView.findViewById(R.id.view_department);
        tv_title = (TextView) mView.findViewById(R.id.tv_title);
        //==========initData============
        tv_name.setText(Const.loginInfo.userName);
        tv_title.setText(Const.loginInfo.defaultGroupName);
        tv_department.setText(Const.loginInfo.defaultDeptName);
        view_department.setOnClickListener(this);
        mList_wait.clear();
        //================
//        LayoutInflater layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = getActivity().getLayoutInflater().inflate(R.layout.his_record_list, null);
        mListViewDepart = (ListView) view.findViewById(R.id.lv_data_list);
        departAdapter = new DepartmentAdapter(ctx, RoomData);
        mListViewDepart.setAdapter(departAdapter);
        mListViewDepart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                DepartmentInfo nowInfo = RoomData.get(position);
                if (dePatPopWin != null) {
                    dePatPopWin.dismiss();
                }
                tv_department.setText("当前科室:"+nowInfo.departmentName);
                Const.loginInfo.defaultDeptId=nowInfo.departmentId;
                Const.loginInfo.defaultDeptName=nowInfo.departmentName;
//                if (position == 0) {
//                    tv_hisdept.setText(SysManager.getAdmTypeDesc(sr.admType) );
//                    tv_hisrcd.setText(sr.admDate);
//                } else {
//                    tv_hisdept.setText(sr.admDept);
//                    tv_hisrcd.setText(sr.admDate);
//                }
//                selectPos = position;
                departAdapter.notifyDataSetChanged();
            }
        });
        ll_width.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // TODO Auto-generated method stub
                ll_width.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                WindowManager windowManager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
                int left=ll_width.getLeft()*2;
                int width =(windowManager.getDefaultDisplay().getWidth() - left);
                dePatPopWin = new PopupWindow(view, width, LinearLayout.LayoutParams.WRAP_CONTENT);
                dePatPopWin.setFocusable(true);
                dePatPopWin.setOutsideTouchable(true);
                dePatPopWin.setBackgroundDrawable(new BitmapDrawable());
            }
        });
        iv_top_bg=mView.findViewById(R.id.iv_top_bg);
        ll_head=mView.findViewById(R.id.ll_head);
        ll_head.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams params = iv_top_bg.getLayoutParams();
                params.height=ll_head.getHeight()-100;
                iv_top_bg.setLayoutParams(params);
            }
        });
    }


    @Override
    public void onClick(View v) {
        int vid = v.getId();
        switch (vid) {
            case R.id.ll_ward:
                if (Const.curPat != null) {
                    Intent it = new Intent(ctx, WardRoundActivity.class);
                    startActivity(it);
                } else {
                    //ctx.showNoPatDialog(ctx, null, WardRoundActivity.class);
                    Intent i =  new Intent(ctx, PatientListActivity.class);
                    String target= WardRoundActivity.class.getName();
                    i.putExtra("target",target);
                    startActivity(i);
                }
                break;
            case R.id.ll_Onlineinquiry:
                Intent it = new Intent(ctx, ImMainActivity.class);
                startActivity(it);
                break;
            case R.id.view_department:
                showWindow(v);
                break;
        }
    }



    /**
     * 异步加载菜单
     */
    public class LoadDataTask extends AsyncTask<Void, Void, Void> {
        BaseBeanMy bean = new BaseBeanMy();
        List<funcCode> tempList;
        List<DepartmentInfo> departList;

        @Override
        protected Void doInBackground(Void... params) {
            if (isCancelled()) {
                return null;
            }
            try {
                tempList = PageGridManager.dispalyList(ctx, Const.loginInfo.userCode);
                departList = BusyManager.listDept(Const.loginInfo.userCode);
                bean.success = true;
            } catch (Exception e) {
                e.printStackTrace();
                bean.success = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            int count = 0;
            if (bean.success) {
                RoomData.clear();
                RoomData.addAll(departList);
                departAdapter = new DepartmentAdapter(ctx, RoomData);
                mListViewDepart.setAdapter(departAdapter);
                departAdapter.notifyDataSetChanged();
                if (tempList.size() > 0) {
                    tempList.add(new funcCode("10018"));//出诊信息
                    for (funcCode item : tempList) {
                        //工作台 wait
                        for (int i = 0; i < code_wait.length; i++) {
                            if (code_wait[i].equals(item.functionCode)) {
                                Item itemtep = new Item(images_wait[i], names_wait[i], i);
                                mList_wait.add(itemtep);
                            }
                        }
                    }
                    mAdapter_wait.notifyDataSetChanged();
                }
            } else {
//                dispalyDefaultMenu();
            }
            super.onPostExecute(result);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

    }

    private void showWindow(View parent) {
        if (dePatPopWin != null) {
            dePatPopWin.showAsDropDown(parent, 0, 0);
        }
    }

    public void queryMsgNoRead() {
        new Thread() {
            MessageNoRead noRead;

            @Override
            public void run() {
                super.run();
                noRead = MessageService.getunreadcount();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (noRead != null && noRead.count > 0) {
                            mView.findViewById(R.id.iv_read).setVisibility(View.VISIBLE);
                        } else {
                            mView.findViewById(R.id.iv_read).setVisibility(View.GONE);
                        }

                    }
                });
            }
        }.start();
    }
}
