package com.imedical.jpush.service;

import com.google.gson.Gson;
import com.imedical.jpush.bean.DeleteAllMsgRequest;
import com.imedical.jpush.bean.DeleteMsgRequest;
import com.imedical.jpush.bean.GetUnReadCountRequest;
import com.imedical.jpush.bean.MessageNoRead;
import com.imedical.jpush.bean.MsgResponse;
import com.imedical.jpush.bean.QueryMsgRequest;
import com.imedical.jpush.bean.UpdateMsgStatusRequest;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.XMLservice.SettingManager;
import com.imedical.mobiledoctor.api.RequestUtil;
import com.imedical.mobiledoctor.api.WsApiUtil;
import com.imedical.mobiledoctor.util.LogMe;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dashan on 2017/7/10.
 */

public class MessageService {
    public static Gson gson=new Gson();
    public static MsgResponse queryMsg(final String canload){
        QueryMsgRequest queryMsgRequest=new QueryMsgRequest("reg_"+ Const.loginInfo.userCode,Const.jpushkey, "10",canload);
        String serviceUrl = SettingManager.getServerUrl();
        String resultXml  = WsApiUtil.loadSoapObjectJson(serviceUrl,Const.BIZ_CODE_getappmsg,null, gson.toJson(queryMsgRequest));
        MsgResponse response=gson.fromJson(resultXml,MsgResponse.class);
        return response;

    }
    //获取未读消息个数
    public static MessageNoRead getunreadcount(){
        GetUnReadCountRequest getUnReadCountRequest=new GetUnReadCountRequest("reg_"+ Const.loginInfo.userCode,Const.jpushkey);
        String serviceUrl = SettingManager.getServerUrl();
        String resultXml  = WsApiUtil.loadSoapObjectJson(serviceUrl,Const.BIZ_CODE_getunreadcount,null, gson.toJson(getUnReadCountRequest));
        MessageNoRead response=gson.fromJson(resultXml,MessageNoRead.class);
        return response;

    }

    //全部置为已读

    public static MessageNoRead updateallmsgstatus(){
        GetUnReadCountRequest getUnReadCountRequest=new GetUnReadCountRequest("reg_"+ Const.loginInfo.userCode,Const.jpushkey);
        String serviceUrl = SettingManager.getServerUrl();
        String resultXml  = WsApiUtil.loadSoapObjectJson(serviceUrl,Const.BIZ_CODE_updateallmsgstatus,null, gson.toJson(getUnReadCountRequest));
        MessageNoRead response=gson.fromJson(resultXml,MessageNoRead.class);
        return response;

    }

    //一条信息置为已读
    public static MessageNoRead updatemsgstatus(final String msgid, final String markread){
        UpdateMsgStatusRequest updateMsgStatusRequest=new UpdateMsgStatusRequest("reg_"+ Const.loginInfo.userCode,Const.jpushkey,msgid,markread);
        String serviceUrl = SettingManager.getServerUrl();
        String resultXml  = WsApiUtil.loadSoapObjectJson(serviceUrl,Const.BIZ_CODE_updatemsgstatus,null, gson.toJson(updateMsgStatusRequest));
        MessageNoRead response=gson.fromJson(resultXml,MessageNoRead.class);
        return response;

    }
    //删除全部消息

    public static MessageNoRead deleteallmsg(final String total){//是否删除全部(0=>已读,1=>全部),默认不传0,删除已读消息
        DeleteAllMsgRequest deleteAllMsgRequest=new DeleteAllMsgRequest("reg_"+ Const.loginInfo.userCode,Const.jpushkey,total);
        String serviceUrl = SettingManager.getServerUrl();
        String resultXml  = WsApiUtil.loadSoapObjectJson(serviceUrl,Const.BIZ_CODE_deleteallmsg,null, gson.toJson(deleteAllMsgRequest));
        MessageNoRead response=gson.fromJson(resultXml,MessageNoRead.class);
        return response;

    }

    //删除一条消息

    public static MessageNoRead deletemsg(final String msgid){
        DeleteMsgRequest deleteMsgRequest=new DeleteMsgRequest("reg_"+ Const.loginInfo.userCode,Const.jpushkey,msgid);
        String serviceUrl = SettingManager.getServerUrl();
        String resultXml  = WsApiUtil.loadSoapObjectJson(serviceUrl,Const.BIZ_CODE_deletemsg,null, gson.toJson(deleteMsgRequest));
        MessageNoRead response=gson.fromJson(resultXml,MessageNoRead.class);
        return response;

    }

}


