package com.imedical.mobiledoctor.entity.homegrid;

import java.io.Serializable;

/**
 * Created by liuende on 16/10/14.
 */
public class funcCode implements Serializable {
    private static final long serialVersionUID = 1L;

    public funcCode() {
    }

    public funcCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public String functionCode;
}
