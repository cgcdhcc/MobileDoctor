package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * <TempEventData>
 *
 * <timeNo>时刻顺序号</timeNo>
 * <pos>显示的位置：U／D 上、下</pos>
 * <content>项目内容</content>
 *
 * </TempEventData>
 */
public class TempEventData implements Serializable {
    private static final long serialVersionUID = 1L;

    public TempEventData() {
    }

    public String timeNo = "";
    public String pos = "";
    public String content = "";
}
