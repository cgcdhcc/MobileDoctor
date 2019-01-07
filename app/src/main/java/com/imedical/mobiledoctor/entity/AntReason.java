package com.imedical.mobiledoctor.entity;


/**
 * 获取抗生素用药原因
 * <AntReason>
 * <antibId>原因Id</antibId>
 * <antibDesc>原因描述</antibDesc>
 * </AntReason>
 * ========================
 * 获取使用目的－使用目的
 * <AntReason>
 * <reasonId>使用目的Id</reasonId>
 * <reasonCode>使用目的代码</reasonCode>
 * <reasonDesc>使用目的描述</reasonDesc>
 * </AntReason>
 *
 * @author sszvip
 */
public class AntReason {
    //========== 抗生素
    public String reasonId;
    public String reasonCode;
    public String reasonDesc;

    @Override
    public String toString() {
        return reasonDesc;
    }

    //==========医嘱录入
    public String antibId;
    public String antibDesc;


}
