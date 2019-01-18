package com.imedical.jpush.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by dashan on 2017/7/6.
 */

public class extras implements Serializable {
    public String newsClassification;//": "prompt",
    public String actionCode;//": "Regist",
    public String actionName;//": "挂号成功",
    public String msgGroupCode;//": "appRemaind",
    public String msgGroupName;//": "预约提醒",
    public Map<String,String> jumpData;//": []
    public String actionUrl;//H5跳转
}
