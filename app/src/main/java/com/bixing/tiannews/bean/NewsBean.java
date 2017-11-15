package com.bixing.tiannews.bean;

import android.text.TextUtils;

import com.bixing.tiannews.utils.DebugLog;

import java.util.List;

/**
 * Created by sjw on 2017/9/23.
 */

public class NewsBean extends BaseBean {

    /**
     * newsId
     * title
     * typeId
     * img
     * video
     * content
     * number
     * like
     * comment
     */
    private String newsId;
    private String title;
    private String typeId;
    private List<String> img;
    private String content;
    private String number;
    private String like;
    private String comment;
    private String blockType;
    private String detailUrl;
    private String label;
    private String time;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public int getBlockType() {

        return Integer.valueOf(blockType)-1;
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }



    public String getVideo() {
        return video;
    }


    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    private String video;

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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getComment() {
        return comment+" 评";
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
