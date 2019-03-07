package com.imedical.mobiledoctor.entity;

import com.imedical.im.entity.LabSpec;
import com.imedical.mobiledoctor.util.Desc;

import java.io.Serializable;
import java.util.List;

/**
 * <ArcimItem>
 * <arcimId>医嘱项Id：不显示</arcimId>
 * <arcimCode>医嘱项代码：不显示</arcimCode>
 * <arcimDesc>医嘱项名称</arcimDesc>
 * <freqCode>医嘱频次</freqCode>
 * <phUomDesc>药品规格</phUomDesc>
 * <subCatDesc>医嘱子类</subCatDesc>
 * <itemPrice>单价</itemPrice>
 * <billUom>计价单位</billUom>
 * <stockQty>库存:为0显示红色</stockQty>
 * <recLoc>接收科室</recLoc>
 * </ArcimItem>
 *
 * <ArcimItem>
 * <arcimId>医嘱项目Id</arcimId>
 * <arcimDesc>医嘱项目名称</arcimDesc>
 * <arcType>医嘱类型：R药品、L检验、C检查、N普通</arcType>
 * <priorId>默认优先级Id</priorId>
 * <priorDesc>默认优先级</priorDesc>
 * <freqId>默认频次Id</freqId>
 * <freqCode>默认频次</freqCode>
 * <instrId>默认用法Id</instrId>
 * <instrCode>默认用法</instrCode>
 * <itemPrice>医嘱价格</itemPrice>
 * <packQty>整包装数量/界面上的数量</packQty>
 * <billUom>计价单位/整包装单位/界面上数量后面的单位</billUom>
 * <arcMsg>提示信息：如果存在首先提示再加载数据</arcMsg>
 * <needSkin>是否需要皮试：Y需要皮试，N不需要皮试</neddSkin>
 * <skinActId>皮试备注Id，只有需要皮试时才存在</skinActId>
 * <skinAct>皮试备注，只有需要皮试时才存在</skinAct>
 * <labSpec>检验标本：只有医嘱类型为检验（L）时才存在</labSpec>
 * <labSpecCode> 检验标本代码</labSpecCode>
 * <doseQtyList>
 * 单次剂量信息，只有是药品医嘱才存在，可能存在多个
 * <DoseQtyInfo>
 * <doseQty>单次剂量</doseQty>
 * <doseUomDesc>单次剂量单位</doseUomDesc>
 * <doseUomId>单次剂量单位Id</doseUomId>
 * <defaultFlag>默认标识Y／N </defaultFlag>
 * </DoseQtyInfo>
 * </doseQtyList>
 * <recLocList>
 * 医嘱接收科室，存在多个
 * <DeptInfo>
 * <locId>科室Id</locId>
 * <locDesc>科室名称</locDesc>
 * <defaultFlag>默认标识Y／N</defaultFlag>
 * </DeptInfo>
 * </recLocList>
 * <isAntib>抗生素标识,0、1，当为1时弹出抗生素用药目的的选择窗口，治疗(1)、预防(2)</isAntib>
 * </ArcimItem>
 * <p>
 * ----------下面是获取默认参数时返回的xml--------------------------------------
 * <ArcimItem>
 * <showIndex>临时数据中的Id/更新时作为关键Id</showIndex>
 * <arcimId>医嘱项目Id</arcimId>
 * <arcimDesc>医嘱项目名称</arcimDesc>
 * <arcType>医嘱类型：R药品、L检验、C检查、N普通</arcType>
 * <priorId>默认优先级Id</priorId>
 * <priorDesc>默认优先级</priorDesc>
 * <freqId>默认频次Id</freqId>
 * <freqCode>默认频次</freqCode>
 * <instrId>默认用法Id</instrId>
 * <instrCode>默认用法</instrCode>
 * <itemPrice>医嘱价格</itemPrice>
 * <packQty>整包装数量/界面上的数量</packQty>
 * <billUom>计价单位</billUom>
 * <arcMsg>提示信息：如果存在首先提示再加载数据</arcMsg>
 * <needSkin>是否需要皮试：Y需要皮试，N不需要皮试</neddSkin>
 * <skinActId>皮试备注Id，只有需要皮试时才存在</skinActId>
 * <labSpec>检验标本：只有医嘱类型为检验（L）时才存在</labSpec>
 * <labSpecCode> 检验标本代码</labSpecCode>
 * <doseQtyList>
 * 单次剂量信息，只有是药品医嘱才存在，可能存在多个
 * <DoseQtyInfo>
 * <doseQty>单次剂量</doseQty>
 * <doseUomDesc>单次剂量单位</doseUomDesc>
 * <doseUomId>单次剂量单位Id</doseUomId>
 * <defaultFlag>默认标识Y／N </defaultFlag>
 * </DoseQtyInfo>
 * </doseQtyList>
 * <recLocList>
 * 医嘱接收科室，存在多个
 * <DeptInfo>
 * <locId>科室Id</locId>
 * <locDesc>科室名称</locDesc>
 * <defaultFlag>默认标识Y／N</defaultFlag>
 * </DeptInfo>
 * </recLocList>
 * <ordStartDate>开始日期</ordStartDate>
 * <ordStartTime>开始时间</ordStartTime>
 * <masterSeqNo>关联号<masterSeqNo>
 * <firstTimes>长嘱首日次数<firstTimes>
 * <ordNote>医嘱备注</ordNote>
 * <anntibId>抗生素用药原因：1/治疗、2/预防</anntibId>
 * </ArcimItem>
 * <!--  医嘱录入列表 -->
 * <ArcimItem>
 * <showIndex>当日当次医嘱录入医嘱编号<showIndex>
 * <ordState>医嘱状态，空/核实，
 * 核实时不能编辑不能选中，删除按钮不显示</ordState>
 * <arcimDesc>医嘱项目名称</arcimDesc>
 * <priorDesc>优先级</priorDesc>
 * <freqCode>频次</freqCode>
 * <doseQty>单次剂量</doseQty>
 * <doseUom>单次剂量单位</doseUom>
 * <instrCode>用法</instrCode>
 * <recLoc>接收科室</recLoc>
 * <skinAct>皮试备注</skinAct>
 * <p>
 * <!-- 第二次需求变动新添加20131029  -->
 *
 * <isAntib>
 * 抗生素标识,0、1，当为1时弹出抗生素用药目的的选择窗口，通过50919获取
 * </isAntib>
 * <dispense>
 * 是否需要配液中心配药的标识（Y／N）
 * </dispense>
 * <poisonType>
 * 管制分类:F/不允许开 A／受限需提示可以开 P／需申请 S／非限制使用
 * A: 提示 药品的管制分类是受控制的，您确认开吗？
 * P：弹出抗菌药物申请界面，传入参数admId、arcimId、departmentId、userReasonId四个参数，返回antAppId、userReasonId、instrId、instrCode、antibId、isEmergency、priorId、priorDesc八个参数，相关数据通过接口 2.13抗菌药物 加载获取
 * </poisonType>
 * </ArcimItem>
 * 备注说明：
 * 1、	packQty 药品的整包装数量、普通医嘱的数量
 * 2、	billUom 计价单位，整包装单位
 * 3、	当医嘱类型为R药品医嘱，并且医嘱优先级为 出院带药时，数量可填，当医嘱类型不为药品时，可填，检验、简单一般默认返回为1
 * <antibDesc>抗生素用药目的</antibDesc>
 * <flowRate>滴速</flowRate>
 * <rateUnit>滴速单位Id</rateUnit>
 * <rateUnitDesc>滴速单位描述</rateUnitDesc>
 * <antAppId>抗菌药物申请Id</antAppId>
 * <userReasonId>
 * 抗生素用药目的Id,与antAppId有很强的关联性
 * </userReasonId>
 * <poisonType>
 *
 *
 * </ArcimItem>
 *
 * @author sszvip
 */
