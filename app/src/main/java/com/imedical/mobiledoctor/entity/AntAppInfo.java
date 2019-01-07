package com.imedical.mobiledoctor.entity;

/**
 * <ResultList>
 * <AntAppInfo>
 * <antAppId>71535</antAppId>
 * <appDocName>吴昌亮 主治医师</appDocName>
 * <appDateTime>2016-12-13 07:05:05</appDateTime>
 * <patName>01床 朱祥民</patName>
 * <consInfo>临床药学科 徐文 无会诊记录</consInfo>
 * <arcimDesc>[*注射用亚胺培南西司他丁钠(泰能)[0.5g:0.5g]] [西药特殊抗生素针剂] [特殊使用级抗菌药物]</arcimDesc>
 * <appInfo>用法:静脉滴注 开始日期:2016-12-13 疗程:1天</appInfo>
 * <reasonDesc>治疗-外科-经验</reasonDesc>
 * <indicateDesc>其他</indicateDesc>
 * <submitFlag>N</submitFlag>
 * <appStatus>申请</appStatus>
 * </AntAppInfo>
 */
public class AntAppInfo {
    public String antAppId;//>抗生素用药申请Id</antAppId>
    public String patName;//>患者姓名（包含了床号＋姓名）</patName>
    public String arcimDesc;//>抗菌药物信息包含了药品级别，名字会很长</arcimDesc>
    public String appDocName;//>申请医师信息包含了医师级别</appDocName>
    public String appDateTime;//>申请时间</appDateTime>
    public String appInfo;//>申请信息，包含是否越级 用法 开始日期 疗程 </appInfo>
    public String consInfo;//>会诊科室包含了会诊科室 会诊医生 会诊结果</consInfo>
    public String reasonDesc;//>使用目的</reasonDesc>
    public String indicateDesc;//>指征</indicateDesc>
    public String bodyDesc;//>感染部位</bodyDesc>
    public String submiteFlag;//>病原学送检</submiteFlag>
    public String otherCause;//>其他原因</otherCause>
    public String opTimeDesc;//>预防用药时间</opTimeDesc>
    public String appStatus;//>申请状态  申请／审核／拒绝</appStatus>
}
