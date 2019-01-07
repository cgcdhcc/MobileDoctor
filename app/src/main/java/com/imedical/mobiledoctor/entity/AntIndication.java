package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * <AntIndication>
 * <indicateId>指标Id</indicateId>
 * <indicateCode>指标代码</indicateCode>
 * <indicateDesc>指标描述</indicateDesc>
 * </AntIndication>
 *
 * @author sszvip
 */
public class AntIndication implements Serializable {
    private static final long serialVersionUID = 1L;

    public AntIndication() {
    }

    public String indicateId;
    public String indicateCode;
    public String indicateDesc;

    @Override
    public String toString() {
        return indicateDesc;
    }
}
