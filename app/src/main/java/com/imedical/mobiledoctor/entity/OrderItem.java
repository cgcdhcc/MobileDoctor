package com.imedical.mobiledoctor.entity;

import com.imedical.mobiledoctor.util.Desc;

import java.io.Serializable;

/**
 * @author sszvip
 * <p>
 * 此类为接口入参数，不要随意增删属性
 */
public class OrderItem implements Serializable {
    /**
     *
     */
    @Desc(label = "显示序号", type = "")
    public String ordIndex;
    @Desc(label = "医嘱Id", type = "")
    public String ordItemId;
    @Desc(label = "医嘱关联编号", type = "")
    public String seqNo;
    @Desc(label = "医嘱大类", type = "")
    public String itemCat;
    @Desc(label = "医嘱描述", type = "")
    public String itemDesc;
    @Desc(label = "开医嘱医生", type = "")
    public String orderDoctor;
    @Desc(label = "开医嘱日期", type = "")
    public String orderDate;
    @Desc(label = "开医嘱时间", type = "")
    public String orderTime;
    @Desc(label = "停止医生", type = "")
    public String stopOrderDoctor;
    @Desc(label = "停止日期", type = "")
    public String stopOrderDate;
    @Desc(label = "停止时间", type = "")
    public String stopOrderTime;
    @Desc(label = "医嘱状态", type = "")
    public String orderStatus;
    @Desc(label = "医嘱开始日期", type = "")
    public String ordStartDate;
    @Desc(label = "医嘱开始时间", type = "")
    public String ordStartTime;
    @Desc(label = "医嘱优先级", type = "")
    public String priority;
    @Desc(label = "单次剂量", type = "")
    public String doseQty;
    @Desc(label = "单位", type = "")
    public String doseUom;
    @Desc(label = "频次", type = "")
    public String frequency;
    @Desc(label = "用法", type = "")
    public String instruction;
    @Desc(label = "疗程", type = "")
    public String duration;
    @Desc(label = "数量", type = "")
    public String qty;
    @Desc(label = "单位", type = "")
    public String uom;
    @Desc(label = "接收科室", type = "")
    public String recLoc;
    @Desc(label = "备注", type = "")
    public String orderNote;
    @Desc(label = "医嘱类型图片标识", type = "")//
    public String icoFile;//医嘱类型图片标识,药品医嘱drug.png、普通医嘱orderdefault.png
    @Desc(label = "医嘱能否停止", type = "")//
    public String stopPerm;//,0/1,1显示停止按钮

    @Desc(label = "继续加载的标示，第一次加载不传", type = "")//
    public String conLoad;//,0/1,1显示停止按钮


    @Override
    public boolean equals(Object o) {
        OrderItem it = (OrderItem) o;
        return this.ordItemId.equals(it.ordItemId);
    }

    @Override
    public int hashCode() {
        return ordItemId.hashCode();
    }
}
