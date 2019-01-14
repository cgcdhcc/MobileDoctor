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
import com.imedical.im.entity.MessageInfo;
import com.imedical.im.entity.UserFriend;
import com.imedical.im.media.RecordButton;
import com.imedical.mobiledoctor.AppConfig;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.util.DateUtil;
import com.imedical.mobiledoctor.util.FileDataUtil;
import com.imedical.mobiledoctor.util.Validator;

import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    private Button btn_send, btn_img_send, voice_button, keybord_button;
    public RecordButton mRecordButton;
    public Intent IoService;
    public TextView chat_status;
    public boolean isonline = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.im_activity_talklist);
        //userfriend = (UserFriend)this.getIntent().getSerializableExtra("userfriend");
        userfriend = new UserFriend();
        userfriend.friendUsername = "zhangwei";
        userfriend.friendNickName = "张伟";
        userfriend.ownerUsername = Const.loginInfo.userCode;
        action = userfriend.friendUsername + "_chat";
        reciver = new MyBroadcastReceiver();
        IntentFilter intentf = new IntentFilter();
        intentf.addAction(action);
        intentf.addAction(connect_action);
        intentf.addAction(login_action);
        registerReceiver(reciver, intentf);
        list_data = (ListView) findViewById(R.id.list_data);
        setTitle(userfriend.friendNickName);
        btn_img_send = (Button) findViewById(R.id.btn_img_send);
        et_msg = (EditText) findViewById(R.id.et_msg);
        ta = new TalkMsgAdapter(this, data_list, userfriend);
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
                    voice_button.setVisibility(View.VISIBLE);
                    btn_send.setVisibility(View.GONE);
                } else {
                    voice_button.setVisibility(View.GONE);
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
        keybord_button = (Button) findViewById(R.id.keybord_button);
        voice_button = (Button) findViewById(R.id.voice_button);
        voice_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                voice_button.setVisibility(View.GONE);
                keybord_button.setVisibility(View.VISIBLE);
                et_msg.setVisibility(View.GONE);
                mRecordButton.setVisibility(View.VISIBLE);
            }
        });

        keybord_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                voice_button.setVisibility(View.VISIBLE);
                keybord_button.setVisibility(View.GONE);
                et_msg.setVisibility(View.VISIBLE);
                mRecordButton.setVisibility(View.GONE);
            }
        });
        btn_img_send.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                popup(TalkMsgActivity.this);
            }
        });
        ll_talk = findViewById(R.id.ll_talk);
        chat_status = (TextView) findViewById(R.id.chat_status);
        IoService = new Intent(this, SocketService.class);
        startService(IoService);
        loadData();
    }

    public synchronized void loadData() {
        data_list.clear();
        data_list.addAll(LitePal.limit(50).where("(fromUser=? and toUser=?) or (fromUser=? and toUser=?)", userfriend.ownerUsername, userfriend.friendUsername, userfriend.friendUsername, userfriend.ownerUsername).order("timeStamp asc").find(MessageInfo.class));
        Log.d("msg", "获取消息记录：" + data_list.size());
        ta.notifyDataSetChanged();
        list_data.setSelection(data_list.size() - 1);
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
                    jsonObject.put("fromUser", Const.loginInfo.userCode);
                    jsonObject.put("appId", ImConst.appId);
                    jsonObject.put("content", content);
                    //jsonObject.put( "contactId", userfriend.id);
                    final MessageInfo messageInfo = new MessageInfo("txt", content, Const.loginInfo.userCode, userfriend.friendUsername, DateUtil.getNowTimeMillis(), "", "");
                    messageInfo.save();
                    data_list.add(messageInfo);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ta.notifyDataSetChanged();
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
        final File file = new File(path);
        new Thread() {
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("messageType", messageType);
                    jsonObject.put("toUser", userfriend.friendUsername);
                    jsonObject.put("fromUser", Const.loginInfo.userCode);
                    jsonObject.put("appId", ImConst.appId);
                    jsonObject.put("fileName", file.getName());
                    //jsonObject.put( "contactId", userfriend.id);
                    jsonObject.put("fileData", FileDataUtil.getBytesFromFile(file));
                    if (messageType.equals("audio")) {
                        jsonObject.put("duration", "20");
                        jsonObject.put("size", "2048");
                    }
                    final MessageInfo messageInfo = new MessageInfo(messageType, "", Const.loginInfo.userCode, userfriend.friendUsername, DateUtil.getNowTimeMillis(), path, path);
                    messageInfo.save();
                    data_list.add(messageInfo);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ta.notifyDataSetChanged();
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
                            chat_status.setText("连接失败，请检查网络");
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
            } else {
                savefile(offlist.get(i));
            }
        }
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
                    //jsonObject.put( "contactId", userfriend.id);
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
        final File file = new File(messageInfo.fileRemotePath);
        new Thread() {
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("messageType", messageInfo.messageType);
                    jsonObject.put("toUser", messageInfo.toUser);
                    jsonObject.put("fromUser", messageInfo.fromUser);
                    jsonObject.put("appId", ImConst.appId);
                    jsonObject.put("fileName", file.getName());
                    //jsonObject.put( "contactId", userfriend.id);
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
        stopService(IoService);
        unregisterReceiver(reciver);
    }
}
