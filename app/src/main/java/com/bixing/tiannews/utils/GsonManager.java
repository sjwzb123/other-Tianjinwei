package com.bixing.tiannews.utils;

import com.bixing.tiannews.bean.BaseBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by sjw on 2017/9/23.
 */

public class GsonManager {

    public static <T> T fromJsonStr(String json, Class<T> c) {
        Gson gson = new Gson();
        return gson.fromJson(json, c);
    }

    public static String toJson(BaseBean bean) {
        Gson gson = new Gson();
        return gson.toJson(bean);
    }
}
