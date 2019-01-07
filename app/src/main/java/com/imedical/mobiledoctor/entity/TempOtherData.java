package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * ---体温脉搏呼吸
 * <TempOtherData>
 * <timeFlag>时间标识，AM上午、PM下午，空是全天只测量一次</timeFlag>
 * <itemCode>
 * 项目代码，标示位，显示顺序
 * <itemCode>
 * <itemName>项目名称，显示的名称<itemName>
 * <itemValue>项目数据</itemValue>
 * </TempOtherData>
 */
public class TempOtherData implements Serializable {
    private static final long serialVersionUID = 1L;

    public TempOtherData() {
    }

    public String timeFlag;
    public String itemCode;
    public String itemName;
    public String itemValue;
}
