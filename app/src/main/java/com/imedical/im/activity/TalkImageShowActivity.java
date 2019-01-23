package com.imedical.im.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

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
    public ViewPager view_page;
    public String[] imgurl;
    public int position;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgurl=getIntent().getStringArrayExtra("imgurl");
        position=getIntent().getIntExtra("position", 0);
        this.setContentView(R.layout.im_talk_img_show_item);
        setTitle("查看图片");
        view_page=(ViewPager)findViewById(R.id.view_page);
        view_page.setAdapter(new ImageAdapter());
        view_page.setCurrentItem(position);
    }

    public class ImageAdapter extends PagerAdapter {

        public ImageAdapter() {

        }

        @Override
        public int getCount() {
            // 设置成最大，使用户看不到边界
            return imgurl.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // Warning：不要在这里调用removeView
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 对ViewPager页号求模取出View列表中要显示的项

            View view = getLayoutInflater().inflate(R.layout.item_img_view, null);
            PhotoView iv_img = (PhotoView) view.findViewById(R.id.iv_img);
            DownloadUtil.loadImage(iv_img,imgurl[position],R.drawable.im_iconfont_tupian,R.drawable.im_iconfont_tupian,R.drawable.im_iconfont_tupian);
            // 如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
            ViewParent vp = view.getParent();
            if (vp != null) {
                ViewGroup parent = (ViewGroup) vp;
                parent.removeView(view);
            }
            container.addView(view);
            // add listeners here if necessary
            return view;
        }
    }



}
