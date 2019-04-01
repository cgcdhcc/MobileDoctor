package com.imedical.mobiledoctor.entity;



import com.imedical.mobiledoctor.util.Desc;

import java.io.Serializable;


public class CanUseRes implements Serializable {
    @Desc(label = "可用日期", type = "") public String bookedDate;


    public CanUseRes(String date) {
        bookedDate=date;
    }

}
