package com.imedical.im.entity;

import java.util.List;

/**
 * Created by dashan on 2018/7/25.
 */

public class chatmessageresponse {
    public boolean success;
    public String msg;
    public chatdata data;
    public class chatdata{
        public int total;//": 2,
        public List<MessageInfo> items;

    }
}
