package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * <OperaTime>
 * <timeId>时间Id</timeId>
 * <timeCode>时间代码</timeCode>
 * <timeDesc>时间描述</timeDesc>
 * </OperaTime>
 *
 * @author sszvip
 */
public class OperaTime implements Serializable {
    private static final long serialVersionUID = 1L;

    public OperaTime() {
    }

    public String timeId;
    public String timeCode;
    public String timeDesc;

    @Override
    public String toString() {
        return timeDesc;
    }

}
