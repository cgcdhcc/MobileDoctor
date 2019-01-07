package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * <OtherInfo>
 *
 * <priorId>优先级Id</priorId>
 * <priorDesc>优先级</priorDesc>
 *
 * <firstTimes>长期医嘱首日次数</firstTimes>
 *
 * <freqId>默认频次Id</freqId>
 * <freqCode>默认频次</freqCode>
 *
 * <packQty>整包装数量/界面上的数量</packQty>
 * <billUom>计价单位/整包装单位/界面上数量后面的单位</billUom>
 * <dispense>
 * 是否需要配液中心配药的标识（Y／N）
 * </dispense>
 * </OtherInfo>
 *
 * @author sszvip
 * <p>
 * 此类为接口入参数，不要随意增删属性
 */
public class OtherInfo implements Serializable {
    /**
     *
     */
    public String priorId;
    public String priorDesc;
    public String firstTimes;
    public String freqId;
    public String freqCode;
    public String packQty;
    public String billUom;
    public String dispense;//是否需要配液中心配药的标识（Y／N）
}
