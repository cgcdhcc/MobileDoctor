package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * <SkinAction>
 * <actionId>皮试备注Id</actionId>
 * <actionDesc>皮试备注描述</actionDesc>
 * </SkinAction>
 *
 * @author sszvip
 * <p>
 * 此类为接口入参数，不要随意增删属性
 */
public class SkinAction implements Serializable {
    /**
     *
     */
    public String actionId;
    public String actionDesc;
}
