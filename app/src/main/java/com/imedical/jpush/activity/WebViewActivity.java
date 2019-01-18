package com.imedical.jpush.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.imedical.mobiledoctor.R;


/**
 * Created by dashan on 2017/3/9.
 */

public class WebViewActivity extends Activity {
    public WebView view_wb;
    public ProgressBar progress;
    public String url;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_webview);
        url=this.getIntent().getStringExtra("url");
        view_wb=(WebView)findViewById(R.id.view_wb);
        progress=(ProgressBar)findViewById(R.id.progress);
        view_wb.getSettings().setJavaScriptEnabled(true);
        view_wb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        view_wb.getSettings().setAllowFileAccess(true);
        view_wb.getSettings().setAppCacheEnabled(true);
        view_wb.getSettings().setBlockNetworkImage(false);
        view_wb.getSettings().setDomStorageEnabled(true);//开启dom缓存
        view_wb.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }
        });
        view_wb.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if (newProgress == 100) {
                    // 网页加载完成
                    progress.setVisibility(View.GONE);
                } else {
                    // 加载中
                    progress.setVisibility(View.VISIBLE);
                }


            }
        });
        view_wb.loadUrl(url);
    }
    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {
            if(view_wb.canGoBack())
            {
                view_wb.goBack();//返回上一页面
                return true;
            }
            else
            {
            	this.finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
