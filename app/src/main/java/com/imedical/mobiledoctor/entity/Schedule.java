package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * Created by dashan on 2017/5/25.
 */

public class Schedule implements Serializable {
    public String scheduleCode;//>17209||399</scheduleCode>
    public String scheduleDate;//>2017-05-25</scheduleDate>
    public String weekDay;//>星期四</weekDay>
    public String departmentName;//>风湿免疫科门诊</departmentName>
    public String sessionName;//>上午</sessionName>
    public String availableNum;//>3</availableNum>
    public String appedNum;//>46</appedNum>
}
