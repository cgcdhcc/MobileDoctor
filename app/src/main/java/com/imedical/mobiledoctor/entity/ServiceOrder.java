package com.imedical.mobiledoctor.entity;



import com.imedical.mobiledoctor.util.Desc;

import java.io.Serializable;


public class ServiceOrder implements Serializable {
    @Desc(label = "医嘱项目ID", type = "") public String orderRowId;
    @Desc(label = "医嘱名称:显示", type = "") public String orderName;
    @Desc(label = "检查申请状态:显示", type = "") public String appStatus;
    @Desc(label = "预约方式", type = "") public String appMethod;
    @Desc(label = "预约日期:显示", type = "") public String bookedDate;
    @Desc(label = "预约时间:显示", type = "") public String bookedTime;
    @Desc(label = "预约类型", type = "") public String appType;
    @Desc(label = "服务资源组Id", type = "") public String serviceId;
    @Desc(label = "接收科室:显示", type = "") public String recLoc;
    @Desc(label = "接收科室Id", type = "") public String recLocId;

    @Desc(label = "是否关联，与XML数据无关", type = "") public int IsGroup;
    @Desc(label = "是否点击，与XML数据无关", type = "") public boolean IsChecked;

    public ServiceOrder() {

    }

}
