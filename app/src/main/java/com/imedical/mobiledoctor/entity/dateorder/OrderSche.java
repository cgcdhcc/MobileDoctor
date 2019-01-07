package com.imedical.mobiledoctor.entity.dateorder;

import java.io.Serializable;

/**
 * @author LED
 *
 * <RegisterInfo>
 * <departmentCode>科室ID</departmentCode>
 * <departmentName>科室描述1</departmentName>
 * <timeRange>时间段</timeRange>
 * </RegisterInfo>
 */
public class OrderSche implements Serializable {
    private static final long serialVersionUID = 1L;

    public OrderSche() {
    }

    public String departmentCode;
    public String departmentName;
    public String timeRange;
}
