package com.bixing.tiannews.bean;

import java.util.List;

/**
 * Created by sjw on 2017/9/24.
 */

public class NewsResponsBean extends BaseResponsBean{
    private List<NewsBean> data;

    public List<NewsBean> getData() {
        return data;
    }

    public void setData(List<NewsBean> data) {
        this.data = data;
    }
}
