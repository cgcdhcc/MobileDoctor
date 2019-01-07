package com.imedical.mobiledoctor.entity;



import com.imedical.mobiledoctor.util.Desc;

import java.io.Serializable;


/**
 * @author sszvip
 * @Desc 注解内容可用于自动生成界面的label信息，非必要内容，开发完成后可删除
 */
public class PatientInfo implements Serializable {
    public String admType;//就诊类型
    //要在界面上展现的内容
    @Desc(label = "就诊Id", type = "")
    public String admId;
    @Desc(label = "住院号", type = "")
    public String patMedNo;
    @Desc(label = "所在病区", type = "")
    public String wardDesc;
    @Desc(label = "床位代码", type = "")
    public String bedCode;
    @Desc(label = "是否主管医生", type = "")
    public String isMainDoc;//是否主管医生,0/1,1 用特殊颜色显示,不能是红色 ????病人信息里怎么会有这个属性

    @Desc(label = "备注信息", type = "")
    public String patNote;
    //
    @Desc(label = "姓名", type = "")
    public String patName;
    @Desc(label = "性别", type = "")
    public String patSex;
    @Desc(label = "年龄", type = "")
    public String patAge;
    @Desc(label = "血型", type = "")
    public String bloodType;
    @Desc(label = "职业", type = "")
    public String occupation;
    @Desc(label = "婚姻状况", type = "")
    public String marital;
    @Desc(label = "过敏史", type = "")
    public String allergies;
    @Desc(label = "住院科室", type = "")
    public String departmentName;
    @Desc(label = "主管医生", type = "")
    public String mainDoctor;
    @Desc(label = "入院日期", type = "")
    public String inDate;
    @Desc(label = "入院天数", type = "")
    public String inDays;
    @Desc(label = "护理级别", type = "")
    public String careLevel;
    @Desc(label = "病情状况", type = "")
    public String condition;
    @Desc(label = "部门ID", type = Desc.TYPE_PARSE_IGNORE) //xml中不包含此项，不需要解析
    public String departmentId;
    ///20113.07.30 新添加 by sszvip@gmail.com
    @Desc(label = "病人类型", type = "")
    public String payType;
    @Desc(label = "预交金", type = "")
    public String deposit;
    @Desc(label = "费用合计", type = "")
    public String total;
    @Desc(label = "患者自负", type = "")
    public String patShare;
    @Desc(label = "分页标识", type = "")
    public String conLoad;
    public String patRegNo;

    public PatientInfo() {
    }

}
