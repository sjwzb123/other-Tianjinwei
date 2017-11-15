package com.bixing.tiannews.bean;

import java.util.List;

/**
 * Created by sjw on 2017/11/15.
 */

public class CollectResponseBean extends BaseResponsBean{

   private List<CollectBean> data;

    public List<CollectBean> getData() {
        return data;
    }

    public void setData(List<CollectBean> data) {
        this.data = data;
    }
}
