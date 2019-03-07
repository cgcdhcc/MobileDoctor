package com.imedical.im.util;


import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.WindowManager;
import android.widget.DatePicker;

import java.util.Calendar;


public class DiaLogUtil {
	public static void showCAlertYesOrNo(final Context ctx, final String title,final String msg,
			final MyCallback<Boolean> callback) {
		Builder builder = new Builder(ctx.getApplicationContext());// 初始化弹出窗口工具 
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setNegativeButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				callback.onCallback(true);
			}
		});
		Dialog dialog = builder.create();
		dialog.getWindow()
				.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		dialog.show();
	}
	public static void showAlertYesOrNo(final Activity ctx, final String title,final String msg,
			final MyCallback<Boolean> callback) {
		new Builder(ctx)
				.setMessage(msg)
				.setTitle(title)
				.setCancelable(false)
				.setPositiveButton("确定", new OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						callback.onCallback(true);
						dialog.dismiss();
					}
				})
				.setNegativeButton("取消", new OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						callback.onCallback(false);
						dialog.dismiss();
					}
				}).show();
	}
	public static void showDatePicker(Context context,
			final MyCallback<String> callback) {
		final Calendar cal = Calendar.getInstance();
		DatePickerDialog dp = new DatePickerDialog(context,
				new OnDateSetListener() {
					boolean set = true;

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						if (set) {
							String date = "" + year;
							if (monthOfYear < 10) {
								date = date + "-0" + (monthOfYear + 1);
							} else {
								date = date + "-" + (monthOfYear + 1);
							}
							if (dayOfMonth < 10) {
								date = date + "-0" + dayOfMonth;
							} else {
								date = date + "-" + dayOfMonth;
							}
							callback.onCallback(date);
							set = false;
						}
					}
				}, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH));
		dp.show();
	}



	public static void showAlert(final Activity ctx, final String msg,
								 final MyCallback callback) {
		new Builder(ctx).setTitle("提示信息")
				.setIcon(android.R.drawable.ic_dialog_alert)
				// 图标
				.setMessage(msg).setCancelable(false)
				.setPositiveButton("确定", new OnClickListener() {
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
				new Builder(ctx)
						.setMessage(msg)
						// .setTitle("提示信息")
						.setCancelable(false)
						.setPositiveButton("确定",
								new OnClickListener() {
									public void onClick(DialogInterface dialog,
														int whichButton) {
										callback.onCallback(true);
									}
								})
						.setNegativeButton("取消",
								new OnClickListener() {
									public void onClick(DialogInterface dialog,
														int whichButton) {
										callback.onCallback(false);
										dialog.dismiss();
									}
								}).show();
			}
		});
	}
	public interface MyCallback<T> {

		public void onCallback(T value);
	}
}
