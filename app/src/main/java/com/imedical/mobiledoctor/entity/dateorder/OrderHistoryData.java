package com.imedical.mobiledoctor.entity.dateorder;

import java.io.Serializable;

/**
 * <RegisterInfo>
 * <RegisterId>病人ID或者卡号</RegisterId>
 * </RegisterInfo>
 */
public class OrderHistoryData implements Serializable {
    private static final long serialVersionUID = 1L;

    public OrderHistoryData() {
    }

    public String RegisterId;
}
