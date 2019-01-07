package com.imedical.mobiledoctor.entity;

import com.imedical.mobiledoctor.util.Desc;

import java.io.Serializable;

/**
 * <TimeLineCategory>
 * <categoryCode>项目分类代码</categoryCode>
 * <categoryDesc>项目分类名称</categoryDesc>
 * <dataTypeCode>项目分类明细代码</dataTypeCode>
 * <dataTypeDesc>项目分类明细描述</dataTypeDesc>
 * <viewType>显示类型</ViewType>
 * </TimeLineCategory>
 *
 * @author sszvip
 * 获取dicom图像 请求参数
 */
public class TimeLineCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    public TimeLineCategory() {
    }

    public String categoryCode;
    public String categoryDesc;
    public String dataTypeCode;
    public String dataTypeDesc;
    public String viewType;
    @Desc(label = "是否选中", type = Desc.TYPE_PARSE_IGNORE)//自定义，不被用于解析
    public boolean isSelected = false;

    @Override
    public boolean equals(Object o) {
        boolean is = true;

        TimeLineCategory t = (TimeLineCategory) o;
        if (!dataTypeCode.equals(t.dataTypeCode)) {
            return false;
        }

        return is;
    }

    ;

    @Override
    public int hashCode() {
        return dataTypeCode.hashCode();
    }
}
