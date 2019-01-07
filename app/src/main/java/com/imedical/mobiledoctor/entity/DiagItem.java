package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * <DiagItem>
 * <diagICDId>诊断Id</tabId>
 * <diagICDDesc>诊断名称</diagICDDesc>
 * </DiagItem>
 *
 * @author sszvip
 * <p>
 * 此类为接口入参数，不要随意增删属性
 */
public class DiagItem implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    public String diagICDId;
    public String diagICDDesc;

    public DiagItem() {
    }

    public DiagItem(String diagICDId, String diagICDDesc) {
        super();
        this.diagICDId = diagICDId;
        this.diagICDDesc = diagICDDesc;
    }


}
