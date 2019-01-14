package com.imedical.im.entity;


import com.imedical.im.ImConst;
import com.imedical.mobiledoctor.Const;

/**
 * Created by dashan on 2018/7/24.
 */

public class newcontact {
    public String appId = ImConst.appId;//":"f942c872-5f21-11e8-bb09-1ad4d8c039da",
    public String ownerUsername;//”:
    public String friendUsername;//”:

    public newcontact(String ownerUsername, String friendUsername) {
        this.ownerUsername = ownerUsername;
        this.friendUsername = friendUsername;
    }
}
