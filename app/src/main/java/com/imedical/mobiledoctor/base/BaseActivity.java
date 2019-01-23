package com.imedical.mobiledoctor.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.BusyManager;
import com.imedical.mobiledoctor.XMLservice.SysManager;
import com.imedical.mobiledoctor.activity.WardRoundActivity;
import com.imedical.mobiledoctor.adapter.HisRecordsAdapter;
import com.imedical.mobiledoctor.entity.SeeDoctorRecord;
import com.imedical.mobiledoctor.util.StatusBarUtils;
import com.imedical.mobiledoctor.widget.CustomProgressDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseActivity extends Activity {
    private CustomProgressDialog progressDialog = null;
    private TextView tv_my,tv_bedNo,tv_infos;
    private ImageView iv_left;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.mobile_blue);
        checkAndSetNetwork();
    }

    protected void showNodata(boolean isNodata,View mView) {
        View v = mView.findViewById(R.id.ll_nodata);
        if (isNodata) {
            mView.findViewById(R.id.ll_content_area).setVisibility(View.GONE);
            v.setVisibility(View.VISIBLE);
        } else {
            mView.findViewById(R.id.ll_content_area).setVisibility(View.VISIBLE);
            v.setVisibility(View.GONE);
        }
    }
    protected void checkAndSetNetwork() {
        try {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo netWrokInfo = manager.getActiveNetworkInfo();
            String info = netWrokInfo.getExtraInfo();
            String type = netWrokInfo.getTypeName();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "网络不可用，请检查手机是否已联网！", Toast.LENGTH_LONG).show();
        }
    }

    //设置标题
    public void setTitle(CharSequence title) {
        iv_left = (ImageView) findViewById(R.id.iv_left);
        tv_my = (TextView) findViewById(R.id.tv_title);
        if (tv_my != null) {
            tv_my.setText(title);
        }
        if (iv_left != null) {
            iv_left.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    finish();
                }

            });
        }
    }

    public void setInfos(CharSequence name,CharSequence infos) {
        tv_bedNo = (TextView) findViewById(R.id.tv_bedNo);
        tv_infos= (TextView) findViewById(R.id.tv_infos);
        if (tv_bedNo != null) {
            tv_bedNo.setText(name);
        }
        if (tv_infos != null) {
            tv_infos.setText(infos);
        }
    }

    protected void showNodataInListView(boolean isNodata) {
        View v = findViewById(R.id.ll_nodata);
        if (isNodata) {
            findViewById(R.id.lv_data).setVisibility(View.GONE);
            v.setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.lv_data).setVisibility(View.VISIBLE);
            v.setVisibility(View.GONE);
        }
    }

    protected void onBackBtnClick() {
        finish();
    }


    public void showExitdialog() {
        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("确定要离开吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog1, int whichButton) {
                        dialog1.dismiss(); // 关闭对话框
                        finish();
                    }
                })
                .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog2, int whichButton) {
                        dialog2.dismiss();
                    }
                }).create();
        dialog.show();
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dismissProgress();
            onBackBtnClick();
        }
        return super.onKeyDown(keyCode, event);
    }

    // 显示对话框
    protected void showDialog(Activity ctx, String mess) {
        try {
            new AlertDialog.Builder(ctx)
                    .setTitle("友情提示")
                    .setMessage(mess)
                    .setNeutralButton("我知道了",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog4,
                                                    int whichButton) {
                                    dialog4.dismiss();
                                }
                            }).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showCustom(String content) {
        LayoutInflater inflater = this.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom,(ViewGroup)this.findViewById(R.id.llToast));
        TextView title = (TextView) layout.findViewById(R.id.tvTitleToast);
        TextView text = (TextView) layout.findViewById(R.id.tvTextToast);
        text.setText(content==null?"网络错误！":content);
        Toast toast= new Toast(this.getApplicationContext());
        toast.setGravity(Gravity.CENTER , 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
    public void showProgress() {
        if ((progressDialog != null) && progressDialog.isShowing()) {
            return;
        } else {
            progressDialog = CustomProgressDialog.createDialog(this);
            progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
                    if (arg1 == KeyEvent.KEYCODE_BACK) {
                        if ((progressDialog != null) && progressDialog.isShowing()) {
                            progressDialog.cancel();
                        }
                    }
                    return false;
                }
            });
            progressDialog.show();
        }
    }

    public void dismissProgress() {
        if ((progressDialog != null) && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    public void  showNoPatDialog(final Activity ctx, final Bundle bundle, final Class classTarget){
        new AlertDialog.Builder(ctx)
                .setTitle("提示信息")
                .setMessage("请先选择病人")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Intent i =  new Intent(ctx,MyPatActivity.class);
//                        String target= classTarget.getName();
//                        i.putExtra("target",target);
//                        if(bundle!=null) {
//                            i.putExtras(bundle);
//                        }
//                        startActivity(i);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    public void showToast(String msg) {
        Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_LONG).show();
    }

//    public void showNodata(boolean bool) {
//        View nodata = findViewById(R.id.ll_nodata);
//        if (bool) {
//            nodata.setVisibility(View.VISIBLE);
//        } else {
//            nodata.setVisibility(View.GONE);
//        }
//    }

//    public void hideIM(View edt) {
//        try {
//            InputMethodManager im = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//            IBinder windowToken = edt.getWindowToken();
//            if (windowToken != null) {
//                im.hideSoftInputFromWindow(windowToken, 0);
//            }
//        } catch (Exception e) {
//            Log.e("HideInputMethod", "failed:" + e.getMessage());
//        }
//    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); // 不能遗漏
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this); // 不能遗漏
    }
}