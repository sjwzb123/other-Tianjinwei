package com.bixing.tiannews.bean;

import java.util.List;

/**
 * Created by sjw on 2017/9/25.
 */

public class UserResponsBean extends BaseResponsBean {
    private UserBean data;
    private String token;

    public UserBean getData() {
        return data;
    }

    public void setData(UserBean data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
