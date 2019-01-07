package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * <BaseInfo>
 * <arcimDesc>药品名称</arcimDesc>
 * <arcCatDesc>药品分类</arcCatDesc>
 * <careName>医护人员</careName>
 * <careType>医护人员职称</careType>
 * <poisonDesc>管制分类</poisonDesc>
 * <appFlag>
 * 需要审核 标识  0/1，当为1时显示申请信息
 * </appFlag>
 * <consultFlag>
 * 需要会诊 标识 0/1，当为1时显示会诊信息
 * </consultFlag>
 * </BaseInfo>
 *
 * @author sszvip
 */
public class BaseInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    public BaseInfo() {
    }

    public String arcimDesc;
    public String arcCatDesc;
    public String careName;
    public String careType;
    public String poisonDesc;
    public String appFlag;
    public String consultFlag;
}
