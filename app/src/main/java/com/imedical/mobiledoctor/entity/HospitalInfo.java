package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

/**
 * <HospitalInfo>
 * <hospitalId>医院Id<hospitalId>
 * <hospitalName>医院名称</hospitalName>
 * <hospitalCode>医院代码</hospitalCode>
 * <internet>外网服务器地址</internet>
 * <intranet>内网服务器地址</intranet>
 * </HospitalInfo>
 *
 * @author sszvip
 */
public class HospitalInfo implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public String hospitalId;
    public String hospitalName;
    public String hospitalCode;
    public String internet;
    public String intranet;

    public HospitalInfo() {
    }

}
