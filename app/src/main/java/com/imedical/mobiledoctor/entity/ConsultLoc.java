package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * <ConsultLoc>
 * <consLocId>会诊科室Id</consLocId>
 * <consLocCode>会诊科室代码</consLocCode>
 * <consLocDesc>会诊科室描述</consLocDesc>
 * </ConsultLoc>
 *
 * @author sszvip
 */
public class ConsultLoc implements Serializable {
    private static final long serialVersionUID = 1L;

    public ConsultLoc() {
    }

    public String consLocId;
    public String consLocCode;
    public String consLocDesc;

    @Override
    public String toString() {
        return consLocDesc;
    }
}
