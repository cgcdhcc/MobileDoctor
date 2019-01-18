package com.imedical.im;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.imedical.im.entity.MessageInfo;
import com.imedical.im.entity.OnLineMsg;
import com.imedical.im.entity.UserFriend;
import com.imedical.mobiledoctor.Const;

import org.json.JSONObject;
import org.litepal.LitePal;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import okhttp3.OkHttpClient;

/**
 * Created by dashan on 2018/6/26.
 */

public class SocketService extends Service {
    private final String login_action = "com.dashan.Broadcast.login";
    private final String connect_action = "com.dashan.Broadcast.connect";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("msg", "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("msg", "onStartCommand");
        new Thread() {
            @Override
            public void run() {
                super.run();
                Log.d("msg", "run");
                try {
                    Log.d("msg", "初始化Socket");
                    IO.Options opts = new IO.Options();
//                    OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                            .hostnameVerifier(new HostnameVerifier() {
//                                                  @Override
//                                                  public boolean verify(String hostname, SSLSession session) {
//                                                      return true;
//                                                  }
//                                              }
//                            )
//                            .sslSocketFactory(mySSLContext.getSocketFactory(), myX509TrustManager)
//                            .build();
//                    // default settings for all sockets
//                    IO.setDefaultOkHttpWebSocketFactory(okHttpClient);
//                    IO.setDefaultOkHttpCallFactory(okHttpClient);
//                    // set as an option
//                    opts = new IO.Options();
//                    opts.callFactory = okHttpClient;
//                    opts.webSocketFactory = okHttpClient;

                    //opts.forceNew = true;
                    //opts.reconnection=true;
                    opts.transports = new String[]{"websocket"};
                    //失败重连的时间间隔
                    opts.reconnectionDelay = 1000;
                    opts.forceNew=false;
                    opts.reconnection=true;
                    ImConst.mSocket = IO.socket(ImConst.socketIp,opts); // 初始化Socket
                    ImConst.mSocket.connect();
                    ImConst.mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            Log.d("msg", "EVENT_CONNECT-连接成功");
                            new Thread(){
                                @Override
                                public void run() {
                                    super.run();
                                        try{
                                            JSONObject jsonObject = new JSONObject();
                                            jsonObject.put( "username", Const.loginInfo.docMarkId);
                                            jsonObject.put( "password", Const.loginInfo.docMarkId );
                                            jsonObject.put( "authType", ImConst.source );
                                            jsonObject.put( "appId", ImConst.appId );
                                            ImConst.mSocket.emit("authentication",jsonObject);
                                        }catch (Exception e){
                                            Log.d("msg","认证异常");
                                            e.printStackTrace();
                                        }
                                    }
                            }.start();

                        }
                    });
                    ImConst.mSocket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            Log.d("msg", "EVENT_DISCONNECT-断开连接");
                            Intent intent = new Intent(connect_action);
                            intent.putExtra("connect", false);
                            sendBroadcast(intent);
                        }
                    });
                    ImConst.mSocket.on("authenticated", new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            Log.d("msg", "authenticated-认证通过");
                            Intent intent = new Intent(login_action);
                            intent.putExtra("login", true);
                            sendBroadcast(intent);
                        }
                    });
                    ImConst.mSocket.on("chatLogout", new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            Log.d("msg", "收到chatLogout");
                            Intent intent = new Intent(login_action);
                            intent.putExtra("login", false);
                            sendBroadcast(intent);
                        }
                    });
                    ImConst.mSocket.on("unauthorized", new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            Log.d("msg", "认证失败");
                            Intent intent = new Intent(login_action);
                            intent.putExtra("login", false);
                            sendBroadcast(intent);
                        }
                    });
                    ImConst.mSocket.on("chat", new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            Ack ack = (Ack) args[args.length - 1];
                            ack.call(args[0]);
                            Log.d("msg", "chat服务器返回的消息" + args[0]);
                            MessageInfo messageInfo = (new Gson()).fromJson(args[0].toString(), MessageInfo.class);
                            messageInfo.setSended(1);
                            messageInfo.save();
                            Intent mIntent = new Intent();
                            mIntent.putExtra("chatmsg", args[0].toString());
                            mIntent.setAction(messageInfo.fromUser + "_chat");
                            Log.d("msg", messageInfo.fromUser + "_chat");
                            sendBroadcast(mIntent);
//                            mIntent.putExtra("chatmsg", args[0].toString());
//                            mIntent.setAction("chat");
//                            sendBroadcast(mIntent);
                        }

                    });
                    ImConst.mSocket.on("chatOnline", new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            Log.d("msg", "chat服务器返回的消息" + args[0]);
                            OnLineMsg onLineMsg = (new Gson()).fromJson(args[0].toString(), OnLineMsg.class);
                            ContentValues values = new ContentValues();
                            values.put("online", onLineMsg.online);
                            LitePal.updateAll(UserFriend.class, values, "friendUsername = ?", onLineMsg.friendUsername);
                            Intent mIntent = new Intent();
                            mIntent.putExtra("onlinemsg", args[0].toString());
                            mIntent.setAction("onlinemsg");
                            sendBroadcast(mIntent);
                        }

                    });

//                    ImConst.mSocket.on("chatContact", new Emitter.Listener() {
//                        @Override
//                        public void call(Object... args) {//好友添加更新列表
//                            Log.d("msg", "好友申请");
//                            Intent mIntent = new Intent();
//                            mIntent.putExtra("chatContact", args[0].toString());
//                            mIntent.setAction("chatContact");
//                            sendBroadcast(mIntent);
//                        }
//                    });

                    ImConst.mSocket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            Log.d("msg", "EVENT_CONNECT_ERROR-连接错误");
                        }
                    });
                    ImConst.mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            Log.d("msg", "EVENT_CONNECT_ERROR-连接超时");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("msg", "Socket异常");
                }
            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ImConst.mSocket.disconnect();
    }
}
