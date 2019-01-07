package com.imedical.mobiledoctor.entity;

/**
 * <DiagStatus>
 * <diagStatId>诊断状态Id</diagStatId>
 * <diagStatCode>诊断状态代码</diagStatCode>
 * <diagStatDesc>诊断状态描述</diagStatDesc>
 * </DiagStatus>
 *
 * @author sszvip
 */
public class DiagStatus {
    public DiagStatus() {
    }

    public String diagStatId;
    public String diagStatCode;
    public String diagStatDesc;


    @Override
    public String toString() {
        return diagStatDesc;
    }

}
