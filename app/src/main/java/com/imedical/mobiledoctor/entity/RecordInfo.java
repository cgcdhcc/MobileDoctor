package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * @param args <DepartmentInfo>
 *             <departmentId>科室Id</departmentId>
 *             <departmentName>科室名称</departmentName>
 *             <groupId>安全组Id</groupId>
 *             <departmentNote>科室备注</departmentNote>
 *             </DepartmentInfo>
 */

public class RecordInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public String fileName;
    public String filePath;
}
