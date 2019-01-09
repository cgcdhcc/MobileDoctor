package com.imedical.mobiledoctor.activity;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.widget.CircleImageView;

public class WardRoundActivity extends BaseActivity {
     TextView   tv_name,tv_department,tv_title;
    CircleImageView re_civ_photo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ward_round_activity);
        InitViews();
    }

    private void InitViews(){
        re_civ_photo=(CircleImageView)findViewById(R.id.re_civ_photo);
        tv_name=(TextView) findViewById(R.id.tv_name);
        tv_department=(TextView) findViewById(R.id.tv_department);
        tv_title=(TextView) findViewById(R.id.tv_title);
        //==========initData============
        tv_name.setText(Const.curPat.patName);
        tv_title.setText(Const.curPat.patRegNo);
        if(Const.curPat.patSex.equals("女")){
            re_civ_photo.setImageDrawable(getDrawable(R.drawable.pat_famale));
        }else {
            re_civ_photo.setImageDrawable(getDrawable(R.drawable.pat_male));
        }
    }
}
