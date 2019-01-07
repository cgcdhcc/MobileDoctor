package com.imedical.mobiledoctor.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <TempData>
 * <mainTitle>一级标题</mainTitle>
 * <subTitle>二级标题</subTitle>
 * <patMedNo>住院号</patMedNo>
 * <patName>姓名</patName>
 * <patSex>性别</patSex>
 * <wardDesc>病区</wardDesc>
 * <bedCode>床号</bedCode>
 *
 * <patAge>年龄</patAge>
 * <departName>科室</departName>
 * <mainDoctor>主管医生</mainDoctor>
 * <inDate>入院日期</inDate>
 * <inDays>入院天数</inDays>
 * <weekNo>周数</weekNo>
 *
 * </TempData>
 */
public class TempData implements Serializable {
    private static final long serialVersionUID = 1L;

    public TempData() {
    }

    public String mainTitle = "";
    public String subTitle = "";
    public String patMedNo = "";
    public String patName = "";
    public String patSex = "";
    public String wardDesc = "";
    public String bedCode = "";

    public String patAge = "";
    public String departName = "";
    public String mainDoctor = "";
    public String inDate = "";
    public String inDays = "";
    public String weekNo = "";
    public List<TempDataDay> DataDayList;//7天的数据

    /*
     * 获取单元格内的元素单位
     */
    public TempPulseData getRowColPulseListData(int row, int col) {
        TempPulseData data = null;
        try {
            TempDataDay day = DataDayList.get(col);//7天中的某一天
            if (day.PulseList.size() > row) {
                data = day.PulseList.get(row);//脉搏，体温
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    /*
     * 获取单元格内的元素单位
     */
    public Map<String, TempOtherData> getOtherColMap(int col) {
        TempDataDay day = DataDayList.get(col);//7天中的某一天
        return day.getOtherColMap();
    }

}
