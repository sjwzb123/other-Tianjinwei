package com.bixing.tiannews.bean;

import java.util.List;

/**
 * Created by sjw on 2017/9/30.
 */

public class ResponseServiceBean extends BaseResponsBean {
    /**
     * title
     * child
     * serviceId
     * title
     * icon
     */
    private List<ServiceBean> data;

    public List<ServiceBean> getData() {
        return data;
    }

    public void setData(List<ServiceBean> data) {
        this.data = data;
    }
}
