package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * <ConsultDoc>
 * <consDocId>会诊医生Id</consDocId>
 * <consDocCode>会诊医生代码</consDocCode>
 * <consDocDesc>会诊医生描述</consDocDesc>
 * </ConsultDoc>
 *
 * @author sszvip
 */
public class ConsultDoc implements Serializable {
    private static final long serialVersionUID = 1L;

    public ConsultDoc() {
    }

    public String consDocId;
    public String consDocCode;
    public String consDocDesc;

    @Override
    public String toString() {
        return consDocDesc;
    }
}
