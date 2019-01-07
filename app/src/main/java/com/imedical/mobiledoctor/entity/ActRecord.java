package com.imedical.mobiledoctor.entity;

import com.imedical.mobiledoctor.util.Desc;

import java.io.Serializable;
import java.util.List;

/**
 * <ActRecord>
 * <actDate>事件发生日期</actDate>   (对于长期医嘱是开始日期)
 * <actTime>事件发生时间</actTime>   (对于长期医嘱是开始时间)
 * <dataValue>数据值</dataValue>
 * <summary>摘要</summary>
 * <parameters>与数据类型相关</parameters>
 * <actDesc>事件描述</actDesc>
 * <actCode>事件代码</actCode>
 * <positive>阳性标示：0、1，1时显示为红色</positive>
 * <SubActRecordList>
 * <ActRecord>…</ActRecord>
 * </SubActRecordList>  （目前检查和检验用）
 * <endDate>停止日期</endDate>     （目前只有长期医嘱用）
 * <endTime>停止时间</endTime>		（目前只有长期医嘱用）
 * <objId>业务指针id</objId>   	（目前只有长期医嘱用，医嘱id）
 * </ActRecord>
 *
 * @author sszvip
 * 集成视图信息
 */
public class ActRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    public ActRecord() {
    }

    public String actDate;
    public String actTime;
    public String dataValue;
    public String summary;
    public String parameters;
    public String actDesc;
    public String actCode;
    public String positive;
    public String endDate;
    public String endTime;
    public String objId;
    @Desc(label = "子列表,目前检查和检验用,20130909", type = Desc.TYPE_LIST)
    public List<ActRecord> SubActRecordList;
}
