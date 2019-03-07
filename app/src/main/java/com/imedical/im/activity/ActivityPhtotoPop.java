package com.imedical.im.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.imedical.im.util.FileUtil;
import com.imedical.im.util.PhotoPicker;
import com.imedical.im.util.PopupUtil;
import com.imedical.jpush.activity.MessageDetailActivity;
import com.imedical.jpush.bean.Message;
import com.imedical.jpush.bean.extras;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.util.DateUtil;

import java.io.File;

import cn.jpush.android.api.JPushInterface;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * @author jarrah
 * 
 */
public class ActivityPhtotoPop extends BaseActivity {

	/* sys photo gallery request code
	 */
	public static final int FROM_GALLERY = 0xa2014;


	/**
	 * capture request code
	 */
	public static final int FROM_CAPTURE = FROM_GALLERY + 1;


	/**
	 * sys crop request code
	 */
	public static final int FROM_CROP = FROM_CAPTURE + 1;
	private Dialog dialog;

	protected File captureFile;

	@SuppressLint("InflateParams")
	protected void popup(Context context) {

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View holder = inflater.inflate(R.layout.im_view_popup_button, null, false);
		View gallery = holder.findViewById(R.id.btnPhoto);
		View capture = holder.findViewById(R.id.btnCapture);
		View cancel = holder.findViewById(R.id.btnCanel);

		ButtonClick click = new ButtonClick(this);
		gallery.setOnClickListener(click);
		capture.setOnClickListener(click);
		cancel.setOnClickListener(click);

		dialog = PopupUtil.makePopup(context, holder);
		dialog.show();
	}

	public class ButtonClick implements View.OnClickListener {

		private Activity activity;

		public ButtonClick(Activity activity) {
			this.activity = activity;
		}

		@Override
		public void onClick(View v) {

			if (dialog != null) {
				dialog.dismiss();
			}

			if (v.getId() == R.id.btnPhoto) {
				PhotoPicker.launchGallery(activity, FROM_GALLERY);
			}

			if (v.getId() == R.id.btnCapture) {
				captureFile = FileUtil.getCaptureFile(activity);
				PhotoPicker.launchCamera(activity, FROM_CAPTURE, captureFile);
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {

			if (requestCode == FROM_GALLERY) {
				if (data != null) {
					String path = PhotoPicker
							.getPhotoPathByLocalUri(this, data);
					onGalleryComplete(path);
				}
			} else if (requestCode == FROM_CAPTURE) {
				
				onCaptureComplete(captureFile);
				
			} else if (requestCode == FROM_CROP) {
				if (data != null) {
					Bitmap bitmap = data.getParcelableExtra("data");
					onCropComplete(bitmap);
				}
			}

		}
	}

	protected void onGalleryComplete(String path) {

	}

	protected void onCropComplete(Bitmap bitmap) {

	}

	protected void onCaptureComplete(File captureFile) {

	}
	public void showNotification(String title, String body){
		NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
		style.bigText(Html.fromHtml(body));
		style.setBigContentTitle(title);
		NotificationManager mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(this);
		mNotifyBuilder.setContentTitle(title)//设置通知栏标题
				.setContentText(Html.fromHtml(body)) //设置通知栏显示内容
				.setAutoCancel(true)
				.setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
				.setDefaults(Notification.DEFAULT_SOUND)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
				.setStyle(style)
				.setPriority(Notification.PRIORITY_HIGH)
				.setSmallIcon(R.drawable.icon);//设置通知小ICON
		mNotifyManager.notify((int) System.currentTimeMillis(), mNotifyBuilder.getNotification());
	}
}
