package com.bixing.tiannews.bean;

/**
 * Created by sjw on 2017/11/14.
 */

public class horseBean {
    /**
     * "newsId": "93",
     "title": "最高检：公布10月大要案信息，15名官员涉嫌受贿犯罪"
     */
    private String newsId;
    private String title;
    private String detailUrl;

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
