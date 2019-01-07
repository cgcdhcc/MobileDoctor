package com.imedical.mobiledoctor.entity;

import com.imedical.mobiledoctor.util.Desc;

import java.io.Serializable;
import java.util.List;

/**
 * <ViewData>
 * <searchDate>当页显示（本次查询）的开始日期</searchDate>
 * <weekNo>当前周</weekNo>
 * <ActDataList>
 * <ActCategoryData></ActCategoryData>
 * …
 * <ActCategoryData></ActCategoryData>
 * </ActDataList>
 * </ViewData>
 *
 * @author sszvip
 * 获取dicom图像 请求参数
 */
public class ViewData implements Serializable {
    private static final long serialVersionUID = 1L;

    public ViewData() {
    }

    public String searchDate = "";
    public String weekNo = "";
    @Desc(label = "", type = Desc.TYPE_LIST)
    public List<ActCategoryData> ActDataList;

}
