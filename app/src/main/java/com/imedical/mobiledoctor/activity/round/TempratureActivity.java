package com.imedical.mobiledoctor.activity.round;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.SettingManager;
import com.imedical.mobiledoctor.XMLservice.TempManager;
import com.imedical.mobiledoctor.adapter.CommonPopAdapter;
import com.imedical.mobiledoctor.base.BaseRoundActivity;
import com.imedical.mobiledoctor.entity.ActionItem;
import com.imedical.mobiledoctor.entity.LoginInfo;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.entity.SeeDoctorRecord;
import com.imedical.mobiledoctor.entity.TempData;
import com.imedical.mobiledoctor.entity.TempImageFile;

import java.util.ArrayList;
import java.util.List;

public class TempratureActivity extends BaseRoundActivity {
    private CommonPopAdapter popAdapter;
    private String mInfo = "error!";
    private List<TempImageFile> mList = null;
    private List<ActionItem> aiList=new ArrayList<ActionItem>();
    private LinearLayout ll_nodata,ll_date;
    private TextView btn_week;
    private ImageView btn_left,btn_right;
    private WebView webView = null;
    private int mWeekMax = 0;
    private LoginInfo mLogin;
    private TempData mTempData;
    private PopupWindow PopWin;
    ListView popList;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.page6_temprature_activity);
        InitViews();
        InitRecordListAndPatientList(TempratureActivity.this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == SWITHC_CODE) {
            loadData();
        }
        setInfos(Const.curPat.patName,(Const.curPat.bedCode==null?"":Const.curPat.bedCode)+"床("+(Const.curPat.patRegNo==null?"":Const.curPat.patRegNo)+")");//更新姓名，床号
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void OnPatientSelected(PatientInfo p) {
        Intent it0 =new Intent(TempratureActivity.this,PatientListActivity.class);
        startActivityForResult(it0, SWITHC_CODE);
    }

    @Override
    public void OnRecordSelected(SeeDoctorRecord sr) {
        loadData();
    }

    private void InitViews() {
        mLogin = Const.loginInfo;
        this.webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setUseWideViewPort(true);
        ll_nodata = (LinearLayout) findViewById(R.id.ll_nodata);
        ll_date = (LinearLayout) findViewById(R.id.ll_date);
        btn_week = (TextView)findViewById(R.id.btn_week);
        btn_left=(ImageView) findViewById(R.id.btn_left);
        btn_right=(ImageView) findViewById(R.id.btn_right);
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos =Integer.parseInt(btn_week.getTag().toString());
                int weekNum=Integer.parseInt(btn_week.getHint().toString());
                // cur=0 , week=4
                if((pos+1)<mWeekMax)
                {
                    int nowPos =pos+1;
                    int nowWeek =weekNum-1;
                    String.valueOf(nowWeek);
                    btn_week.setHint(String.valueOf(nowWeek));
                    btn_week.setText("第 "+nowWeek+" 周");
                    btn_week.setTag(nowPos);
                    if(mTempData!=null){
                        loadTempViewData();
                    }else{
                        String htmlText = null;
                        TempImageFile t=mList.get(nowPos);
                        List l = new ArrayList<TempImageFile>();
                        l.add(t);
                        htmlText = createHtmlTextWithTag(l);
                        btn_week.setText("第 "+t.weekNo+" 周");
                        btn_week.setTag(nowPos);
                        btn_week.setHint(t.weekNo);
                        webView.loadDataWithBaseURL("about:blank", htmlText,
                                "text/html", "utf-8", null);
                    }
                }
            }
        });
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos =Integer.parseInt(btn_week.getTag().toString());
                int weekNum=Integer.parseInt(btn_week.getHint().toString());
                // pos=1 , week=2
                // pos=0 ,  week =3
                if((pos-1)>=0)
                {
                    int nowPos =pos-1;
                    int nowWeek =weekNum+1;
                    String.valueOf(nowWeek);
                    btn_week.setHint(String.valueOf(nowWeek));
                    btn_week.setText("第 "+nowWeek+" 周");
                    btn_week.setTag(nowPos);
                    if(mTempData!=null){
                        loadTempViewData();
                    }else{
                        String htmlText = null;
                        TempImageFile t=mList.get(nowPos);
                        List l = new ArrayList<TempImageFile>();
                        l.add(t);
                        htmlText = createHtmlTextWithTag(l);
                        btn_week.setText("第 "+t.weekNo+" 周");
                        btn_week.setTag(nowPos);
                        btn_week.setHint(t.weekNo);
                        webView.loadDataWithBaseURL("about:blank", htmlText,
                                "text/html", "utf-8", null);
                    }
                }
            }
        });
        //btn_week.getBackground().setAlpha(100);
        btn_week.setHint("");// 开始为空
        btn_week.setTag(0);
        setTitle("体温单");
        InitPop();
        loadData();
    }

    private void showWindow(View parent) {
        if (PopWin != null) {
            WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            PopWin.showAsDropDown(parent, 0, 0);
        }
    }
    protected void InitPop(){
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.common_pop_list, null);
        popList = (ListView) view.findViewById(R.id.lv_data_list);
        popAdapter = new CommonPopAdapter(TempratureActivity.this, aiList);
        popList.setAdapter(popAdapter);
        popList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                ActionItem actionItem = aiList.get(position);
                if (PopWin != null) {
                    PopWin.dismiss();
                }
                popAdapter.notifyDataSetChanged();
                TempImageFile t = (TempImageFile) actionItem.getAdditionalData();
                btn_week.setText(actionItem.getTitle());
                btn_week.setHint(t.weekNo);// 值
                btn_week.setTag(position);
                String htmlText = null;
                List l = new ArrayList<TempImageFile>();
                l.add(t);
                htmlText = createHtmlTextWithTag(l);
                webView.loadDataWithBaseURL("about:blank", htmlText,
                        "text/html", "utf-8", null);
            }

        });

        btn_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWindow(v);
            }
        });
        btn_week.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // TODO Auto-generated method stub
                btn_week.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int width=btn_week.getWidth();
                PopWin = new PopupWindow(view, width, LinearLayout.LayoutParams.WRAP_CONTENT);
                PopWin.setFocusable(true);
                PopWin.setOutsideTouchable(true);
                PopWin.setBackgroundDrawable(new BitmapDrawable());
            }
        });
    }
    private String createHtmlTextWithTag(List<TempImageFile> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head><meta content='user-scalable = yes, width=device-width,height = device-height,initial-scale=0.5,minimum-scale=0.1,maximum-scale=6.0, user-scalable=yes'  name='viewport' />" +
                "</head><body>");
        if (list == null || list.size() == 0) {
            sb.append("<div height='100%' width='100%' align='center'>体温单不存在！</div>");
        } else {

            for (TempImageFile f : list) {
                sb.append("<div align=center><img src='")
                        .append(f.filePath)
                        .append("' width='100%' align='center' /></div><br/><hr/>");
            }
        }
        sb.append("</body></html>");
        return sb.toString();
    }

    private void loadData() {
        new Thread() {
            public void run() {
                Message msg = new Message();
                try {
                    LoginInfo log = Const.loginInfo;
                    String state = "";
                    if (SettingManager.isIntranet(TempratureActivity.this)) {
                        state = "Intranet";
                    } else {
                        state = "Internet";
                    }
                    mList = TempManager.getPatTempInfo(log.userCode,Const.curPat.admId, state);
                    if (mList.size() > 0) {
                        mWeekMax=mList.size();
                        msg.obj = mList;
                        msg.what = 0;
                    } else {// 绘制曲线
                        msg.what = 2;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = -1;
                    mInfo = e.getMessage();
                } finally {
                    msg.obj = mList;
                    msgHandler.sendMessage(msg);
                }
            }
        }.start();
    }

    // 无体温单图片的时候加载数据画图用，一般都用不上
    public void loadTempViewData() {

    }

    Handler msgHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case 0:
                    if (mList == null || mList.size() == 0) {
                        ll_nodata.setVisibility(View.VISIBLE);
                        ll_date.setVisibility(View.GONE);
                        webView.setVisibility(View.GONE);
                    } else {
                        ll_nodata.setVisibility(View.GONE);
                        ll_date.setVisibility(View.VISIBLE);
                        webView.setVisibility(View.VISIBLE);
                        String htmlText = createHtmlTextWithTag(mList);
                        TempImageFile first =(TempImageFile)mList.get(0);
                        int i=0;
                        for(TempImageFile tf :mList){
                            ActionItem actionItem = new ActionItem(i + 1, "第  " + tf.weekNo + "  周",null, tf);
                            actionItem.setSticky(false);
                            aiList.add(actionItem);
                            i++;
                        }
                        popAdapter = new CommonPopAdapter(TempratureActivity.this, aiList);
                        popList.setAdapter(popAdapter);
                        popAdapter.notifyDataSetChanged();
                        btn_week.setText("第 "+first.weekNo+" 周");
                        btn_week.setHint(first.weekNo);
                        webView.loadDataWithBaseURL("about:blank", htmlText, "text/html", "utf-8", null);
                    }
                    break;
                case 1:
                    ll_nodata.setVisibility(View.GONE);
                    ll_date.setVisibility(View.VISIBLE);
                    webView.setVisibility(View.GONE);
                    break;
                case 2:
                    ll_nodata.setVisibility(View.VISIBLE);
                    webView.setVisibility(View.GONE);
                    ll_date.setVisibility(View.GONE);
                    break;
                default:
                    showCustom(mInfo);
                    break;
            }
        }
    };


}
