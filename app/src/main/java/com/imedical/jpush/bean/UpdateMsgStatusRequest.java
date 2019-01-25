package com.imedical.jpush.bean;

public class UpdateMsgStatusRequest {
    public String userid;
    public String appkey;
    public String msglist;
    public String markread;

    public UpdateMsgStatusRequest(String userid, String appkey, String msglist, String markread) {
        this.userid = userid;
        this.appkey = appkey;
        this.msglist = msglist;
        this.markread = markread;
    }
}
