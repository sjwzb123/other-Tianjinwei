package com.bixing.tiannews.bean;

/**
 * Created by sjw on 2017/9/25.
 */

public class UserBean extends BaseBean{
    /**
     *   "nickName": "",
     "avatarUrl": "",
     "city": "",
     "country": "",
     "gender": "",
     "language": "",
     "province": "",
     "status": "1",
     "name": "",
     "phone": "13164232910",
     "company": "",
     "job": ""
     */

    private String nickName;
    private String avatarUrl;
    private String city;
    private String country;
    private String gender;
    private String language;
    private String province;
    private String status;
    private String name;
    private String phone;
    private String job;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
