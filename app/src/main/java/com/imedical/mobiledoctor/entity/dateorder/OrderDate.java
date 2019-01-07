package com.imedical.mobiledoctor.entity.dateorder;

import java.io.Serializable;

/**
 * @author LED
 *
 * <RegisterInfo>
 * <regDate>日期</regDate>
 * <regFlagAm>上午是否有排班（Y/N）</regFlagAm>
 * <regNumAm>上午预约数</regNumAm>
 * <regFlagPm>下午是否有排班（Y/N）</regFlagAmPm>
 * <regNumPm>下午预约数</regNumPm>
 * </RegisterInfo>
 */
public class OrderDate implements Serializable {
    private static final long serialVersionUID = 1L;

    public OrderDate() {
    }

    public String regDate;
    public String regFlagAm;
    public String regNumAm;
    public String regFlagPm;
    public String regNumPm;
}
