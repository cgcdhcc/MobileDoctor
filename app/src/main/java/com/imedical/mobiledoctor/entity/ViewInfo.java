package com.imedical.mobiledoctor.entity;


import com.imedical.mobiledoctor.util.Desc;

import java.io.Serializable;

/**
 * <ViewInfo>
 * <startDate>入院日期(记录开始日期)</startDate>
 * <endDate>就诊截止日期</endDate>
 * <patientInfo> </patientInfo>
 * <seeDoctorRecord></seeDoctorRecord>…<seeDoctorRecord></seeDoctorRecord>
 * </ViewInfo>
 *
 * @author sszvip
 * 获取dicom图像 请求参数
 */
public class ViewInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    public ViewInfo() {
    }

    public String startDate;
    public String endDate;

    public String seeDoctorRecord;
    public String viewType;

    @Desc(label = "", type = Desc.TYPE_BEAN)
    public PatientInfo PatientInfo;

}
