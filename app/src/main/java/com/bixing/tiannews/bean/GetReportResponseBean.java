package com.bixing.tiannews.bean;

import java.util.List;

/**
 * Created by sjw on 2017/9/30.
 */

public class GetReportResponseBean extends BaseResponsBean {
    private List<ReportBean> data;

    public List<ReportBean> getData() {
        return data;
    }

    public void setData(List<ReportBean> data) {
        this.data = data;
    }
}
