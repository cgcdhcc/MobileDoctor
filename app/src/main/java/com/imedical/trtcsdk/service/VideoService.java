package com.imedical.trtcsdk.service;

import android.util.Log;

import com.google.gson.Gson;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.XMLservice.SettingManager;
import com.imedical.mobiledoctor.api.WsApiUtil;
import com.imedical.trtcsdk.bean.userSigResponse;
import com.imedical.trtcsdk.bean.userSignRequest;

public class VideoService {
    public static Gson gson = new Gson();
    public static userSigResponse GetUserSign( userSignRequest request ) {
        userSigResponse USR;
        try {
            String serviceUrl = SettingManager.getServerUrl();
            String resultXml  = WsApiUtil.loadSoapObjectJson(serviceUrl, Const.BIZ_CODE_VIDEO_USERID,null, gson.toJson(request));
            Log.d("msg",resultXml);
            USR=new Gson().fromJson(resultXml,userSigResponse.class);
        } catch (Exception e) {
            USR=null;
        }
        return USR;
    }

    public static userSigResponse StopVideo( String bizRoomId,String useId) {
        userSigResponse USR;
        userSignRequest request=new userSignRequest();
        request.bizRoomId=bizRoomId;
        request.role="doctor";
        request.userId=useId;
        try {
            String serviceUrl = SettingManager.getServerUrl();
            String resultXml  = WsApiUtil.loadSoapObjectJson(serviceUrl, Const.BIZ_CODE_VIDEO_STOP,null, gson.toJson(request));
            Log.d("msg",resultXml);
            USR=new Gson().fromJson(resultXml,userSigResponse.class);
        } catch (Exception e) {
            USR=null;
        }
        return USR;
    }
}
