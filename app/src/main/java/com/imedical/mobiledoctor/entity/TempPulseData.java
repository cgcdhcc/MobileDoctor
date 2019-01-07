package com.imedical.mobiledoctor.entity;

import com.imedical.mobiledoctor.util.Desc;

import java.io.Serializable;

/**
 * ---体温脉搏呼吸
 * <TempPulseData>
 * <timeNo>时刻顺序号</timeNo>
 * <temp>体温</temp>
 * <cool>
 * 物理降温，以红“○”表示，并用红虚线相连
 * <cool>
 * <method>体温测量方式：口表、肛表、腋表</method>
 * <pulse>脉搏</pulse>
 * <heart>心率</heart>
 * <breath>呼吸</breath>
 * </TempPulseData>
 */
public class TempPulseData implements Serializable {
    private static final long serialVersionUID = 1L;
    @Desc(label = "", type = Desc.TYPE_PARSE_IGNORE)
    public static final String SHAEP_circle_fill = "1";
    @Desc(label = "", type = Desc.TYPE_PARSE_IGNORE)
    public static final String SHAEP_circle = "2";
    @Desc(label = "", type = Desc.TYPE_PARSE_IGNORE)
    public static final String SHAEP_X = "3";


    public TempPulseData() {
    }

    public String timeNo = "";
    public String temp = "";
    public String cool = "";
    public String method = "";
    public String pulse = "";
    public String heart = "";
    public String breath = "";

    public String getShapeFlag() {
        String flag = "";
        if ("脉搏".equals(method) || "口表".equals(method)) {
            flag = SHAEP_circle_fill;
        } else if ("腋表".equals(method)) {
            flag = SHAEP_X;
        } else {
            flag = SHAEP_circle;
        }

        return flag;
    }

}
