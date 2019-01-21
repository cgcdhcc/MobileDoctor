package com.imedical.mobiledoctor.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imedical.im.activity.ImMainActivity;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.PageGridManager;
import com.imedical.mobiledoctor.activity.WardRoundActivity;
import com.imedical.mobiledoctor.activity.frg_1.AntActivity;
import com.imedical.mobiledoctor.activity.frg_1.ConsultActivity;
import com.imedical.mobiledoctor.activity.frg_1.CriticalValueActivity;
import com.imedical.mobiledoctor.activity.visit.DoctorScheduleActivity;
import com.imedical.mobiledoctor.adapter.MyItemDecoration;
import com.imedical.mobiledoctor.adapter.WorkAdapter;
import com.imedical.mobiledoctor.entity.BaseBeanMy;
import com.imedical.mobiledoctor.entity.Item;
import com.imedical.mobiledoctor.entity.homegrid.funcCode;
import com.imedical.mobiledoctor.util.DateUtil;
import com.imedical.mobiledoctor.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment_work extends Fragment implements View.OnClickListener {
    private MainActivity ctx;
    private String mInfo=null;
    private RecyclerView mRecyView_wait;
    private WorkAdapter mAdapter_wait;
    private List<Item> mList_wait = new ArrayList<>();
    private ImageView iv_scan;
    View mView=null;
    View ll_ward,ll_Onlineinquiry;
    TextView tv_name,tv_department,tv_title,tv_date;
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
        this.ctx = (MainActivity)activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_work, null);
        InitView();
        new LoadDataTask().execute();
        return mView;
    }

    private void InitView(){
        tv_date=mView.findViewById(R.id.tv_date);
        tv_date.setText(DateUtil.getDateToday(null));
        iv_scan=(ImageView)mView.findViewById(R.id.iv_scan);
        iv_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ctx,CaptureActivity.class);
                ctx.startActivityForResult(intent,100);
            }
        });
        mRecyView_wait= (RecyclerView) mView.findViewById(R.id.list_wait);
        mAdapter_wait =new WorkAdapter(mList_wait);
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
                        intent = new  Intent(ctx, DoctorScheduleActivity.class);
                        break;
                    case 1:
                        intent =new Intent(ctx,CriticalValueActivity.class);
                        break;
                    case 2:
                        intent = new Intent(ctx, ConsultActivity.class);
                        break;
                    case 3:
                        intent =new Intent(ctx, AntActivity.class);
                        break;
                    case 4:
                        intent =new Intent(ctx, DoctorScheduleActivity.class);
                        break;
                    default:
                        break;
                }
                if(intent!=null) {
                    startActivity(intent);
                }

            }
        });
        ll_ward=mView.findViewById(R.id.ll_ward);
        ll_ward.setOnClickListener(this);
        ll_Onlineinquiry=mView.findViewById(R.id.ll_Onlineinquiry);
        ll_Onlineinquiry.setOnClickListener(this);
        tv_name=(TextView) mView.findViewById(R.id.tv_name);
        tv_department=(TextView) mView.findViewById(R.id.tv_department);
        tv_title=(TextView) mView.findViewById(R.id.tv_title);
        //==========initData============
        tv_name.setText(Const.loginInfo.userName);
        tv_title.setText(Const.loginInfo.userCode);
        tv_department.setText(Const.loginInfo.defaultDeptName);
        mList_wait.clear();
    }




    @Override
    public void onClick(View v) {
        int vid=v.getId();
        switch (vid){
            case R.id.ll_ward:
                if(Const.curPat!=null){
                    Intent it =new Intent(ctx,WardRoundActivity.class);
                    startActivity(it);
                }else {
                    ctx.showNoPatDialog(ctx,null,WardRoundActivity.class);
                }
                break;
            case R.id.ll_Onlineinquiry:
                Intent it =new Intent(ctx,ImMainActivity.class);
                startActivity(it);
                break;

        }
    }

    /**
     * 异步加载菜单
     */
    public class LoadDataTask extends AsyncTask<Void, Void, Void> {
        BaseBeanMy bean=new BaseBeanMy();
        List<funcCode> tempList;
        @Override
        protected Void doInBackground(Void... params) {
            if (isCancelled()) {
                return null;
            }
            try {
                tempList = PageGridManager.dispalyList(ctx, Const.loginInfo.userCode);
                bean.success=true;
            } catch (Exception e) {
                e.printStackTrace();
                bean.success=false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            int count=0;
            if(bean.success) {
                if(tempList.size()>0) {
                    tempList.add(new funcCode("10018"));//出诊信息
                    for (funcCode item:tempList){
                        //工作台 wait
                        for(int i=0;i<code_wait.length;i++){
                            if(code_wait[i].equals(item.functionCode)){
                                Item itemtep=new Item(images_wait[i],names_wait[i],i);
                                mList_wait.add(itemtep);
                            }
                        }
                    }
                    mAdapter_wait.notifyDataSetChanged();
                }
            }else {
//                dispalyDefaultMenu();
            }
            super.onPostExecute(result);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

    }
}
