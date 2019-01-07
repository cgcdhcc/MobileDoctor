package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * <Instruction>
 * <instructId>医嘱用法Id</instructId>
 * <instructDesc>医嘱用法描述</instructDesc>
 * </Instruction>
 *
 * @author sszvip
 * <p>
 * 此类为接口入参数，不要随意增删属性
 */
public class Instruction implements Serializable {
    /**
     *
     */
    public String instructId;
    public String instructDesc;

    @Override
    public String toString() {
        return instructDesc;
    }

}
