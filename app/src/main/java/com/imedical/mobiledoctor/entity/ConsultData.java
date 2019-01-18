package com.imedical.mobiledoctor.entity;

import com.imedical.mobiledoctor.util.Desc;

import java.io.Serializable;

/**
 * 临床事件基础数据记录
 * <ConsultData>
 * <conId>会诊申请Id</conId>
 * <admId>患者就诊Id</admId>
 * <patBed>患者床号</patBed>
 * <patName>患者姓名</patName>
 * <mainDiag>主要诊断</mainDiag>
 *
 * <type>会诊类型</type>
 * <appDoc>申请医生</appDocName>
 * <appDateTime>申请时间</appDateTime>
 * <docAttitude>医生意见，同意/不同意</docAttitude>
 *
 * </ConsultData>
 *
 * @author sszvip
 */
public class ConsultData implements Serializable {
    private static final long serialVersionUID = 1L;

    public ConsultData() {
    }

    public String conId;
    public String admId;
    public String patBed;
    public String patName;
    public String mainDiag;
    public String appLoc;
    public String type;
    public String appDoc;
    public String appDateTime;
    public String docAttitude;
    public String status;
    public String conLoad;//加载
}
