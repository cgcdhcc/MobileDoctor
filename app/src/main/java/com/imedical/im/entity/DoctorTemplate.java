package com.imedical.im.entity;

public class DoctorTemplate {
    public String admId;//>就诊Id</admId>
    public String doctorName;//>医生姓名 </doctorName>
    public String doctorTitle;//>医生级别</doctorTitle>
    public String departmentName;//>科室名称</departmentName>
    public DoctorTemplate(String admId, String doctorName, String doctorTitle, String departmentName){
        this.admId=admId;
        this.doctorName=doctorName;
        this.doctorTitle=doctorTitle;
        this.departmentName=departmentName;
    }
}
