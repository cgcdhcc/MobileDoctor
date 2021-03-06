package com.imedical.mobiledoctor.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.BusyManager;
import com.imedical.mobiledoctor.XMLservice.SysManager;
import com.imedical.mobiledoctor.activity.round.MultimediaActivity;
import com.imedical.mobiledoctor.base.BaseRoundActivity;
import com.imedical.mobiledoctor.activity.round.CaseActivity;
import com.imedical.mobiledoctor.activity.round.DiagnosisActivity;
import com.imedical.mobiledoctor.activity.round.LisActivity;
import com.imedical.mobiledoctor.activity.round.OrdersActivity;
import com.imedical.mobiledoctor.activity.round.PatientInfoActivity;
import com.imedical.mobiledoctor.activity.round.PatientListActivity;
import com.imedical.mobiledoctor.activity.round.RisActivity;
import com.imedical.mobiledoctor.activity.round.TempratureActivity;
import com.imedical.mobiledoctor.adapter.HisRecordsAdapter;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.entity.SeeDoctorRecord;
import com.imedical.mobiledoctor.util.Validator;
import com.imedical.mobiledoctor.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

public class WardRoundActivity extends BaseRoundActivity implements View.OnClickListener {
    private TextView   tv_name,tv_department,tv_doctorTitle,tv_hisdept,tv_hisrcd,tv_patSwitch;
    private HisRecordsAdapter mHisRecordsAdapter;
    private CircleImageView re_civ_photo;
    private List<SeeDoctorRecord> list = new ArrayList<SeeDoctorRecord>();
    private List<SeeDoctorRecord> listtemp = null;
    private ListView mListViewRecord;
    private PopupWindow hisRecordPopWin;
    private  View ll_switch,ll_1,ll_2,ll_3,ll_4,ll_5,ll_6,ll_7,ll_8,ll_dialog,ll_dialog_orders,ll_dialog_diagnosis,ll_dialog_reports;
    private String title;
    private ImageView iv_dialog_close,iv_open;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ward_round_activity);
        title = this.getIntent().getStringExtra("title");
        Type = this.getIntent().getIntExtra("Type",0);
        InitViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String name=tv_name.getText().toString();
        if(!name.equals(Const.curPat.patName)){ //在其他页面切换患者后返回的页面不会触发OnActivityResult事件，需要同步更新患者信息,OnActivityResult先于onResume()不会重复执行InitViews();
            InitViews();
        }
        if(Const.curSRecorder!=null){
            try{
                tv_hisdept.setText(SysManager.getAdmTypeDesc(Const.curSRecorder.admType) );
                tv_hisrcd.setText(Const.curSRecorder.admDate);
                int i=0;
                for(SeeDoctorRecord temp:Const.SRecorderList){  //同步选择标签
                    if(temp.admId.equals(Const.curSRecorder.admId)){
                        selectPos=i;
                    }
                    i++;
                }
                mHisRecordsAdapter.notifyDataSetChanged();

            }catch (Exception ee){
               showCustom(ee.toString());
            }
        }

    }

    private void showWindow(View parent) {
        if (hisRecordPopWin != null) {
            WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            hisRecordPopWin.showAsDropDown(parent, 0, -10);

        }
    }
    private void InitViews(){
        listtemp=null;//onActivityResult需要初始化
        list.clear();//onActivityResult需要初始化
        if (Validator.isBlank(title)){
            setTitle("住院查房");
        }else {
            setTitle(title);
        }
        ll_dialog=this.findViewById(R.id.ll_dialog);
        ll_dialog_orders=this.findViewById(R.id.ll_dialog_orders);
        ll_dialog_orders.setOnClickListener(this);
        ll_dialog_diagnosis=this.findViewById(R.id.ll_dialog_diagnosis);
        ll_dialog_diagnosis.setOnClickListener(this);
        ll_dialog_reports=this.findViewById(R.id.ll_dialog_reports);
        ll_dialog_reports.setOnClickListener(this);
        iv_open=(ImageView) findViewById(R.id.iv_open);
        iv_open.setOnClickListener(this);
        iv_dialog_close=(ImageView) findViewById(R.id.iv_dialog_close);
        iv_dialog_close.setOnClickListener(this);
        re_civ_photo=(CircleImageView)findViewById(R.id.re_civ_photo);
        tv_name=(TextView) findViewById(R.id.tv_name);
        tv_patSwitch=(TextView) findViewById(R.id.tv_patSwitch);
        tv_patSwitch.setOnClickListener(this);
        tv_hisrcd=(TextView) findViewById(R.id.tv_hisrcd);
        tv_hisdept=(TextView) findViewById(R.id.tv_hisdept);
        ll_1=findViewById(R.id.ll_1);
        ll_1.setOnClickListener(this);
        ll_2=findViewById(R.id.ll_2);
        ll_2.setOnClickListener(this);
        ll_3=findViewById(R.id.ll_3);
        ll_3.setOnClickListener(this);
        ll_4=findViewById(R.id.ll_4);
        ll_4.setOnClickListener(this);
        ll_5=findViewById(R.id.ll_5);
        ll_5.setOnClickListener(this);
        ll_6=findViewById(R.id.ll_6);
        ll_6.setOnClickListener(this);
        ll_7=findViewById(R.id.ll_7);
        ll_7.setOnClickListener(this);
        ll_8=findViewById(R.id.ll_8);
        ll_8.setOnClickListener(this);

        ll_switch=findViewById(R.id.ll_switch);
        ll_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWindow(v);
            }
        });

        tv_department=(TextView) findViewById(R.id.tv_department);
        tv_doctorTitle=(TextView) findViewById(R.id.tv_doctorTitle);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       final View view = layoutInflater.inflate(R.layout.his_record_list, null);
        mListViewRecord = (ListView) view.findViewById(R.id.lv_data_list);
        mHisRecordsAdapter = new HisRecordsAdapter(WardRoundActivity.this, list);
        mListViewRecord.setAdapter(mHisRecordsAdapter);
        mListViewRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                SeeDoctorRecord sr = list.get(position);
                if (hisRecordPopWin != null) {
                    hisRecordPopWin.dismiss();
                }
                Const.curPat.inDate=sr.admDate;
                Const.curPat.admId = sr.admId;
                Const.curPat.admType= sr.admType;
                Const.curSRecorder=sr;
                if (position == 0) {
                    tv_hisdept.setText(SysManager.getAdmTypeDesc(sr.admType) );
                    tv_hisrcd.setText(sr.admDate);
                } else {
                    tv_hisdept.setText(sr.admDept);
                    tv_hisrcd.setText(sr.admDate);
                }
                selectPos = position;
                mHisRecordsAdapter.notifyDataSetChanged();
            }

        });
        InitHisRecord();
        ll_switch.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // TODO Auto-generated method stub
                ll_switch.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int width=ll_switch.getWidth();
                hisRecordPopWin = new PopupWindow(view, width, LinearLayout.LayoutParams.WRAP_CONTENT);
                hisRecordPopWin.setFocusable(true);
                hisRecordPopWin.setOutsideTouchable(true);
                hisRecordPopWin.setBackgroundDrawable(new BitmapDrawable());
            }
        });
        //==========initData============
        tv_name.setText(Const.curPat.patName);
        tv_doctorTitle.setText(Const.curPat.patRegNo);
        if(("女").equals(Const.curPat.patSex)){
            re_civ_photo.setImageDrawable(getDrawable(R.drawable.pat_famale));
        }else {
            re_civ_photo.setImageDrawable(getDrawable(R.drawable.pat_male));
        }
        if(super.Type==0){
            tv_patSwitch.setVisibility(View.VISIBLE);
        }else{
            tv_patSwitch.setVisibility(View.GONE);
            setTitle("患者资料");
        }
    }

    private void InitHisRecord(){
        if(Const.SRecorderList==null){//每个患者全局只执行一次查询,
            SeeDoctorRecord newRcd = new SeeDoctorRecord();
            newRcd.admDate =  Const.curPat.inDate;
            newRcd.admDept =  Const.curPat.departmentName;
            newRcd.admId =  Const.curPat.admId;
            newRcd.admType =  Const.curPat.admType==null?"O":Const.curPat.admType;
            list.add(newRcd);
            LoadHisRecord();
        }else {
            list.addAll(Const.SRecorderList);
            mHisRecordsAdapter = new HisRecordsAdapter(WardRoundActivity.this, list);
            mListViewRecord.setAdapter(mHisRecordsAdapter);
            mHisRecordsAdapter.notifyDataSetChanged();
            if(Const.curSRecorder!=null){
                for(SeeDoctorRecord temp:Const.SRecorderList){
                    if(temp.admId.equals(Const.curSRecorder.admId)){ //退出页面也同步
                        tv_hisdept.setText(SysManager.getAdmTypeDesc(temp.admType) );
                        tv_hisrcd.setText(temp.admDate);
                    }
                }
            }else if(list.size()>0){ //默认
                tv_hisdept.setText(SysManager.getAdmTypeDesc(list.get(0).admType) );
                tv_hisrcd.setText(list.get(0).admDate);            }
        }
    }

    private void LoadHisRecord() {
        showProgress();
        new Thread() {
            @Override
            public void run() {
                try {
                    listtemp = BusyManager.listSeeDoctorRecord(Const.curPat.admId);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (listtemp == null) {
                        listtemp = new ArrayList<SeeDoctorRecord>();
                    } else {
                        list.addAll(listtemp);
                        Const.SRecorderList=new ArrayList<SeeDoctorRecord>();
                        Const.SRecorderList.addAll(list);//每个患者全局加载一次
                    }
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            dismissProgress();
                            mHisRecordsAdapter = new HisRecordsAdapter(WardRoundActivity.this, list);
                            mListViewRecord.setAdapter(mHisRecordsAdapter);
                            mHisRecordsAdapter.notifyDataSetChanged();
                            //默认就诊记录，之后会随着患者选择同步
                            tv_hisdept.setText(SysManager.getAdmTypeDesc(list.get(0).admType) );
                            tv_hisrcd.setText(list.get(0).admDate);
                        }

                    });
                }
            }
        }.start();
    }

    @Override
    public void OnPatientSelected(PatientInfo p) {

    }

    @Override
    public void OnRecordSelected(SeeDoctorRecord sr) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == SWITHC_CODE) {
//            showCustom("101");
            InitViews();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onClick(View view) {
        int vid=view.getId();
        switch (vid){
            case R.id.tv_patSwitch:
                Intent it0 =new Intent(WardRoundActivity.this,PatientListActivity.class);
                it0.putExtra("Type",Type);
                startActivityForResult(it0, SWITHC_CODE);
                break;
            case R.id.ll_1:
                Intent it =new Intent(WardRoundActivity.this,PatientInfoActivity.class);
                it.putExtra("Type",Type);
                this.startActivity(it);
                break;
            case R.id.ll_2:
                Intent it2 =new Intent(WardRoundActivity.this,DiagnosisActivity.class);
                it2.putExtra("Type",Type);
                this.startActivity(it2);
                break;
            case R.id.ll_3:
                Intent it3 =new Intent(WardRoundActivity.this,OrdersActivity.class);
                it3.putExtra("Type",Type);
                this.startActivity(it3);
                break;
            case R.id.ll_4:
                Intent it4 =new Intent(WardRoundActivity.this,LisActivity.class);
                it4.putExtra("Type",Type);
                this.startActivity(it4);
                break;
            case R.id.ll_5:
                Intent it5 =new Intent(WardRoundActivity.this,RisActivity.class);
                it5.putExtra("Type",Type);
                this.startActivity(it5);
                break;
            case R.id.ll_6:
                Intent it6 =new Intent(WardRoundActivity.this,TempratureActivity.class);
                it6.putExtra("Type",Type);
                this.startActivity(it6);
                break;
            case R.id.ll_7:
                Intent ll_7 =new Intent(WardRoundActivity.this,CaseActivity.class);
                ll_7.putExtra("Type",Type);
                this.startActivity(ll_7);
                break;
            case R.id.ll_8:
//                Intent ll_8 =new Intent(WardRoundActivity.this, MultimediaActivity.class);
//                this.startActivity(ll_8);
                break;
            case R.id.iv_open:
                  ll_dialog.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_dialog_close:
                ll_dialog.setVisibility(View.GONE);
                break;
            default:break;
        }
    }
}
