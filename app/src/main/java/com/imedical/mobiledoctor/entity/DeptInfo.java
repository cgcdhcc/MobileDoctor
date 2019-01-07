package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * <DeptInfo>
 * <locId>科室Id</locId>
 * <locDesc>科室名称</locDesc>
 * <defaultFlag>默认标识Y／N</defaultFlag>
 * </DeptInfo>
 *
 * @author sszvip
 */
public class DeptInfo implements Serializable {
    /**
     *
     */
    public String locId;
    public String locDesc;
    public String defaultFlag;


    public DeptInfo() {
        super();
    }

    public DeptInfo(String locId, String locDesc, String defaultFlag) {
        super();
        this.locId = locId;
        this.locDesc = locDesc;
        this.defaultFlag = defaultFlag;
    }


}
