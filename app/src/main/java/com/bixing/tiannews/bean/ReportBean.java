package com.bixing.tiannews.bean;

import com.bixing.tiannews.utils.DebugLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sjw on 2017/9/30.
 */

public class ReportBean extends BaseBean {
    /**
     * reportId
     * title
     * addr
     * content
     * img
     * status
     * phone
     * createTime
     */
    private String reportId;
    private String title;
    private String addr;
    private String content;

    private String status;
    private String phone;
    private String createTime;
    private List<String> img;

    public List<String> getImg() {
        DebugLog.d("imgs ","size "+img.size());
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        // 状态 1、待处理 2、有效 3、无效
        if (status.equals("1")) {
            return "待处理";
        }
        if (status.equals("2")) {
            return "有效";
        }
        if (status.equals("3")) {
            return "无效";
        }

        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreateTime() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        long time = Long.valueOf(createTime + "000");
        Date date = new Date(time);
        return fmt.format(date);
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
