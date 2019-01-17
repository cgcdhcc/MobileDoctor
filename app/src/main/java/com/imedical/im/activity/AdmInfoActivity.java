package com.imedical.im.activity;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.imedical.im.entity.AdmInfo;
import com.imedical.im.service.AdmManager;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.util.DownloadUtil;

import java.util.List;

public class AdmInfoActivity extends BaseActivity {
    public String admId;
    public TextView tv_patientName,tv_patientAge,tv_patientCard,tv_patientId,tv_doctorName
            ,tv_departmentName,tv_doctorTitle,tv_patientContent;
    public GridView gv_img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        admId=this.getIntent().getStringExtra("admId");
        setContentView(R.layout.activity_im_adminfo);
        setTitle("患者自述");
        intiView();
        loadData();
    }

    public void intiView(){
        tv_patientName=findViewById(R.id.tv_patientName);
        tv_patientAge=findViewById(R.id.tv_patientAge);
        tv_patientCard=findViewById(R.id.tv_patientCard);
        tv_patientId=findViewById(R.id.tv_patientId);
        tv_doctorName=findViewById(R.id.tv_doctorName);
        tv_departmentName=findViewById(R.id.tv_departmentName);
        tv_doctorTitle=findViewById(R.id.tv_doctorTitle);
        tv_patientContent=findViewById(R.id.tv_patientContent);
        gv_img=findViewById(R.id.gv_img);
    }
    public void intiData(AdmInfo admInfo){
        tv_patientName.setText(admInfo.patientName);
        tv_patientAge.setText(admInfo.patientAge+" | "+admInfo.patientSex);
        tv_patientCard.setText(admInfo.patientCard);
        tv_patientId.setText(admInfo.patientId);
        tv_doctorName.setText(admInfo.doctorName);
        tv_departmentName.setText(admInfo.departmentName);//
        tv_doctorTitle.setText(" | "+admInfo.doctorTitle);//
        tv_patientContent.setText(admInfo.patientContent);
        if(admInfo.picUrl!=null){
            gv_img.setAdapter(new ImageAdapter(admInfo.picUrl.split(",")));
        }

    }
    public void loadData() {
        showProgress();
        new Thread() {
            List<AdmInfo> templist;
            String msg="加载失败了";
            @Override
            public void run() {
                super.run();
                try{
                    templist= AdmManager.GetAdmInfo(Const.DeviceId, Const.loginInfo.userCode, admId);
                }catch (Exception e){
                    e.printStackTrace();
                    msg=e.getMessage();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(templist!=null&&templist.size()>0){
                            intiData(templist.get(0));
                            dismissProgress();
                        }else{
                            dismissProgress();
                            showToast(msg);
                            finish();
                        }
                    }
                });
            }
        }.start();
    }

    public class ImageAdapter extends BaseAdapter{
        String[] imgpaths;
        public ImageAdapter(String[] imgpaths){
            this.imgpaths=imgpaths;
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
            return null;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view=getLayoutInflater().inflate(R.layout.item_common_img, null);
            ImageView iv_img=view.findViewById(R.id.iv_img);
            DownloadUtil.loadImage(iv_img, imgpaths[i] ,R.drawable.icon , R.drawable.icon,R.drawable.icon);
            return view;
        }
    }


}
