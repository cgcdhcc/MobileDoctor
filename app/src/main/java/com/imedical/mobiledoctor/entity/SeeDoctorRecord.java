package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * <SeeDoctorRecord>
 * <admId>患者就诊Id</admId>
 * <admDept>就诊科室</admDept>
 * <admDate>就诊日期</admDate>
 * <admType>类型（I：住院； O：门诊； E：急诊）</admType>
 * <dischgDate>出院日期</dischgDate>
 * </SeeDoctorRecord>
 *
 * @author sszvip
 * 获取dicom图像 请求参数
 */
public class SeeDoctorRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    public SeeDoctorRecord() {
    }

    public String admId;
    public String admDept;
    public String admDate;
    public String admType;
}
