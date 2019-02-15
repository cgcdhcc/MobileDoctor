package com.imedical.jpush.bean;

public class DeleteAllMsgRequest {
    public String userid;
    public String appkey;
    public String total;

    public DeleteAllMsgRequest(String userid, String appkey,String total){
       this.userid=userid;
       this.appkey=appkey;
       this.total=total;
    }
}
