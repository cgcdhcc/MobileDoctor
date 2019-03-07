package com.imedical.trtcsdk.bean;

public class userSigResponse {
    public int code;
    public String message;
    public userSigBean data;

    public userSigResponse() {
        this.code=-1;
        message="数据错误";
    }
}
