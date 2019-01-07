package com.imedical.mobiledoctor.entity;

import java.io.Serializable;

public class BaseBeanMy implements Serializable {
    /**
     *
     */
    public boolean success;
    public String msg;
//	

    public BaseBeanMy() {
    }

    public BaseBeanMy(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
