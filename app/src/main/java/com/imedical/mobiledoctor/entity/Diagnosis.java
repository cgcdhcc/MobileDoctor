package com.imedical.mobiledoctor.entity;

import com.imedical.mobiledoctor.util.Desc;

import java.io.Serializable;

//////////////////////////////
//<Diagnosis>
//<diaId>诊断Id</diaId>
//<diaDesc>诊断描述</diaDesc>
//<diaNote>诊断备注</diaNote>
//<diaType>诊断类型</diaType>
//<diaDoctor>医生</diaDoctor>
//<diaDate>日期</diaDate>
//<diaTime>时间</diaTime>
//</Diagnosis>
/////////////////////////////

public class Diagnosis implements Serializable {
    @Desc(label = "诊断描述", type = "")
    public String diaDesc;
    @Desc(label = "日期", type = "")
    public String diaDate;

    @Desc(label = "诊断Id", type = "")
    public String diaId;
    @Desc(label = "诊断备注", type = "")
    public String diaNote;
    @Desc(label = "诊断类型", type = "")
    public String diaType;
    @Desc(label = "时间", type = "")
    public String diaTime;
    @Desc(label = "继续加载的标示，第一次加载不传", type = "")//
    public String conLoad;//,0/1,1显示停止按钮
    @Desc(label = "诊断状态", type = "")//
    public String diaStat;//,0/1,1显示停止按钮
    @Desc(label = "诊断级别", type = "")//
    public String diaLve;//,0/1,1显示停止按钮

    public String diaDoctor;

}