public class ArcimItem implements Serializable {
    /**
     *
     */
    public String arcimId;
    public String arcimCode;
    public String arcimDesc = "";
    public String phUomDesc = "";
    public String subCatDesc = "";
    //public String freqCode;下方有
    //public String itemPrice;下方有
    //public String billUom;下方有
    public String stockQty = "";
    public String recLoc = "";
    public List<LabSpec> labSpecList;

    //第二个接口里返回的参数
    public String arcType = "";
    public String priorId = "";
    public String priorDesc = "";
    public String freqId = "";
    public String freqCode = "";
    public String instrId = "";
    public String instrCode = "";
    public String itemPrice = "";
    public String packQty = "";
    public String billUom = "";
    public String arcMsg = "";
    public String needSkin = "";
    public String skinActId = "";
    public String skinAct = "";
    //<labSpec>检验标本：只有医嘱类型为检验（L）时才存在</labSpec>
    @Desc(label = "检验标本", type = "") //
    public String labSpec = "";
    public String labSpecCode = "";

    @Desc(label = "单次剂量信息", type = Desc.TYPE_LIST) //使用此注解类型会自动解析到集合中
    public List<DoseQtyInfo> doseQtyList;
    @Desc(label = "医嘱接收科室，存在多个", type = Desc.TYPE_LIST)
    public List<DeptInfo> recLocList;
    //已保存未审核医嘱加载当前医嘱信息
    public String showIndex;

