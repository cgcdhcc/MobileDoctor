package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * <AntReasonData>
 * <bodyId>感染部位Id</bodyId>
 * <bodyDesc>感染部位</bodyDesc>
 * <submitFlag>是否送检</submitFlag>
 * <reasonId>用药目的Id</reasonId>
 * <reasonDesc>用药目的</reasonDesc>
 *
 * <indicateId>指标Id</indicateId>
 * <indicateDesc>指标</indicateDesc>
 * <timeId>用药时间Id</timeId>
 * <timeDesc>用药时间</timeDesc>
 * <otherCause>其他原因</otherCause>
 *
 * <suscepts>
 * 药敏结果中选中的条目的susceptId，用&链接成字符串
 * </suscepts>
 *
 * <opaId>手术Id,手术信息中单选</opaId>
 *
 * <isEmergency>是否越级使用，0／1</isEmergency>
 * <instructId>用法Id</instructId>
 * <instructDesc>用法</instructDesc>
 * <appStDate>申请开始日期</appStDate>
 *
 * <duration>预计疗程(天)</duration>
 * <consLocId>会诊科室Id</ consLocId>
 * <consLocDesc>会诊科室</ consLocDesc>
 * <consDocId>会诊医生Id</consDocId>
 * <consDocDesc>会诊医生</consDocDesc>
 * </AntReasonData>
 *
 * @author sszvip
 */
public class AntReasonData implements Serializable {
    /**
     *
     */
    public String bodyId;
    public String bodyDesc;
    public String submitFlag;
    public String reasonId;
    public String reasonDesc;

    public String indicateId;
    public String indicateDesc;
    public String timeId;
    public String timeDesc;
    public String otherCause;

    public String opaId;
    public String isEmergency;
    public String instructId;
    public String instructDesc;
    public String appStDate;

    public String duration;
    public String consLocId;
    public String consLocDesc;
    public String consDocId;
    public String consDocDesc;

    public String suscepts;

    public AntReasonData() {
        super();
    }

}
