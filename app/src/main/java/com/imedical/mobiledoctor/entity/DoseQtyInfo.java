package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * <DoseQtyInfo>
 * <doseQty>单次剂量</doseQty>
 * <doseUomDesc>单次剂量单位</doseUomDesc>
 * <doseUomId>单次剂量单位Id</doseUomId>
 * <defaultFlag>默认标识Y／N </defaultFlag>
 * </DoseQtyInfo>
 *
 * @author sszvip
 */
public class DoseQtyInfo implements Serializable {
    /**
     *
     */
    public String doseQty;
    public String doseUomDesc;
    public String doseUomId;
    public String defaultFlag;


    public DoseQtyInfo() {
    }

    public DoseQtyInfo(String doseQty, String doseUomDesc, String doseUomId,
                       String defaultFlag) {
        super();
        this.doseQty = doseQty;
        this.doseUomDesc = doseUomDesc;
        this.doseUomId = doseUomId;
        this.defaultFlag = defaultFlag;
    }


}
