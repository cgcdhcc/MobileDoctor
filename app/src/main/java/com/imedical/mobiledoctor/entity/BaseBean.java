package com.imedical.mobiledoctor.entity;

import com.imedical.mobiledoctor.util.Desc;

import java.io.Serializable;
import java.util.List;

public class BaseBean<T> implements Serializable {
    /**
     *
     */
    private String ResultCode;
    private String ResultDesc;
    private String ConLoad;

    @Desc(label = "", type = Desc.TYPE_LIST)
    private List<T> ResultList;

    public BaseBean() {
    }

    public List<T> getResultList() {
        return ResultList;
    }

    public String getResultCode() {
        return ResultCode;
    }

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    public String getResultDesc() {
        return ResultDesc;
    }

    public void setResultDesc(String resultDesc) {
        ResultDesc = resultDesc;
    }
}
