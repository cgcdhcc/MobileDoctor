package com.imedical.mobiledoctor.entity;

import com.imedical.mobiledoctor.util.Desc;

import java.io.Serializable;

/**
 * <Request>
 * <userCode>用户名</userCode>
 * <admId>就诊Id</admId>
 * <showIndex>当前医嘱在临时数据中的Id/增加时为空</showIndex>
 * <arcItemId>医嘱项Id</arcItemId>
 * <priorId>医嘱优先级Id</priorId>
 *
 * <firstTimes>长期医嘱首日次数
 * 只有为长期医嘱才有值</firstTimes>
 * <ordStartDate>医嘱开始日期/YYYY-MM-DD</ordStartDate>
 * <ordStartTime>医嘱开始时间/HH:MM:SS</ordStartTime>
 * <recLocId>接收科室Id</recLocId>
 * <ordNote>医嘱备注</ordNote>
 *
 * <doseQty>单次剂量</doseQty>
 * <doseUomId>单次剂量单位Id</doseUomId>
 * <freqId>频次Id</freqId>
 * <instrId>用法Id</instrId>
 * <masterSeqNo>关联医嘱序号</masterSeqNo>
 *
 * <skinTest>皮试标识（Y/N）</skinTest>
 * <skinActId>皮试备注Id</skinActId>
 * <skinAct>皮试备注</skinAct>
 * <packQty>整包装数量/界面上的数量</packQty>
 * <antibId>抗生素用药原因：1/治疗、2/预防</antibId>
 * <departmentId>登录科室Id</departmentId>
 * <!--- 第二次新添加的内容 ---->
 *
 * <dispense>
 * 是否需要配液中心配药的标识（Y／N）
 * </dispense>
 * <flowRate>滴速</flowRate>
 * <rateUnit>滴速单位</rateUnit>
 *
 * <antAppId>抗菌药物申请Id</antAppId>
 * <userReasonId>
 * 抗生素用药目的Id,与antAppId有很强的关联性
 * </userReasonId>
 *
 * </Request>
 *
 * @author sszvip
 * 医嘱录入提交表单
 */
public class FormArcimItem implements Serializable {
    @Desc(label = "医嘱项Id", type = "")
    public String arcItemId;//主键
    public String labSpecCode;

    public String userCode;
    public String admId;
    public String priorId;

    public String firstTimes;
    public String ordStartDate;
    public String ordStartTime;
    public String recLocId;
    public String ordNote;
    @Desc(label = "单次剂量", type = "")
    public String doseQty;
    @Desc(label = "计价单位", type = "")
    public String billUom;

    public String doseUomId;
    public String freqId;
    public String instrId;
    public String masterSeqNo;
    //<skinTest>皮试标识（Y/N）</skinTest>
    //<skinActId>皮试备注Id</skinActId>
    public String skinTest;
    public String skinActId;

    public String packQty;
    @Desc(label = "抗生素用药原因：1/治疗、2/预防", type = "")
    public String antibId = "";
    public String departmentId = "";
    public String showIndex; //
    //第二次需求变动时新添加
    public String dispense = "";//是否需要配液中心配药的标识（Y／N）
    public String flowRate = "";//滴速
    public String rateUnit = "";//单位
    public String antAppId = "";//抗菌药物申请Id
    public String userReasonId = "";//抗生素用药目的Id,与antAppId有很强的关联性
    public String urgentFlag;//是否加急（Y／N）
    public String mianIns;//是否医保（Y／N）

    public FormArcimItem() {
    }
}
