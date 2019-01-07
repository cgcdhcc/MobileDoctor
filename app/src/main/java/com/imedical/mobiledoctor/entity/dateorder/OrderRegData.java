package com.imedical.mobiledoctor.entity.dateorder;

import java.io.Serializable;

/**
 * <departmentName>科室名称</departmentName>
 * <departmentCode>科室代码</departmentCode>
 * <typeDesc>职称</typeDesc>
 * <markRowId>号别代码</markRowId>
 * <markDesc>号别</markDesc>
 * <timeRange>时间段</timeRange>
 * <regNum>挂号数</regNum>
 * <orderNum>预约数</orderNum>
 * <plusNum>加号数</plusNum>
 * <ScheduleItemCode>排班资源ID</ScheduleItemCode>
 */
public class OrderRegData implements Serializable {
    private static final long serialVersionUID = 1L;

    public OrderRegData() {
    }

    public String departmentName;
    public String departmentCode;
    public String markRowId;
    public String typeDesc;
    public String markDesc;
    public String timeRange;
    public String regNum;
    public String orderNum;
    public String plusNum;
    public String ScheduleItemCode;
}
