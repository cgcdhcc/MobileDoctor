package com.imedical.mobiledoctor.entity;

import com.imedical.mobiledoctor.util.Desc;

public class LisReportList {
    @Desc(label = "显示序号", type = "")
    public String ordIndex;
    @Desc(label = "检验标本号", type = "")
    public String ordLabNo;
    @Desc(label = "医嘱项Id", type = "")
    public String arcItemId;
    @Desc(label = "医嘱名称", type = "")
    public String ordItemDesc;
    @Desc(label = "医嘱状态", type = "")
    public String ordStatus;
    @Desc(label = "医嘱日期", type = "")
    public String ordDate;
    @Desc(label = "医嘱时间", type = "")
    public String ordTime;
    @Desc(label = "检验标本", type = "")
    public String ordLabSpec;
    @Desc(label = "检验科室", type = "")
    public String recDept;
    @Desc(label = "报告状态", type = "")
    public String reportStatus;
    @Desc(label = "报告异常标识", type = "")
    public String reportException;
    @Desc(label = "报告发布医生", type = "")
    public String reportUser;
    @Desc(label = "报告发布日期", type = "")
    public String reportDate;
    @Desc(label = "报告发布时间", type = "")
    public String reportTime;
    @Desc(label = "conLoad", type = "")
    public String conLoad;

}
