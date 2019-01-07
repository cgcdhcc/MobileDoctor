package com.imedical.mobiledoctor.entity;

import com.imedical.mobiledoctor.util.Desc;

import java.util.List;

public class LisReportItem {
    @Desc(label = "检验结果项目序号", type = "")
    public String serialNo;
    @Desc(label = "检验项目代码", type = "")
    public String itemCode;
    @Desc(label = "检验结果项目描述", type = "")
    public String itemDesc;
    @Desc(label = "缩写", type = "")
    public String abbreviation;
    @Desc(label = "结果", type = "")
    public String result;
    @Desc(label = "结果值", type = "")
    public String resultValue;
    @Desc(label = "单位", type = "")
    public String unit;
    @Desc(label = "高低标识", type = "")
    public String flagUpDown;
    @Desc(label = "正常范围", type = "")
    public String naturalRange;

    @Desc(label = "报告日期", type = "")
    public String reportDate;

    @Desc(label = "resultList", type = Desc.TYPE_LIST)
    public List<Result> resultList;

}
