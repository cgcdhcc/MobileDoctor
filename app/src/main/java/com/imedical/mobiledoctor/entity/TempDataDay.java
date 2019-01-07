package com.imedical.mobiledoctor.entity;

import com.imedical.mobiledoctor.util.Desc;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <TempDataDay>
 *
 * <weekDay>每周的第几天</weekDay> <dataDate>日期</dataDate>
 * <inDay>住院第几天</inDays> <operDays>手术后天数</operDays>
 *
 * <PulseList> </PulseList>
 * <OtherList></OtherList>
 * <EventList></EventList>
 *
 * </TempDataDay>
 */
public class TempDataDay implements Serializable {
    private static final long serialVersionUID = 1L;

    public String weekDay;
    public String dataDate;
    public String inDay;
    public String operDays;
    //其它各项数据(纵向)
    public List<TempPulseData> PulseList;
    public List<TempEventData> EventList;
    //此项数据里面，血压数据查能存在两条（上午与下午），所以需要处理后再使用
    private List<TempOtherData> OtherList;

    public TempDataDay() {

    }
    /////////////////////////////////////////////////////////////////////////////////
    //
    //
    /////////////////////////////////////////////////////////////////////////////////

    //合并上下午的血压数据
    @Desc(label = "", type = Desc.TYPE_PARSE_IGNORE)
    private Map<String, TempOtherData> dealtMap = new HashMap<String, TempOtherData>();

    public Map<String, TempOtherData> getOtherColMap() {
        if (dealtMap.size() > 0) {
            return dealtMap;
        }
        if (OtherList == null || OtherList.size() == 0) {
            return dealtMap;
        }

        TempOtherData amTempOtherData = null;//第一次测血压
        for (TempOtherData t : OtherList) {
            TempOtherData data = dealtMap.get(t.itemCode);
            if (data != null) {
                data.itemValue = data.itemValue + "," + t.itemValue;
            } else {
                dealtMap.put(t.itemCode, t);
            }

        }
        return dealtMap;
    }

}
