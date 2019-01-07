package com.imedical.mobiledoctor.entity;


import com.imedical.mobiledoctor.util.Desc;

public class RisReport {
    @Desc(label = "报告主标头/医院名称", type = "")
    public String reportMainTitle;
    @Desc(label = "报告二级标题/检查报告", type = "")
    public String reportSubTitle;
    @Desc(label = "患者姓名", type = "")
    public String patName;
    @Desc(label = "性别", type = "")
    public String sex;
    @Desc(label = "年龄", type = "")
    public String age;
    @Desc(label = "登记号", type = "")
    public String regNo;
    @Desc(label = "就诊科室", type = "")
    public String admDept;
    @Desc(label = "病区", type = "")
    public String wardDesc;
    @Desc(label = "床位代码", type = "")
    public String bedCode;
    @Desc(label = "检查Id", type = "")
    public String studyId;
    @Desc(label = "诊断", type = "")
    public String diagnosis;
    @Desc(label = "检查科室", type = "")
    public String examDept;
    @Desc(label = "医嘱代码", type = "")
    public String itemCode;
    @Desc(label = "检查医嘱", type = "")
    public String itemDesc;
    @Desc(label = "检查描述/诊断印象", type = "")
    public String examDescEx;
    @Desc(label = "检查结果/诊断意见", type = "")
    public String resultDescEx;
    @Desc(label = "检查备注", type = "")
    public String memoEx;
    @Desc(label = "所见描述", type = "")
    public String seeDescEx;
    @Desc(label = "发布时间", type = "")
    public String reportDate;
    @Desc(label = "发布医生", type = "")
    public String reportDoc;
    @Desc(label = "审核时间", type = "")
    public String verifyDate;
    @Desc(label = "审核医生", type = "")
    public String verifyDoc;
    @Desc(label = "报告URL", type = "")
    public String reportURL;
    @Desc(label = "图像URL", type = "")
    public String imageURL;

}
