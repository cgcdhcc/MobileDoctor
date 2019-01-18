package com.imedical.im.activity;

import android.os.Bundle;

import com.imedical.im.ui.photoview.PhotoView;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.util.DownloadUtil;
import com.imedical.mobiledoctor.util.Validator;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * Created by dashan on 2018/1/30.
 */

public class TalkImageShowActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("查看图片");
        String imgurl=getIntent().getStringExtra("imgurl");
        this.setContentView(R.layout.im_talk_img_show_item);
        PhotoView iv_img=(PhotoView)findViewById(R.id.iv_img);
        if(!Validator.isBlank(imgurl)){
            DownloadUtil.loadImage(iv_img,imgurl,R.drawable.icon,R.drawable.icon,R.drawable.icon);
        }
    }

}
