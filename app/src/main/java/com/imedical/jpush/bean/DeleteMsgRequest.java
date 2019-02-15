package com.imedical.jpush.bean;

public class DeleteMsgRequest {
    public String userid;
    public String appkey;
    public String msglist;

    public DeleteMsgRequest(String userid, String appkey, String msglist) {
        this.userid = userid;
        this.appkey = appkey;
        this.msglist = msglist;
    }
}
