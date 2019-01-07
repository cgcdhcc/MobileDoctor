package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * <DiagType>
 * <diagTypeId>诊断类型Id</diagTypeId>
 * <diagTypeCode>诊断类型代码</diagTypeCode>
 * <diagTypeDesc>诊断类型描述</diagTypeDesc>
 * </DiagTypes>
 *
 * @author sszvip
 * <p>
 * 此类为接口入参数，不要随意增删属性
 */
public class DiagType implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    public String diagTypeId;
    public String diagTypeCode;
    public String diagTypeDesc;
}
