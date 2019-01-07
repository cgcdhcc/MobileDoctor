package com.imedical.mobiledoctor.entity;

/**
 * @author ssz
 * <ConsultInfo>
 * <conId>会诊申请Id</conId>
 * <admId>患者就诊Id</admId>
 *
 * <patBed>患者床号</patBed>
 * <patName>患者姓名</patName>
 * <type>会诊类型</type>
 * <inOut>院内院外</inOut>
 *
 * <mainDiag>主要诊断</mainDiag>
 * <intent>会诊目的</intent>
 * <appDoc>申请医生</appDocName>
 * <appDateTime>申请时间</appDateTime>
 *
 * <conAttitude>会诊意见</conAttitude>
 * <appArcim>临床申请使用<appArcim>
 * <docAttitude>医生意见，同意/不同意</docAttitude>
 * </ConsultInfo>
 */
public class ConsultInfo {
    public String conId;
    public String admId;

    public String appArcim;
    public String appDateTime;
    public String appDoc;
    public String conAttitude;
    public String docAttitude;

    public String inOut;
    public String intent;
    public String mainDiag;
    public String patBed;
    public String patName;

    public String type;

}

