package com.bixing.tiannews.bean;

import java.util.List;

/**
 * Created by sjw on 2017/9/24.
 */

public class NewsTypeResponsBean extends BaseResponsBean {
    private List<NewsTypeBean> data;

    public List<NewsTypeBean> getData() {
        return data;
    }

    public void setData(List<NewsTypeBean> data) {
        this.data = data;
    }
}
