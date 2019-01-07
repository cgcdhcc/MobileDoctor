package com.imedical.mobiledoctor.entity;

/**
 * <userCode>用户名</userCode>
 * <departmentId>登录科室Id</departmentId>
 * <admId>就诊Id</admId>
 * <arcimId>医嘱项Id</arcimId>
 * <userReasonId>用药目的记录Id</userReasonId>
 *
 * <antAppId>抗生素用药申请Id</antAppId>
 * <bodyId>感染部位Id</bodyId>
 * <submitFlag>是否送检</submitFlag>
 * <reasonId>用药目的Id</reasonId>
 * <indicateId>指标Id</indicateId>
 * <timeId>用药时间Id</timeId>
 * <otherCause>其他原因</otherCause>
 * <suscepts>药敏结果中选中的条目的susceptId，用&链接成字符串</suscepts>
 * <opaId>手术Id,手术信息中单选</opaId>
 * <appFlag>需要审核 标识  0/1</appFlag>
 * <isEmergency>是否越级使用</isEmergency>
 * <instructId>用法Id</instructId>
 * <appStDate>申请开始日期</appStDate>
 * <duration>预计疗程(天)</duration>
 * <consultFlag>需要会诊 标识 0/1</consultFlag>
 * <consLocId>会诊科室Id</ consLocId>
 * <consDocId>会诊医生Id</consDocId>
 *
 * @author ssz
 */
public class ParamAnti {
    public String antAppId;//抗生素用药申请Id

    public String instructId;
    public String appStDate;
    public String duration;
    public String consultFlag;
    public String consLocId;

    public String consDocId;

    public String otherCause;
    public String suscepts;
    public String opaId;
    public String appFlag;
    public String isEmergency;

    public String bodyId;
    public String submitFlag;
    public String reasonId;
    public String indicateId;
    public String timeId;

    public String userCode;
    public String departmentId;
    public String admId;
    public String arcimId;
    public String userReasonId;

}
