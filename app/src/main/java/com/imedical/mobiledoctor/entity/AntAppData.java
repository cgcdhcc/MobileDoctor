package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * AntAppData>
 * <antAppId>抗生素用药申请Id</antAppId>
 * <userReasonId>抗生素使用目的Id</userReasonId>
 * <admId>患者就诊Id</admId>
 * <arcimId>医嘱项Id</arcimId>
 * <patName>患者姓名</patName>
 *
 * <patBed>患者床号</patBed>
 * <appDocName>申请医生</appDocName>
 * <arcimDesc>药品名称</arcimDesc>
 * <consInfo>会诊信息</cosInfo>
 * <appDateTime>申请时间</appDateTime>
 *
 * <conLoad>继续加载的标识，如果该字段存在，说明后面还有未加载完的内容，加载后面的内容需要传入该参数</conLoad>
 * </AntAppData>
 *
 * @author sszvip
 */
public class AntAppData implements Serializable {
    private static final long serialVersionUID = 1L;

    public AntAppData() {
    }

    public String antAppId;
    public String userReasonId;
    public String admId;
    public String arcimId;

    public String patName;
    public String patBed;
    public String appDocName;
    public String arcimDesc;
    public String consInfo;
    public String appDateTime;
    public String appStatus;
    public String conLoad;
}
