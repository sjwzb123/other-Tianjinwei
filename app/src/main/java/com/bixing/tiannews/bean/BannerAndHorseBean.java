package com.bixing.tiannews.bean;

import java.util.List;

/**
 * Created by sjw on 2017/11/14.
 */

public class BannerAndHorseBean extends NewsBean{
    private List<BannerBean> banner;
    private List<horseBean> horse;

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<horseBean> getHorse() {
        return horse;
    }

    public void setHorse(List<horseBean> horse) {
        this.horse = horse;
    }
}
