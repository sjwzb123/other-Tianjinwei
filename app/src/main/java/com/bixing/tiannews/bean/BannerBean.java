package com.bixing.tiannews.bean;

/**
 * Created by sjw on 2017/11/14.
 */

public class BannerBean {
    /**
     * "newsId": "96",
     "img": "https: //app.tjcaw.gov.cn/u/img/2017-11-06/5a0008527adc3.jpg",
     "title": "专家解读：未来五年反腐工作这样做"
     detailUrl":"https://app.tjcaw.gov.cn/news/96
     */
    private String newsId;
    private String img;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
