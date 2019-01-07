package com.imedical.mobiledoctor.entity.dateorder;

import java.io.Serializable;

/**
 * <RegisterInfo></RegisterInfo>?<RegisterInfo></RegisterInfo>
 * <RegisterInfo>
 * <patName>患者姓名</patName>
 * <patSex>性别</patSex>
 * <patAge>年龄</patAge>
 * <RegisterId>登记Id、病人ID</RegisterId>
 * <departmentName>科室名称</departmentName>
 * <OrderContent>预约方式</OrderContent>
 * <OrderCode>预约单号(预约记录rowid)</OrderCode>
 * <allowedCancel>CAN/CANNOT</allowedCancel>
 */
public class OrderRegDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    public OrderRegDetail() {
    }

    public String regDate;
    public String patName;
    public String patSex;
    public String patAge;
    public String RegisterId;
    public String departmentName;
    public String OrderContent;
    public String OrderCode;
    public String allowedCancel;
    public String conLoad;
    public String patTel;
}
