package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * 危急值
 * <ReportData>
 * <admId>患者就诊Id</admId>
 * <ordItemId>医嘱项Id</ordItemId>
 * <labNo>标本号</labNo>
 * <patName>病人姓名</patName>
 * <arcimDesc>医嘱名称</arcimDesc>
 *
 * <specName>标本</specName>
 * <appDocName>申请医生</appDocName>
 * <reportMemo>危急提示</reportMemo>
 * <authDateTime>报告时间</authDateTime>
 * <conLoad>继续加载标识</conLoad>
 * </ReportData>
 *
 * @author sszvip
 */
public class ReportData implements Serializable {
    private static final long serialVersionUID = 1L;

    public ReportData() {
    }

    public String admId;
    public String ordItemId;
    public String labNo;
    public String patName;
    public String arcimDesc;

    public String specName;
    public String appDocName;
    public String reportMemo;
    public String authDateTime;
    public String conLoad;
}
