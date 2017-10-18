package com.bixing.tiannews.bean;

/**
 * Created by sjw on 2017/9/25.
 */

public class UploadFileResponseBean extends BaseResponsBean {
    private UploadFileBean data;

    public UploadFileBean getData() {
        return data;
    }

    public void setData(UploadFileBean data) {
        this.data = data;
    }
}
