package com.imedical.mobiledoctor.entity.dateorder;

import java.io.Serializable;

public class DoctorSchedule implements Serializable {
    public String timeRangeId;//>出诊时段Id </timeRangeId>
    public String timeRangeDesc;//>出诊时段描述</timeRangeDesc>
    public String startTime;//>开始时间</startTime>
    public String endTime;//>结束时间</endTime>
    public String regLimit;//>挂号限额</regLimit>
    public String regNum;//>已挂号数</regNum>
    public String availableNum;//>;可用挂号数</availableNum>
    public String feeSum;//>费用</feeSum>
    public String sessionTypeDesc;//>出诊类型</sessionTypeDesc>
    public String doctorAlias;//>出诊号别</doctorAlias>
    public String scheduleId;//>排班记录Id </scheduleId>
    public String visitType;//出诊类型图文T/视频V
}
