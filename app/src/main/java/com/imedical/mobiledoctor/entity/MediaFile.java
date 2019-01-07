package com.imedical.mobiledoctor.entity;

import com.imedical.mobiledoctor.util.Desc;

/**
 * <MediaFile>
 * <fileTpe>文件类型</fileType>
 * <filePath>文件路径</filePath>
 * <fileName>文件名字</fileName>
 * <fileNote>文件备注</fileNote>
 * <recDate>创建日期</recDate>
 * </MediaFile>
 *
 * @author sszvip
 */
public class MediaFile {
    public MediaFile() {
    }

    public String fileTpe;
    public String filePath;
    public String fileName;
    public String recDate;
    public String fileNote;
    @Desc(label = "此属性与xml解析无关，是本地文件名", type = Desc.TYPE_PARSE_IGNORE)
    public boolean isLocal;
}
