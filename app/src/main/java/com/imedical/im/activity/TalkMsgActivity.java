package com.imedical.im.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.imedical.im.ImConst;
import com.imedical.im.SocketService;
import com.imedical.im.dialog.ShowMemoDlg;
import com.imedical.im.entity.AdmInfo;
import com.imedical.im.entity.DoctorTemplate;
import com.imedical.im.entity.MessageInfo;
import com.imedical.im.entity.PatTemplate;
import com.imedical.im.entity.UserFriend;
import com.imedical.im.media.RecordButton;
import com.imedical.im.service.AdmManager;
import com.imedical.mobiledoctor.AppConfig;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.BusyManager;
import com.imedical.mobiledoctor.activity.WardRoundActivity;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.util.DateUtil;
import com.imedical.mobiledoctor.util.DialogUtil;
import com.imedical.mobiledoctor.util.FileDataUtil;
import com.imedical.mobiledoctor.util.ImageUtil;
import com.imedical.mobiledoctor.util.MyCallback;
import com.imedical.mobiledoctor.util.PreferManager;
import com.imedical.mobiledoctor.util.Validator;

import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import io.socket.client.Ack;

public class TalkMsgActivity extends ActivityPhtotoPop {
    public ListView list_data;
    public TextView tv_title;
    public EditText et_msg;
    public TalkMsgAdapter ta;
    public View ll_talk;
    public String login_action = "com.dashan.Broadcast.login";
    private final String connect_action = "com.dashan.Broadcast.connect";
    public List<MessageInfo> data_list = new ArrayList<MessageInfo>();
    public MyBroadcastReceiver reciver;
    public String action = "";
    public UserFriend userfriend;
    private Button btn_send;
    private View btn_img_voice, btn_img_photo, btn_img_patientinfo, btn_img_add_diagonsis, keybord_button;
    public RecordButton mRecordButton;
    public Intent IoService;
    public TextView chat_status;
    public boolean isonline = false;
    public AdmInfo admInfo;
    public TextView tv_orderchatstatus;
    public TextView tv_stopserver;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.im_activity_talklist);
        admInfo = (AdmInfo) this.getIntent().getSerializableExtra("admInfo");
        userfriend = new UserFriend();
        userfriend.friendUsername = admInfo.admId;
        userfriend.friendNickName = admInfo.patientName;
        userfriend.ownerUsername = admInfo.docMarkId;
        PreferManager.saveValue("docMarkId",  admInfo.docMarkId);
        action = userfriend.friendUsername + "_chat";
        reciver = new MyBroadcastReceiver();
        IntentFilter intentf = new IntentFilter();
        intentf.addAction(action);
        intentf.addAction(connect_action);
        intentf.addAction(login_action);
        registerReceiver(reciver, intentf);
        list_data = (ListView) findViewById(R.id.list_data);
        setTitle(userfriend.friendNickName);
        et_msg = (EditText) findViewById(R.id.et_msg);
        ta = new TalkMsgAdapter(this, data_list, admInfo);
        list_data.setAdapter(ta);
        String path = AppConfig.FILE_PATH + "/" + createVoiceName();
        File file = new File(AppConfig.FILE_PATH);
        if (!file.exists()) {
            Log.d("msg", "文件夹不存在，生成文件夹");
            if (file.mkdirs())
                Log.d("msg", "文件创建成功");
            else
                Log.d("msg", "文件创建失败");
        }
        mRecordButton = (RecordButton) findViewById(R.id.record_button);
        mRecordButton.setSavePath(path);
        mRecordButton
                .setOnFinishedRecordListener(new RecordButton.OnFinishedRecordListener() {

                    @Override
                    public void onFinishedRecord(String audioPath, int time) {
                        Log.i("RECORD!!!", "finished!!!!!!!!!! save to "
                                + audioPath);
                        if (audioPath != null) {
                            savefile(audioPath, "audio");
                        }
                    }
                });

        mRecordButton.setSavePath(path);
        btn_send = (Button) findViewById(R.id.btn_send);
        et_msg = (EditText) findViewById(R.id.et_msg);
        et_msg.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                if (et_msg.getText().toString().trim().equals("")
                        || et_msg.getText() == null) {
                    btn_send.setVisibility(View.GONE);
                } else {
                    btn_send.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub

            }

        });
        btn_send.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendTxtData();
            }
        });
        btn_img_voice = findViewById(R.id.btn_img_voice);
        btn_img_voice.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                keybord_button.setVisibility(View.VISIBLE);
                et_msg.setVisibility(View.GONE);
                et_msg.setVisibility(View.GONE);
                mRecordButton.setVisibility(View.VISIBLE);
            }
        });
        keybord_button = findViewById(R.id.keybord_button);
        keybord_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                keybord_button.setVisibility(View.GONE);
                et_msg.setVisibility(View.VISIBLE);
                mRecordButton.setVisibility(View.GONE);
            }
        });
        btn_img_photo = findViewById(R.id.btn_img_photo);
        btn_img_photo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                popup(TalkMsgActivity.this);
            }
        });
        btn_img_patientinfo = findViewById(R.id.btn_img_patientinfo);
        btn_img_patientinfo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                loadPatinfo();
            }
        });
        btn_img_add_diagonsis = findViewById(R.id.btn_img_add_diagonsis);
        btn_img_add_diagonsis.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(TalkMsgActivity.this, AddDiagnosisActivity.class);
                intent.putExtra("admId", admInfo.admId);
                startActivityForResult(intent, 101);
            }
        });
        ll_talk = findViewById(R.id.ll_talk);
        chat_status = (TextView) findViewById(R.id.chat_status);
        tv_stopserver = (TextView) findViewById(R.id.tv_stopserver);
        tv_stopserver.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.showAlertYesOrNo(TalkMsgActivity.this, "操作确认", "确定结束服务吗？服务结束后不能再发送聊天消息", new MyCallback<Boolean>() {
                    @Override
                    public void onCallback(Boolean value) {
                        if(value){
                            updatechatstatus("2");
                        }
                    }
                });

            }
        });
        tv_orderchatstatus = (TextView) findViewById(R.id.tv_orderchatstatus);
        loadData();
        intiChat();
    }
    //初始化聊天信息
    public void intiChat(){
//        if("0".equals(admInfo.chatStatus)){//等待咨询
//            if(IoService==null){
//                IoService = new Intent(this, SocketService.class);
//                startService(IoService);
//            }
//            tv_orderchatstatus.setVisibility(View.GONE);
//            tv_stopserver.setVisibility(View.VISIBLE);
//            ll_talk.setVisibility(View.VISIBLE);
//            tv_stopserver.setText("确认服务");
//            if(Validator.isBlank(PreferManager.getValue("sure_noshow"))){
//                ShowMemoDlg dlg=new ShowMemoDlg(this, new ShowMemoDlg.MyCallBack() {
//                    @Override
//                    public void callback(boolean noshow) {
//                        if(noshow){
//                            PreferManager.saveValue("sure_noshow","Y");
//                        }
//                    }
//                });
//                dlg.show();
//            }
//        }else
        if(!"2".equals(admInfo.chatStatus)){//咨询中
            if(IoService==null){
                IoService = new Intent(this, SocketService.class);
                startService(IoService);
            }
            tv_stopserver.setText("结束服务");
            tv_orderchatstatus.setVisibility(View.GONE);
            tv_stopserver.setVisibility(View.VISIBLE);
            ll_talk.setVisibility(View.VISIBLE);
        }else{
            if(IoService!=null){
                stopService(IoService);
                IoService=null;
            }
            tv_orderchatstatus.setVisibility(View.VISIBLE);
            tv_stopserver.setVisibility(View.GONE);
            ll_talk.setVisibility(View.GONE);
            chat_status.setVisibility(View.GONE);
        }
    }
    public synchronized void loadData() {
        data_list.clear();
        PatTemplate patTemplate = new PatTemplate(admInfo.admId, admInfo.patientAge, admInfo.patientSex, admInfo.patientName);
        data_list.add(new MessageInfo("template", userfriend.friendUsername, userfriend.ownerUsername, DateUtil.getNowTimeMillis(), new Gson().toJson(patTemplate), 1, 1));
        data_list.addAll(LitePal.limit(50).where("(fromUser=? and toUser=?) or (fromUser=? and toUser=?)", userfriend.ownerUsername, userfriend.friendUsername, userfriend.friendUsername, userfriend.ownerUsername).order("timeStamp asc").find(MessageInfo.class));
        Log.d("msg", "获取消息记录：" + data_list.size());
        Log.d("msg",new Gson().toJson(data_list) );
        ta.notifyDataSetChanged();
        list_data.setSelection(data_list.size() - 1);
    }



    public void sendTemplateData(final String content, final int templateId) {


        new Thread() {
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("messageType", "template");
                    jsonObject.put("toUser", userfriend.friendUsername);
                    jsonObject.put("fromUser",userfriend.ownerUsername);
                    jsonObject.put("appId", ImConst.appId);
                    jsonObject.put("content", content);
                    jsonObject.put("templateId", templateId);
                    jsonObject.put( "extend","D");
                    final MessageInfo messageInfo = new MessageInfo("template", userfriend.ownerUsername, userfriend.friendUsername, DateUtil.getNowTimeMillis(), content, templateId, 0);
                    messageInfo.save();
                    data_list.add(messageInfo);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ta.notifyDataSetChanged();
                            list_data.setSelection(data_list.size() - 1);
                        }
                    });
                    ImConst.mSocket.emit("chat", jsonObject, new Ack() {
                        @Override
                        public void call(Object... args) {
                            messageInfo.delete();
                            Log.d("msg", "" + args.length);
                            MessageInfo mi = new Gson().fromJson(args[0].toString(), MessageInfo.class);
                            mi.setSended(1);
                            mi.save();
                            data_list.add(mi);
                            data_list.remove(messageInfo);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ta.notifyDataSetChanged();
                                    list_data.setSelection(data_list.size() - 1);
                                    if("0".equals(admInfo.chatStatus)){
                                        updatechatstatus("1");
                                    }
                                }
                            });

                        }
                    });
                } catch (Exception e) {
                    Log.d("msg", "发送异常");
                    e.printStackTrace();
                    dismissProgress();
                }
            }

            ;
        }.start();
    }

    public void sendTxtData() {
        final String content = et_msg.getText() == null ? "" : et_msg
                .getText().toString();
        if (Validator.isBlank(content)) {
            showToast("请输入内容");
            return;
        }
        et_msg.setText("");

        new Thread() {
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("messageType", "txt");
                    jsonObject.put("toUser", userfriend.friendUsername);
                    jsonObject.put("fromUser", userfriend.ownerUsername);
                    jsonObject.put("appId", ImConst.appId);
                    jsonObject.put("content", content);
                    jsonObject.put( "extend","D");
                    final MessageInfo messageInfo = new MessageInfo("txt", content, userfriend.ownerUsername, userfriend.friendUsername, DateUtil.getNowTimeMillis(), "", "");
                    messageInfo.save();
                    data_list.add(messageInfo);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ta.notifyDataSetChanged();
                            list_data.setSelection(data_list.size() - 1);
                        }
                    });
                    ImConst.mSocket.emit("chat", jsonObject, new Ack() {
                        @Override
                        public void call(Object... args) {
                            messageInfo.delete();
                            Log.d("msg", "" + args.length);
                            MessageInfo mi = new Gson().fromJson(args[0].toString(), MessageInfo.class);
                            mi.setSended(1);
                            mi.save();
                            data_list.add(mi);
                            data_list.remove(messageInfo);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ta.notifyDataSetChanged();
                                    list_data.setSelection(data_list.size() - 1);
                                    if("0".equals(admInfo.chatStatus)){
                                        updatechatstatus("1");
                                    }
                                }
                            });

                        }
                    });
                } catch (Exception e) {
                    Log.d("msg", "发送异常");
                    e.printStackTrace();
                    dismissProgress();
                }
            }

            ;
        }.start();
    }


    public void sendFinishText(){
        new Thread() {
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("toUser", userfriend.friendUsername);
                    jsonObject.put("fromUser", userfriend.ownerUsername);
                    jsonObject.put("appId", ImConst.appId);
                    jsonObject.put( "extend","D");
                    jsonObject.put("messageType", "system");
                    jsonObject.put("content", "问诊已结束");
                    ImConst.mSocket.emit("chatFinish", jsonObject, new Ack() {
                        @Override
                        public void call(Object... args) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    intiChat();
                                }
                            });

                        }
                    });
                } catch (Exception e) {
                    Log.d("msg", "发送异常");
                    e.printStackTrace();
                    dismissProgress();
                }
            }

            ;
        }.start();

    }


    @Override
    protected void onCaptureComplete(File captureFile) {
        if (captureFile != null) {
            savefile(captureFile.getAbsolutePath(), "img");
        }

    }

    // 发送图片音频信息
    public void savefile(final String path, final String messageType) {

        new Thread() {
            public void run() {
                try {
                    File file = new File(path);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("messageType", messageType);
                    jsonObject.put("toUser", userfriend.friendUsername);
                    jsonObject.put("fromUser", userfriend.ownerUsername);
                    jsonObject.put("appId", ImConst.appId);
                    jsonObject.put("fileName", file.getName());
                    jsonObject.put( "extend","D");
                    if(messageType.equals("img")){
                        file = new Compressor(TalkMsgActivity.this).compressToFile(file);
                    }
                    byte[] bytes=FileDataUtil.getBytesFromFile(file);
                    Log.d("msg", "长度："+bytes.length);
                    jsonObject.put("fileData", bytes);
                    if (messageType.equals("audio")) {
                        jsonObject.put("duration", "20");
                        jsonObject.put("size", "2048");
                    }
                    final MessageInfo messageInfo = new MessageInfo(messageType, "", userfriend.ownerUsername, userfriend.friendUsername, DateUtil.getNowTimeMillis(), path, path);
                    messageInfo.save();
                    data_list.add(messageInfo);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ta.notifyDataSetChanged();
                            list_data.setSelection(data_list.size() - 1);
                        }
                    });
                    ImConst.mSocket.emit("chat", jsonObject, new Ack() {
                        @Override
                        public void call(Object... args) {
                            messageInfo.delete();
                            Log.d("msg", args[0].toString());
                            MessageInfo mi = new Gson().fromJson(args[0].toString(), MessageInfo.class);
                            mi.setSended(1);
                            mi.save();
                            data_list.add(mi);
                            data_list.remove(messageInfo);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ta.notifyDataSetChanged();
                                    list_data.setSelection(data_list.size() - 1);
                                    if("0".equals(admInfo.chatStatus)){
                                        updatechatstatus("1");
                                    }
                                }
                            });

                        }
                    });
                } catch (Exception e) {
                    Log.d("msg", "发送异常");
                    e.printStackTrace();
                    dismissProgress();
                }
            }

            ;

        }.start();
    }

    @Override
    protected void onGalleryComplete(String path) {

        if (path != null) {
            savefile(path, "img");
        }
    }

    class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (intent.getAction().equals(action)) {
                loadData();
            } else if (intent.getAction().equals(login_action)) {
                final boolean login = intent.getBooleanExtra("login", false);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chat_status.setVisibility(View.VISIBLE);
                        if (login) {
                            chat_status.setText("可以发送消息了");
                            ll_talk.setVisibility(View.VISIBLE);
                            isonline = true;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    chat_status.setVisibility(View.GONE);
                                    sendOffLineMsg();
                                }
                            }, 5000);
                        } else {
                            chat_status.setText("登录认证失败，请稍后再试");
                        }
                    }
                });
            } else if (intent.getAction().equals(connect_action)) {
                final boolean connect = intent.getBooleanExtra("connect", false);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chat_status.setVisibility(View.VISIBLE);
                        if (connect) {
                            chat_status.setText("连接建立成功");
                        } else {
                            isonline = false;
                            if("2".equals(admInfo.chatStatus)){
                                chat_status.setText("服务已结束");
                            }else{
                                chat_status.setText("连接失败，请检查网络");
                            }
                        }
                    }
                });
            }

        }

    }


    public void sendOffLineMsg() {
        List<MessageInfo> offlist = LitePal.limit(1).where("sended=?", "0").order("timeStamp asc").find(MessageInfo.class);
        for (int i = 0; i < offlist.size(); i++) {
            if (offlist.get(i).messageType.equals("txt")) {
                sendTxtData(offlist.get(i));
            } else if (offlist.get(i).messageType.equals("template")) {
                sendTemplateData(offlist.get(i));
            } else {
                savefile(offlist.get(i));
            }
        }
    }


    public void sendTemplateData(final MessageInfo messageInfo) {

        new Thread() {
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("messageType", messageInfo.messageType);
                    jsonObject.put("toUser", messageInfo.toUser);
                    jsonObject.put("fromUser", messageInfo.fromUser);
                    jsonObject.put("appId", ImConst.appId);
                    jsonObject.put("content", messageInfo.content);
                    jsonObject.put("templateId", messageInfo.templateId);
                    jsonObject.put( "extend","D");
                    ImConst.mSocket.emit("chat", jsonObject, new Ack() {
                        @Override
                        public void call(Object... args) {
                            messageInfo.delete();
                            MessageInfo mi = new Gson().fromJson(args[0].toString(), MessageInfo.class);
                            mi.setSended(1);
                            mi.save();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if("0".equals(admInfo.chatStatus)){
                                        updatechatstatus("1");
                                    }
                                    loadData();
                                    sendOffLineMsg();
                                }
                            });

                        }
                    });
                } catch (Exception e) {
                    Log.d("msg", "发送异常");
                    e.printStackTrace();
                    dismissProgress();
                }
            }

            ;
        }.start();
    }

    public void sendTxtData(final MessageInfo messageInfo) {

        new Thread() {
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("messageType", "txt");
                    jsonObject.put("toUser", messageInfo.toUser);
                    jsonObject.put("fromUser", messageInfo.fromUser);
                    jsonObject.put("appId", ImConst.appId);
                    jsonObject.put("content", messageInfo.content);
                    jsonObject.put( "extend","D");
                    ImConst.mSocket.emit("chat", jsonObject, new Ack() {
                        @Override
                        public void call(Object... args) {
                            messageInfo.delete();
                            MessageInfo mi = new Gson().fromJson(args[0].toString(), MessageInfo.class);
                            mi.setSended(1);
                            mi.save();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if("0".equals(admInfo.chatStatus)){
                                        updatechatstatus("1");
                                    }
                                    loadData();
                                    sendOffLineMsg();
                                }
                            });

                        }
                    });
                } catch (Exception e) {
                    Log.d("msg", "发送异常");
                    e.printStackTrace();
                    dismissProgress();
                }
            }

            ;
        }.start();
    }

    // 发送图片音频信息
    public void savefile(final MessageInfo messageInfo) {
        new Thread() {
            public void run() {
                try {
                    File file = new File(messageInfo.fileRemotePath);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("messageType", messageInfo.messageType);
                    jsonObject.put("toUser", messageInfo.toUser);
                    jsonObject.put("fromUser", messageInfo.fromUser);
                    jsonObject.put("appId", ImConst.appId);
                    jsonObject.put("fileName", file.getName());
                    jsonObject.put( "extend","D");
                    if(messageInfo.messageType.equals("img")){
                        file = new Compressor(TalkMsgActivity.this).compressToFile(file);
                    }
                    jsonObject.put("fileData", FileDataUtil.getBytesFromFile(file));
                    if (messageInfo.messageType.equals("audio")) {
                        jsonObject.put("duration", "20");
                        jsonObject.put("size", "2048");
                    }
                    ImConst.mSocket.emit("chat", jsonObject, new Ack() {
                        @Override
                        public void call(Object... args) {
                            messageInfo.delete();
                            MessageInfo mi = new Gson().fromJson(args[0].toString(), MessageInfo.class);
                            mi.setSended(1);
                            mi.save();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if("0".equals(admInfo.chatStatus)){
                                        updatechatstatus("1");
                                    }
                                    loadData();
                                    sendOffLineMsg();
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    Log.d("msg", "发送异常");
                    e.printStackTrace();
                    dismissProgress();
                }
            }

            ;

        }.start();
    }

    private String createVoiceName() {
        String fileName = Const.loginInfo.userCode + "_"
                + DateUtil.getNowDateTime("yyyyMMddHHmmss");
        return fileName;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // vp.mediaPlayer.stop();
            for (String key : ta.mapPlay.keySet()) {
                ta.mapPlay.get(key).release();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    // 别忘了将广播取消掉哦~
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(IoService!=null){
            Log.d("msg", "结束聊天服务");
            stopService(IoService);
        }
        unregisterReceiver(reciver);
    }

    public void updatechatstatus(final String chatStatus) {
        showProgress();
        new Thread() {
            String msg = "网络错误，请稍后再试";
            BaseBean baseBean;
            @Override
            public void run() {
                super.run();
                try {
                    baseBean = AdmManager.updatechatstatus(Const.DeviceId, Const.loginInfo.userCode, admInfo.admId,chatStatus);
                } catch (Exception e) {
                    e.printStackTrace();
                    msg = e.getMessage();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        if (baseBean!=null&&baseBean.getResultCode().equals("0")) {
                            if("1".equals(chatStatus)){//结束服务，并
                                msg = "服务已确认";
                                admInfo.chatStatus=chatStatus;
                                intiChat();
                            }else{
                                msg = "服务已结束";
                                admInfo.chatStatus=chatStatus;
                                sendFinishText();
                                showToast(msg);
                            }
                        } else {
                            if(baseBean!=null){
                                msg = baseBean.getResultDesc();
                            }
                            showToast(msg);
                        }
                    }
                });
            }
        }.start();
    }
    public void cancleOrder() {
        showProgress();
        new Thread() {
            String msg = "";
            BaseBean baseBean;
            @Override
            public void run() {
                super.run();
                try {
                    baseBean = AdmManager.cancleOrder(Const.DeviceId,admInfo.patientCard, admInfo.patientId,admInfo.registerId);
                } catch (Exception e) {
                    e.printStackTrace();
                    msg = e.getMessage();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        if (baseBean!=null&&baseBean.getResultCode().equals("0")) {
                                msg = "已退号,服务已结束";
                                admInfo.chatStatus="2";
                                sendFinishText();
                                ta.notifyDataSetChanged();
                                showToast(msg);
                        } else {
                            if(baseBean!=null){
                                msg = baseBean.getResultDesc();
                            }
                            showToast(msg);
                        }
                    }
                });
            }
        }.start();
    }

    public void loadPatinfo() {
        showProgress();
        new Thread() {
            PatientInfo patientInfo;

            public void run() {
                try {
                    patientInfo = BusyManager.loadPatientInfo(Const.loginInfo.userCode, admInfo.admId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        if (patientInfo != null) {
                            Const.curPat = patientInfo;
                            Const.curSRecorder = null;
                            Const.SRecorderList = null;
                            Intent intent = new Intent(TalkMsgActivity.this, WardRoundActivity.class);
                            intent.putExtra("title", "患者资料");
                            startActivity(intent);
                        } else {
                            showToast("获取患者信息失败");
                        }
                    }
                });
            }

            ;
        }.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("msg", requestCode + "   " + resultCode);
        if (requestCode == 101 && resultCode == 100) {
            DoctorTemplate patTemplate = new DoctorTemplate(admInfo.admId, admInfo.doctorName, admInfo.doctorTitle, admInfo.departmentName);
            sendTemplateData(new Gson().toJson(patTemplate), 2);
        }
    }
}
