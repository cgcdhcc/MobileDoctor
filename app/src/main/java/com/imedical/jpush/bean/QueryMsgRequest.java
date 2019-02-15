package com.imedical.jpush.bean;

public class QueryMsgRequest {
    public String userid;
    public String appkey;
    public String pagesize;
    public String canload;

    public QueryMsgRequest(String userid,String appkey,String pagesize,String canload){
       this.userid=userid;
       this.appkey=appkey;
       this.pagesize=pagesize;
       this.canload=canload;
    }
}
