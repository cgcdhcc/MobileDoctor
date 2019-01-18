package com.imedical.im.entity;

public class PatTemplate {
    public String admId;//>就诊Id</admId>
    public String patientAge;//>患者年龄</patientAge>
    public String patientSex;//>患者性别</patientSex>
    public String patientName;//>患者姓名</patientName>
    public PatTemplate(String admId,String patientAge,String patientSex,String patientName){
        this.admId=admId;
        this.patientAge=patientAge;
        this.patientSex=patientSex;
        this.patientName=patientName;
    }
}
