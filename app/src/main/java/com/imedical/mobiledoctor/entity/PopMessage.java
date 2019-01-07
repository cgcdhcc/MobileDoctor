package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * 获取抗生素用药原因
 * <popId>消息Id</popId>
 * <popType>
 * 弹出消息类型；A/Alert(提示－继续执行)，C/Cancel(提示－取消执行)，D/Dialog（提示，确定－继续执行、取消－终止）
 * </popType>
 * <popContent>消息内容</popContent>
 * </PopMessage>
 *
 * @author sszvip
 */
public class PopMessage implements Serializable {
    public String popId;
    public String popType;
    public String popContent;
}
