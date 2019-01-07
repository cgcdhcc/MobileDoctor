package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * <Priority>
 * <oecprId>医嘱优先级Id</oecprId>
 * <oecprDesc>医嘱优先级描述</oecprDesc>
 * </Priority>
 *
 * @author sszvip
 * <p>
 * 此类为接口入参数，不要随意增删属性
 */
public class Priority implements Serializable {
    /**
     *
     */
    public String oecprId;
    public String oecprDesc;
}
