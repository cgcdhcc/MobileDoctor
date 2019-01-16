package com.imedical.mobiledoctor.activity.round;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.BrowseManager;
import com.imedical.mobiledoctor.XMLservice.SettingManager;
import com.imedical.mobiledoctor.XMLservice.SysManager;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.BrowseLocation;
import com.imedical.mobiledoctor.entity.CateCharpter;
import com.imedical.mobiledoctor.entity.LoginInfo;
import com.imedical.mobiledoctor.entity.PageFile;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.entity.SeeDoctorRecord;
import com.imedical.mobiledoctor.widget.TabView;

import java.util.ArrayList;
import java.util.List;

public class CaseActivity extends BaseRoundActivity {
    private String mInfo = "error!";
    private BrowseLocation mBrowseLocation=null;
    private LayoutInflater mInflater;
    private LinearLayout layout_btn;
    private View scr_browse ;
    private WebView webView_case_report=null;
    private LinearLayout ll_nodata;
    private List<TabView> listBtn;
    private int oldSel=-1;
    private ImageView iv_nore = null ;
    private LoginInfo mLogin;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.page7_case_activity);
        InitViews();
        InitRecordList(CaseActivity.this);
    }

    @Override
    public void OnPatientSelected(PatientInfo p) {

    }

    @Override
    public void OnRecordSelected(SeeDoctorRecord sr) {
        loadData();
    }


    private void InitViews() {

        mLogin = Const.loginInfo;
        this.webView_case_report = (WebView)findViewById(R.id.webView_case_report);
        this.scr_browse = findViewById(R.id.lay_browse);
        ll_nodata= (LinearLayout) findViewById(R.id.ll_nodata);
        webView_case_report.getSettings().setSupportZoom(true);
        webView_case_report.getSettings().setBuiltInZoomControls(true);
        webView_case_report.getSettings().setSupportZoom(true);
        webView_case_report.getSettings().setBuiltInZoomControls(true);
        webView_case_report.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView_case_report.getSettings().setJavaScriptEnabled(true);
        this.layout_btn = (LinearLayout) findViewById(R.id.lay_browse);
        this.iv_nore  = (ImageView)findViewById(R.id.iv_no_re);
        this.listBtn = new ArrayList<TabView>();
        setTitle("电子病历");
        loadData();
    }

    private void loadData() {
        showProgress();
        new Thread(){
            public void run(){
                Message msg = new Message();
                try {
                    LoginInfo u = Const.loginInfo;
                    String netType = "";
                    Log.d("msg", u.userCode+"   "+Const.curPat.admId+"   "+Const.curPat.patName+"   "+netType);
                    if(SettingManager.isIntranet(CaseActivity.this)) {
                        netType = "Intranet";
                    } else {
                        netType = "Internet";
                    }
                    mBrowseLocation = BrowseManager.getBrowseLocationInfo(u.userCode,Const.curPat.admId,netType);
                    msg.what = 0 ;
                    mInfo = "success!";
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = -1 ;
                    mInfo = e.getMessage();
                }finally{
                    msgHandler.sendMessage(msg);
                }
            }
        }.start();
    }

    Handler msgHandler = new Handler(){
        public void handleMessage(Message msg){
            dismissProgress();
            int what = msg.what;
            switch (what) {
                case 0:
                    iv_nore.setVisibility(View.GONE);
                    scr_browse.setVisibility(View.VISIBLE);
                    webView_case_report.setVisibility(View.VISIBLE);
                    if(mBrowseLocation.CateCharpterList!=null || mBrowseLocation.CateCharpterList.size()==0){
                        addTabsToLeft();
                    }
                    if("ONEURL".equals(mBrowseLocation.browseType)){
                        Log.d("msg","URL:"+mBrowseLocation.fileServerLocation);
                        webView_case_report.loadUrl(mBrowseLocation.fileServerLocation);
                    }else{
                        String htmlText = createHtmlTextWithTag(0);//默认先中第一个
                        Log.d("msg","URL:"+htmlText);
                        webView_case_report.loadDataWithBaseURL("about:blank", htmlText, "text/html", "utf-8", null);
                    }
                    break;

                default:
                    iv_nore.setVisibility(View.VISIBLE);
                    scr_browse.setVisibility(View.GONE);
                    webView_case_report.setVisibility(View.GONE);
                    showCustom(mInfo);
                    break;
            }

        }

    };

    protected void addTabsToLeft() {
        for(int i=0;i<mBrowseLocation.CateCharpterList.size();i++){
            CateCharpter c = mBrowseLocation.CateCharpterList.get(i);
            TabView tabView =new TabView(CaseActivity.this,c.cateCharpterName,btnClicked);
            tabView.setTag(i);
            listBtn.add(tabView);
            layout_btn.addView(tabView);
        }
        if(listBtn.size() > 0) {
            webView_case_report.setVisibility(View.VISIBLE);
            setUIState(listBtn.get(0),0);
        }
    }
    private void setUIState(TabView v, int tag) {
        v.SetFoucse();
        oldSel = tag;
        String htmlText = createHtmlTextWithTag(tag);//默认先中第一个
        webView_case_report.loadDataWithBaseURL("about:blank", htmlText, "text/html", "utf-8", null);
    }

    private String createHtmlTextWithTag(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>");
        List<CateCharpter> list = mBrowseLocation.CateCharpterList ;
        if(list==null || list.size()==0){
            ll_nodata.setVisibility(View.VISIBLE);
            webView_case_report.setVisibility(View.GONE);
            //显示nodata界面
//			sb.append("<div height='100%' width='100%' align='center'>电子病历不存在！</div>") ;
        }else{
            ll_nodata.setVisibility(View.GONE);
            webView_case_report.setVisibility(View.VISIBLE);
            CateCharpter c = list.get(i);
            for(PageFile f:c.PageFileList){
                sb.append("<div align=center><img src='")
                        .append(mBrowseLocation.fileServerLocation).append("/").append(f.fileLocation)
                        .append("' width='100%' align='center' /></div><br/><hr/>");
            }
        }
        sb.append("</body></html>");
        return sb.toString();
    }

    public View.OnClickListener btnClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tag = (Integer) v.getTag();
            if(oldSel == tag){
                return;
            }
            if(oldSel>=0){
                for( TabView btn :listBtn){
                    btn.SetUnFoucse();
                }
                TabView btn = listBtn.get(tag);
                setUIState(btn, tag);
            }
        }

    };
}
