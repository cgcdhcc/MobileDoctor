package com.imedical.mobiledoctor.entity.dateorder;

import java.io.Serializable;

public class OrderDept implements Serializable {
    private static final long serialVersionUID = 1L;

    public OrderDept() {
    }

    public String departmentId;
    public String departmentDesc;
    public String markDesc;
    public String regFlag;
}
