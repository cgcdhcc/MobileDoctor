package com.imedical.mobiledoctor.entity;

import com.imedical.mobiledoctor.util.Desc;

public class RisReportList {
    @Desc(label = "检查登记Id", type = "")
    public String risRegId;
    @Desc(label = "检查报告Id", type = "")
    public String studyId;
    @Desc(label = "医嘱Id", type = "")
    public String ordItemId;
    @Desc(label = "医嘱名称", type = "")
    public String ordItemDesc;
    @Desc(label = "医嘱状态", type = "")
    public String ordStatus;
    @Desc(label = "医嘱日期", type = "")
    public String ordDate;
    @Desc(label = "医嘱时间", type = "")
    public String ordTime;
    @Desc(label = "报告状态", type = "")
    public String reportStatus;
    @Desc(label = "报告发布医生", type = "")
    public String reportUser;
    @Desc(label = "报告发布日期", type = "")
    public String reportDate;
    @Desc(label = "报告发布时间", type = "")
    public String reportTime;
}
