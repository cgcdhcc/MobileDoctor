package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * <UisView>
 * <uisUrl>UIS服务器地址</uisUrl>
 * <rsaUrl>RSA服务器地址</rsaUrl>
 * <applicationId>ValueOfApplicationID</applicationId>
 * <userViewId>ValueOfUserViewID</userViewId>
 * <columnName>ValueOfColumnName</columName>
 * <column>ValueOfColumn</column>
 * <userName>用户名</userName>
 * <password>口令</password>
 * <nowDate>当前日期 YYYY－MM－DD</nowDate>
 * <nowTime>当前时间 HH:MM:SS</nowTime>
 * </UisView>
 *
 * @author sszvip
 * 获取dicom图像 请求参数
 */
public class UisView implements Serializable {
    private static final long serialVersionUID = 1L;

    public UisView() {
    }

    public String uisUrl;
    //rsaUrl http://10.160.16.131/mhealth/Default.aspx
    public String rsaUrl;
    public String applicationId;
    public String userViewId;
    public String columnName;
    public String column;
    public String userName;
    public String password;
    public String nowDate;
    public String nowTime;
}
