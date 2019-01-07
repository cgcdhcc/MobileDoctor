package com.imedical.mobiledoctor.entity;

/**
 * @author sszvip
 *
 * <SysParameter>
 * <serverURL>服务器URL，加载存储在本地</serverURL>
 * <androidVersion>Android版本号</androidVersion>
 * <iOSVersion>iOS版本号</iOSVersion>
 * <androidAppURL>Android APK 下载地址</androidAppURL>
 * <iOSAppURL>iOS APP 下载地址</iOSAppURL>
 * <hospitalId>医院ID</hospitalId>
 * <userId>手机His业务使用的用户Id<userId>
 * <groupId>手机His业务使用的用户安全组</groupId>
 * <logonLocId>手机His业务使用的科室Id</logonLocId>
 * <sourceType>手机His业务定义的来源／当前为His</sourceType>
 * <appMethod>手机预约方式的代码/当前WIN</appMethod>
 * <regDays>手机可挂号的天数</regDays>
 * <appDays>手机可预约的天数</appDays>
 * <billDays>缴费有效天数</billDays>
 * </SysParameter>
 */
public class SysParameter {
    public String serverURL;
    public String androidVersion;
    public String androidAppURL;
    public String hospitalId;
    public String userId;
    public String groupId;
    public String logonLocId;
    public String sourceType;
    public String appMethod;
    public String regDays;
    public String appDays;
    public String billDays;
}
