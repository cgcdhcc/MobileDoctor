package com.imedical.im.entity;


import com.imedical.im.ImConst;
import com.imedical.mobiledoctor.Const;

/**
 * Created by dashan on 2018/7/24.
 */

public class userlogin {
    public String appId= ImConst.appId;//":"f942c872-5f21-11e8-bb09-1ad4d8c039da",
    public String username;//":"dongzt",
    public String password;//": "dongzt",
    public String source=Const.source;//": "wx_client",""
    public userlogin(String username, String password){
        this.username=username;
        this.password=password;
    }
}
