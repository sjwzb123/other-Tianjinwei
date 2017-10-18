package com.bixing.tiannews.bean;

/**
 * Created by sjw on 2017/9/25.
 */

public class SMSCodeResponseBean extends BaseResponsBean{

    private SMSCode data;

    public SMSCode getData() {
        return data;
    }

    public void setData(SMSCode data) {
        this.data = data;
    }

    class SMSCode{
        String smscode;

        public String getSmscode() {
            return smscode;
        }

        public void setSmscode(String smscode) {
            this.smscode = smscode;
        }
    }
}
