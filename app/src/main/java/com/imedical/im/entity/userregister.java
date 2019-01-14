package com.imedical.im.entity;


import com.imedical.im.ImConst;
import com.imedical.mobiledoctor.Const;

/**
 * Created by dashan on 2018/7/24.
 */

public class userregister {
    public String appId= ImConst.appId;//":"f942c872-5f21-11e8-bb09-1ad4d8c039da",
    public String username;//":"dongzt",
    public String password;//": "dongzt",
    public String source=ImConst.source;//": "wx_client",
    public String nickname;//": "龙腾四海",
    public String profile;//": ""
    public userregister(String username, String password, String nickname){
            this.username=username;
            this.password=password;
            this.nickname=nickname;
    }

}
