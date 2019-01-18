package com.imedical.mobiledoctor.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.widget.CustomProgressDialog;

import java.util.List;

/**
 * 窗口工具类,提供可重用的窗口
 *
 * @author sszvip
 */
public class DialogUtil {

    private static CustomProgressDialog progressDialogMy;
    private static ProgressDialog progDialog;

    public static void showProgress(Activity ctx, String msg) {
        if (progDialog == null) {
            progDialog = new ProgressDialog(ctx);
        }
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        if (Validator.isBlank(msg)) {
            progDialog.setMessage(msg);
        }
        progDialog.show();
    }

    /**
     * 显示等待条
     */
    public static void showProgress(final Context ctx) {
        try {
            dismissProgress();
            progressDialogMy = CustomProgressDialog.createDialog(ctx);
            // 添加按键监听
            progressDialogMy
                    .setOnKeyListener(new DialogInterface.OnKeyListener() {
                        public boolean onKey(DialogInterface arg0, int arg1,
                                             KeyEvent arg2) {
                            if (arg1 == KeyEvent.KEYCODE_BACK) {

                                if ((progressDialogMy != null)
                                        && progressDialogMy.isShowing()) {
                                    progressDialogMy.cancel();
                                }
                            }
                            return false;
                        }
                    });
            progressDialogMy.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示等待条
     */
    public static void showProgress(final Context ctx, final int inProgressId) {
        try {
            dismissProgress();
            progressDialogMy = CustomProgressDialog.createDialog(ctx);
            progressDialogMy.setProgressId(inProgressId);
            // 添加按键监听
            progressDialogMy
                    .setOnKeyListener(new DialogInterface.OnKeyListener() {
                        public boolean onKey(DialogInterface arg0, int arg1,
                                             KeyEvent arg2) {
                            if (arg1 == KeyEvent.KEYCODE_BACK) {

                                if ((progressDialogMy != null)
                                        && progressDialogMy.isShowing()) {
                                    progressDialogMy.cancel();
                                }
                            }
                            return false;
                        }
                    });
            progressDialogMy.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 隐藏progress
     */
    public static void dismissProgress(int inProgressId) {
        try {
            if ((progressDialogMy != null) && progressDialogMy.isShowing() && progressDialogMy.getProgressId() == inProgressId) {
                progressDialogMy.dismiss();
            }
            if (progDialog != null) {
                progDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏progress
     */
    public static void dismissProgress() {
        try {
            if ((progressDialogMy != null) && progressDialogMy.isShowing()) {
                progressDialogMy.dismiss();
            }
            if (progDialog != null) {
                progDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showToasMsgAsyn(final Activity act, final String msg) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(act, msg, Toast.LENGTH_LONG).show();
            }
        });

    }

    public static void showToasMsg(final Activity act, final String msg) {
        Toast.makeText(act, msg, Toast.LENGTH_LONG).show();
    }

    public static void showAlertOnUIThread(final Activity ctx,
                                           final String msg, final MyCallback callback) {
        ctx.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showAlert(ctx, msg, callback);
            }
        });
    }

    public static void showAlert(final Activity ctx, final String msg,
                                 final MyCallback callback) {
        new AlertDialog.Builder(ctx).setTitle("提示信息")
                .setIcon(android.R.drawable.ic_dialog_alert)
                // 图标
                .setMessage(msg).setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (callback != null) {
                            callback.onCallback(true);
                        }
                        dialog.cancel();
                    }
                }).show();
    }

    public static void showAlertYesOrNoOnUIThread(final Activity ctx,
                                                  final String msg, final MyCallback callback) {
        ctx.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(ctx)
                        .setMessage(msg)
                        // .setTitle("提示信息")
                        .setCancelable(false)
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton) {
                                        callback.onCallback(true);
                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton) {
                                        callback.onCallback(false);
                                        dialog.dismiss();
                                    }
                                }).show();
            }
        });
    }

    public static void showAlertYesOrNo(final Activity ctx, final String title,final String msg,
                                        final MyCallback<Boolean> callback) {
        new AlertDialog.Builder(ctx)
                .setMessage(msg)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        callback.onCallback(true);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        callback.onCallback(false);
                        dialog.dismiss();
                    }
                }).show();
    }


}
