package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * <Request>
 * <phoneNo>手机号码</phoneNo>
 * <terminalId>终端编号</terminalId>
 * <terminalType>终端类型/备用</terminalType>
 * <hospitalId>医院Id</hospitalId>
 * <patientCard>患者卡号</patientCard>
 * <patientId>患者Id</patientId>
 * <admId>就诊Id</admId>
 * <ordLabId>检验Id</ordLabId>
 * <ordItemId>医嘱Id</ordItemId>
 * </Request>
 *
 * @author sszvip
 * <p>
 * 此类为接口入参数，不要随意增删属性
 */
public class ParamUser implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String patientCard;
    private String cardType;
    private String phoneNo;
    private String passWord;
    private String terminalType;
    private String terminalId;
    private String hospitalId;
    private String idNo;
    private String requestAction;
    private String newPassWord;
    private String departmentCode;
    private String schDate;

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public String getRequestAction() {
        return requestAction;
    }

    public void setRequestAction(String requestAction) {
        this.requestAction = requestAction;
    }

    public String getNewPassWord() {
        return newPassWord;
    }

    public void setNewPassWord(String newPassWord) {
        this.newPassWord = newPassWord;
    }

    public String getAction() {
        return requestAction;
    }

    public void setAction(String action) {
        this.requestAction = action;
    }

    public ParamUser() {
    }

    public String getPatientCard() {
        return patientCard;
    }

    public void setPatientCard(String patientCard) {
        this.patientCard = patientCard;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getSchDate() {
        return schDate;
    }

    public void setSchDate(String schDate) {
        this.schDate = schDate;
    }

}
