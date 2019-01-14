package com.imedical.im.entity;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dashan on 2018/7/25.
 */

public class UserFriend extends LitePalSupport implements Serializable {

    public String ownerUsername;//":"dongzt",
    public String friendUsername;//": "龙腾四海",
    public int id;//": 21,
    public String friendNickName;//": "董浩",
    public boolean online;//": false
    public int relateContact;//

    public List<MessageInfo> queryMessage(){
        return LitePal.limit(1).where("(fromUser=? and toUser=?) or (fromUser=? and toUser=?)",ownerUsername,friendUsername,friendUsername,ownerUsername).order("timeStamp desc").find(MessageInfo.class);
    }

}
