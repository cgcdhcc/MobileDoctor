package com.imedical.mobiledoctor.entity.dateorder;

import java.io.Serializable;

/**
 * <RegisterInfo>
 * <patName>患者姓名</patName>
 * <patSex>性别</patSex>
 * <patAge>年龄</patAge>
 * <RegisterId>病人ID</RegisterId>
 * <defaultNum>违约次数</defaultNum>
 * </RegisterInfo>
 */
public class OrderSureData implements Serializable {
    private static final long serialVersionUID = 1L;

    public OrderSureData() {
    }

    public String patName;
    public String patSex;
    public String patAge;
    public String RegisterId;
    public String defaultNum;
}
