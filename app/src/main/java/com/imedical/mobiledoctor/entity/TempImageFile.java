package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * @param args <TempImageFile>
 *             <weekNo>周编号</weekNo>
 *             <filePath>体温单图片路径</filePath>
 *             </TempImageFile>
 */

public class TempImageFile implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public String weekNo;
    public String filePath;

}
