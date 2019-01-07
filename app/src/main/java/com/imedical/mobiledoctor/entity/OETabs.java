package com.imedical.mobiledoctor.entity;

import java.io.Serializable;
import java.util.List;

/**
 * <OETabs>
 * <tabId>页签Id</tabId>
 * <tabName>页签名称</tabName>
 * </OETabs>
 *
 * <OETabs>
 * <subTabId>二级页签Id</subTabId>
 * <subTabName>二级页签名称</subTabName>
 * </OETabs>
 *
 * <OETabs>
 * <arcRowId>医嘱项Id</arcRowId>
 * <arcDesc>医嘱项描述</arcDesc>
 * </OETabs>
 *
 * @author sszvip
 */
public class OETabs implements Serializable {
    /**
     *
     */

    public String tabId;
    public String tabName;

    public String subTabId;
    public String subTabName;

    public String arcRowId;
    public String arcDesc;

    public boolean expand = false;//是否展开
    public List<OETabs> list;//模板详情
}
