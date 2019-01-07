package com.imedical.mobiledoctor.entity;

import com.imedical.mobiledoctor.util.Desc;

public class Result {
    @Desc(label = "报告日期", type = "")
    public String reportDate;
    @Desc(label = "结果值+高低标识", type = "")
    public String resultValue;

}
