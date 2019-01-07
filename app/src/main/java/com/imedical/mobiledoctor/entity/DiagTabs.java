package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * <DiagTabs>
 * <tabId>诊断模板你Id</tabId>
 * <tabName>诊断模板名称</tabName>
 * </DiagTabs>
 *
 * <DiagTabs>
 * <diagICDId>诊断Id</tabId>
 * <diagICDDesc>诊断名称</diagICDDesc>
 * </DiagTabs>
 *
 * @author sszvip
 * <p>
 * 此类为接口入参数，不要随意增删属性
 */
public class DiagTabs implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public DiagTabs() {
    }

    public DiagTabs(String tabId, String tabName) {
        this.tabId = tabId;
        this.tabName = tabName;
    }

    public String tabId;
    public String tabName;

}
