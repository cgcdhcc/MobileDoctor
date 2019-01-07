package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * <OrderSuscept>
 * <susceptId>药敏Id</susceptId>
 * <seqNo>序号</seqNo>
 * <bacterial>细菌名</bacterial>
 * <antName>抗生素名</antName>
 * <resistance>耐药机制</resistance>
 * <labName>检验项目</labName>
 * <labSpec>标本</labSpec>
 * <reportDate>报告日期</reportDate>
 * </OrderSuscept>
 *
 * @author sszvip
 */
public class OrderSuscept implements Serializable {
    private static final long serialVersionUID = 1L;

    public OrderSuscept() {
    }

    public String susceptId;
    public String seqNo;
    public String bacterial;
    public String antName;
    public String resistance;
    public String labName;
    public String labSpec;
    public String reportDate;
}
