package com.imedical.mobiledoctor.entity;



import com.imedical.mobiledoctor.util.Desc;

import java.io.Serializable;


public class AdmInfo implements Serializable {
    @Desc(label = "就诊Id", type = "") public String admId;
    @Desc(label = "患者年龄", type = "") public String patientAge;
    @Desc(label = "患者登记号", type = "") public String patientNo;
    @Desc(label = "患者性别", type = "") public String patientSex;
    @Desc(label = "患者姓名", type = "") public String patientName;
    @Desc(label = "医生Id", type = "") public String doctorId;
    @Desc(label = "医生姓名", type = "") public String doctorName;
    @Desc(label = "患者Id", type = "") public String patientId;
    @Desc(label = "挂号号别", type = "") public String doctorAlias;
    @Desc(label = "医生级别", type = "") public String doctorTitle;
    @Desc(label = "科室Id", type = "") public String departmentId;
    @Desc(label = "科室名称", type = "") public String departmentName;
    @Desc(label = "挂号日期", type = "") public String registerDate;
    @Desc(label = "状态A待就诊F已就诊", type = "") public String stateCode;
    @Desc(label = "状态描述", type = "") public String stateDesc;
    @Desc(label = "费用", type = "") public String feeSum;
    @Desc(label = "排队号", type = "") public String seqCode;
    @Desc(label = "医生出诊时段：全天 上午 下午 夜间", type = "") public String sessionName;
    @Desc(label = "医生接诊类型 N 正常 V视频 T图文", type = "") public String visitType;
    public AdmInfo() {
    }

}
