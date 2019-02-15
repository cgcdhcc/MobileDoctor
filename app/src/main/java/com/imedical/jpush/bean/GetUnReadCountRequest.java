package com.imedical.jpush.bean;

public class GetUnReadCountRequest {
    public String userid;
    public String appkey;

    public GetUnReadCountRequest(String userid, String appkey){
       this.userid=userid;
       this.appkey=appkey;
    }
}
