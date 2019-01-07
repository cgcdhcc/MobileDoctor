package com.imedical.mobiledoctor.entity;


import com.imedical.mobiledoctor.util.Desc;

import java.io.Serializable;
import java.util.List;

/**
 * 临床事件基础数据记录
 * <ActCategoryData>
 * <categoryCode>项目分类代码</categoryCode>
 * <dataTypeCode>项目分类明细代码</dataTypeCode>
 * <ActRecordList>
 * <ActRecord></ActRecord>
 * …
 * <ActRecord></ActRecord>
 * </ActRecorfList>
 * </ActCategoryData>
 *
 * @author sszvip
 */
public class ActCategoryData implements Serializable {
    private static final long serialVersionUID = 1L;

    public ActCategoryData() {
    }

    public String categoryCode;
    public String dataTypeCode;
    @Desc(label = "记录", type = Desc.TYPE_LIST)
    public List<ActRecord> ActRecordList;

    @Override
    public boolean equals(Object o) {
        boolean is = true;
        ActCategoryData a = (ActCategoryData) o;
        if (!dataTypeCode.equals(a.dataTypeCode)) {
            is = false;
        }
        return is;
    }

    ;

    @Override
    public int hashCode() {
        return categoryCode.hashCode() + dataTypeCode.hashCode();
    }
}
