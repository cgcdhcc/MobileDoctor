package com.imedical.jpush;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.util.Log;

import com.google.gson.Gson;
import com.imedical.jpush.activity.MessageDetailActivity;
import com.imedical.jpush.bean.Message;
import com.imedical.jpush.bean.extras;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.util.DateUtil;

import cn.jpush.android.api.JPushInterface;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by dashan on 2017/6/30.
 */

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";
    private NotificationManager nm;
    public Gson gson=new Gson();
    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Log.d(TAG, "JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "接受到推送下来的自定义消息");
            showNotification(context,bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "接受到推送下来的通知");

            receivingNotification(context,bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "用户点击打开了通知");

            openNotification(context,bundle);

        } else {
            Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    private void receivingNotification(Context context, Bundle bundle){
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Log.d(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        Log.d(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.d(TAG, "extras : " + extras);


    }

    private void openNotification(final Context context, Bundle bundle){
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Log.d(TAG, " title : " + title);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.d(TAG, "extras : " + extras);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        Log.d(TAG, "content : " + message);
        String id=bundle.getString(JPushInterface.EXTRA_MSG_ID);
        Log.d(TAG, "messageid : " + id);
        if(extras==null||extras.equals("")){
            return;
        }
        extras=extras.replaceAll("\\[\\]","\"\"");//容错，防止返回的jumpdata中返回[]导致解析失败
        final com.imedical.jpush.bean.extras extra=gson.fromJson(extras,extras.class);
        Intent intent=new Intent(context, MessageDetailActivity.class);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        Message msg=new Message();
        msg.created_at= DateUtil.getNowDateTime(null);
        msg.markread="1";
        msg.content=message;
        msg.extras=extra;
        msg.title=title;
        msg.msg_id=id;
        intent.putExtra("message",msg);
        context.startActivity(intent);
    }
    public void showNotification(Context context, Bundle bundle){
        String id=bundle.getString(JPushInterface.EXTRA_MSG_ID);
        Log.d(TAG, "messageid : " + id);
        String title = bundle.getString(JPushInterface.EXTRA_TITLE);
        Log.d(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        Log.d(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.d(TAG, "extras : " + extras);
        if(extras==null||extras.equals("")){
            return;
        }
        extras=extras.replaceAll("\\[\\]","\"\"");//容错，防止返回的jumpdata中返回[]导致解析失败
        final extras extra=gson.fromJson(extras,extras.class);
        Intent intent=new Intent(context,MessageDetailActivity.class);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        Message msg=new Message();
        msg.created_at= DateUtil.getNowDateTime(null);
        msg.markread="1";
        msg.content=message;
        msg.extras=extra;
        msg.title=title;
        msg.msg_id=id;
        intent.putExtra("message",msg);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
        style.bigText(Html.fromHtml(message));
        style.setBigContentTitle(title);
        NotificationManager mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(context);
        mNotifyBuilder.setContentTitle(title)//设置通知栏标题
                .setContentText(Html.fromHtml(message)) //设置通知栏显示内容
                .setContentIntent(pi) //设置通知栏点击意图
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setDefaults(Notification.DEFAULT_SOUND)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                .setStyle(style)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.icon);//设置通知小ICON
        mNotifyManager.notify((int) System.currentTimeMillis(), mNotifyBuilder.getNotification());
    }
}