    public String ordStartDate = "";
    public String ordStartTime = "";
    public String masterSeqNo = "";
    public String firstTimes = "";
    public String antibId = "";
    public String ordNote = "";
    //------医嘱列表
    public String ordState;
    //<!-- 第二次需求变动新添加20131029  -->
    public String isAntib = "";//抗生素标识,0、1，当为1时弹出抗生素用药目的的选择窗口，通过50919获取
    public String dispense = "";//是否需要配液中心配药的标识（Y／N）
    /**
     * 管制分类:F/不允许开 A／受限需提示可以开 P／需申请 S／非限制使用
     * A: 提示 药品的管制分类是受控制的，您确认开吗？
     * P：弹出抗菌药物申请界面，传入参数admId、arcimId、departmentId、userReasonId四个参数，返回antAppId、userReasonId、instrId、instrCode、antibId、isEmergency、priorId、priorDesc八个参数，相关数据通过接口 2.13抗菌药物 加载获取
     */
    public String poisonType = "";// 管制分类
    public String doseQty = "";
    public String flowRate = "0";

    public String antibDesc;
    public String rateUnit;
    public String rateUnitDesc;
    public String antAppId;//抗菌药物申请Id
    public String userReasonId;//抗生素用药目的Id,与antAppId有很强的关联性
    public String reasonDesc;
    public String isEmergency;

//	备注说明：
//	1、	packQty 药品的整包装数量、普通医嘱的数量
//	2、	billUom 计价单位，整包装单位
//	3、	当医嘱类型为R药品医嘱，并且医嘱优先级为 出院带药时，数量可填，当医嘱类型不为药品时，可填，检验、简单一般默认返回为1


    public ArcimItem() {
    }

    public String getAnntibId() {
        if (antibId == null) {
            antibId = "";
        }
        return antibId;
    }

    public String getOrdStartDate() {
        if (ordStartDate == null) {
            return "";
        }
        return ordStartDate;
    }

    public String getOrdStartTime() {
        if (ordStartTime == null) {
            return "";
        }
        return ordStartTime;
    }

    public DeptInfo getDeptDefaultSelected() {
        DeptInfo dept = null;

        if (recLocList != null) {// 接收科室
            for (DeptInfo d : recLocList) {
                if ("Y".equals(d.defaultFlag)) {
                    dept = d;
                    break;
                }
            }
        }
        return dept;
    }

    public DoseQtyInfo getDoseQtyInfoDefaultSelected(ArcimItem arc) {
        DoseQtyInfo v = null;
        // 设置默认的单次计量
        if (arc.doseQtyList != null) {
            for (DoseQtyInfo d : arc.doseQtyList) {
                if ("Y".equals(d.defaultFlag)) {
                    v = d;
                    break;
                }
            }
        }
        return v;
    }

    public String getRateUnit() {
        return (rateUnit == null) ? "" : rateUnit;
    }

    public CharSequence getAntibId() {

        return (antibId == null) ? "" : antibId;
    }

    public CharSequence getSkinActId() {
        return (skinActId == null) ? "" : skinActId;
    }

    public CharSequence getInstrId() {
        if (instrId == null) {
            instrId = "";
        }
        return instrId;
    }

    public CharSequence getPriorId() {
        if (priorId == null) {
            priorId = "";
        }
        return priorId;
    }


}
