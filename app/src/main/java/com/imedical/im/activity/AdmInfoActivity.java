package com.imedical.im.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imedical.im.entity.AdmInfo;
import com.imedical.im.service.AdmManager;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.BusyManager;
import com.imedical.mobiledoctor.activity.WardRoundActivity;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.util.DownloadUtil;
import com.imedical.mobiledoctor.util.Validator;

import java.util.List;

public class AdmInfoActivity extends BaseActivity {
    public String admId,Type=null;
    public TextView tv_patientName, tv_patientAge, tv_patientCard, tv_doctorName, tv_departmentName, tv_doctorTitle, tv_patientContent;
    public TextView tv_complaintStr_Item1, tv_complaintStr_Item2, tv_complaintStr_Item3, tv_complaintStr_Item4, tv_complaintStr_Item5,btn_dzbl,btn_diagnosis;
    public View ll_operation;
    public GridView gv_img;
    public int mScreenWidth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        admId = this.getIntent().getStringExtra("admId");
        setContentView(R.layout.activity_im_adminfo);
        Type=this.getIntent().getStringExtra("Type");//默认是不带操作界面的，
        setTitle("患者自述");
        intiView();
        loadData();
    }

    public void intiView() {
        Display display = getWindowManager().getDefaultDisplay();
        mScreenWidth = display.getWidth();
        ll_operation=findViewById(R.id.ll_operation);
        if(Type==null){
            ll_operation.setVisibility(View.GONE);
        }else {
            ll_operation.setVisibility(View.VISIBLE);
        }
        btn_dzbl=findViewById(R.id.btn_dzbl);
        btn_dzbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPatinfo(admId);
            }
        });
        btn_diagnosis=findViewById(R.id.btn_diagnosis);
        btn_diagnosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(AdmInfoActivity.this, AddDiagnosisActivity.class);
                        it.putExtra("admId", admId);
                        startActivity(it);
            }
        });
        tv_patientName = findViewById(R.id.tv_patientName);
        tv_patientAge = findViewById(R.id.tv_patientAge);
        tv_patientCard = findViewById(R.id.tv_patientCard);
        tv_doctorName = findViewById(R.id.tv_doctorName);
        tv_departmentName = findViewById(R.id.tv_departmentName);
        tv_doctorTitle = findViewById(R.id.tv_doctorTitle);
        tv_patientContent = findViewById(R.id.tv_patientContent);
        gv_img = findViewById(R.id.gv_img);

        tv_complaintStr_Item1 = findViewById(R.id.tv_complaintStr_Item1);
        tv_complaintStr_Item2 = findViewById(R.id.tv_complaintStr_Item2);
        tv_complaintStr_Item3 = findViewById(R.id.tv_complaintStr_Item3);
        tv_complaintStr_Item4 = findViewById(R.id.tv_complaintStr_Item4);
        tv_complaintStr_Item5 = findViewById(R.id.tv_complaintStr_Item5);
    }

    public void loadPatinfo(final String admId) {
        showProgress();
        new Thread() {
            PatientInfo patientInfo;

            public void run() {
                try {
                    patientInfo = BusyManager.loadPatientInfo(Const.loginInfo.userCode, admId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        if (patientInfo != null) {
                            Const.curPat = patientInfo;
                            Const.curSRecorder = null;
                            Const.SRecorderList = null;
                            Intent intent = new Intent(AdmInfoActivity.this, WardRoundActivity.class);
                            intent.putExtra("title", "患者资料");
                            startActivity(intent);
                        } else {
                            showToast("获取患者信息失败");
                        }
                    }
                });
            }

            ;
        }.start();
    }

    public void intiData(final AdmInfo admInfo) {
        tv_patientName.setText(admInfo.patientName);
        tv_patientAge.setText(admInfo.patientAge + " | " + admInfo.patientSex);
        tv_patientCard.setText(admInfo.patientId);
        tv_doctorName.setText(admInfo.doctorName);
        tv_departmentName.setText(admInfo.departmentName);//
        tv_doctorTitle.setText(" | " + admInfo.doctorTitle);//
        tv_patientContent.setText(admInfo.patientContent);

        if (!Validator.isBlank(admInfo.complaintStr)) {
            String[] items = admInfo.complaintStr.split(";");
            for (int i = 0; i < items.length; i++) {
                if (!Validator.isBlank(items[i])) {
                    String details[] = items[i].split(":");
                    if (details != null && details.length == 2) {
                        switch (details[0]) {
                            case "Item1":
                                tv_complaintStr_Item1.setText(details[1]);
                                break;
                            case "Item2":
                                tv_complaintStr_Item2.setText(details[1]);
                                break;
                            case "Item3":
                                tv_complaintStr_Item3.setText(details[1]);
                                break;
                            case "Item4":
                                tv_complaintStr_Item4.setText(details[1]);
                                break;
                            case "Item5":
                                tv_complaintStr_Item5.setText(details[1]);
                                break;
                        }
                    }
                }
            }
        }

        if (admInfo.picUrl != null) {
            gv_img.setAdapter(new ImageAdapter(admInfo.picUrl.split(",")));
            gv_img.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String[] imgurl= admInfo.picUrl.split(",");
                    for(int m = 0;m<imgurl.length;m++){
                        imgurl[m]=Const.IMG_URL +imgurl[m];
                    }
                    Intent intent = new Intent(AdmInfoActivity.this, TalkImageShowActivity.class);
                    intent.putExtra("imgurl", imgurl);
                    intent.putExtra("position", i);
                    startActivity(intent);
                }
            });
        }

    }

    public void loadData() {
        showProgress();
        new Thread() {
            List<AdmInfo> templist;
            String msg = "加载失败了";

            @Override
            public void run() {
                super.run();
                try {
                    templist = AdmManager.GetAdmInfo(Const.DeviceId, Const.loginInfo.userCode, admId);
                } catch (Exception e) {
                    e.printStackTrace();
                    msg = e.getMessage();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (templist != null && templist.size() > 0) {
                            intiData(templist.get(0));
                            dismissProgress();
                        } else {
                            dismissProgress();
                            showToast(msg);
                            finish();
                        }
                    }
                });
            }
        }.start();
    }

    public class ImageAdapter extends BaseAdapter {
        String[] imgpaths;

        public ImageAdapter(String[] imgpaths) {
            this.imgpaths = imgpaths;
        }

        @Override
        public int getCount() {
            return imgpaths.length;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return imgpaths[i];
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.item_common_img, null);
            ImageView iv_img = view.findViewById(R.id.iv_img);
            ViewGroup.LayoutParams para = iv_img.getLayoutParams();
            para.height = (mScreenWidth-30)/3;
            iv_img.setLayoutParams(para);
            DownloadUtil.loadImage(iv_img, Const.IMG_URL+ imgpaths[i], R.drawable.im_iconfont_tupian, R.drawable.im_iconfont_tupian, R.drawable.im_iconfont_tupian);
            return view;
        }
    }



}
