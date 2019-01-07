package com.imedical.mobiledoctor.util;

import java.io.Serializable;

public class WeekDate implements Serializable {
    public String date;
    public String shortdate;
    public String week;
    public String shortweek;
    public String haveschedual = "0";//是否有排班,0没有，1有

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShortdate() {
        return shortdate;
    }

    public void setShortdate(String shortdate) {
        this.shortdate = shortdate;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getShortweek() {
        return shortweek;
    }

    public void setShortweek(String shortweek) {
        this.shortweek = shortweek;
    }

}
