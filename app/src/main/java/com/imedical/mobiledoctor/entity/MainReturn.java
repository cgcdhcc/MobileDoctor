package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * <MainReturn>
 * <antAppId>抗生素申请Id</antAppId>
 * <userReasonId>用药目的记录Id </userReasonId>
 * <instrId>用法Id</instrId>
 * <instrCode>用法代码</instrCode>
 * <antibId>抗生素使用目的Id</antibId>
 * <isEmergency>是否越级使用，0／1</isEmergency>
 * <priorId>医嘱优先级Id</priorId>
 * <priorDesc>医嘱优先级描述</priorDesc>
 * </MainReturn>
 *
 * @author sszvip
 */
public class MainReturn implements Serializable {
    /**
     *
     */
    public String antAppId;
    public String userReasonId;
    public String antibId;
    public String isEmergency;
    //5
    public String instrId;
    public String instrCode;
    //6
    public String priorId;
    public String priorDesc;

    public MainReturn() {
        super();
    }

}
