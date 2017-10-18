package com.bixing.tiannews.bean;

import java.util.List;

/**
 * Created by sjw on 2017/9/23.
 */

public class NewsTypeBean extends BaseBean {

    /***
     * "id": "1",
     * "title": "公安",
     * "update": "1",
     * "pid": "0",
     * "level": "1",
     * "sort": "1",
     * "child": []
     */
    private String typeId;
    private String id;
    private String title;
    private String update;
    private String pid;
    private String level;
    private String sort;
    private List<NewsTypeBean> child;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public List<NewsTypeBean> getChild() {
        return child;
    }

    public void setChild(List<NewsTypeBean> child) {
        this.child = child;
    }
}
