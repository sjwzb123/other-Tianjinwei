package com.bixing.tiannews.bean;

import java.io.Serializable;

/**
 * Created by sjw on 2017/9/18.
 */

public class BaseResponsBean implements Serializable{
    private String code;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public boolean isSucc(){
        return getCode().equals("000000");
    }
}
