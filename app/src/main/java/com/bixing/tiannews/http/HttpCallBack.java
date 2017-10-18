package com.bixing.tiannews.http;

import com.bixing.tiannews.bean.BaseBean;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by sjw on 2017/9/23.
 */

public interface HttpCallBack {

    void onFailure(String errCode, String errMsg);

    void onSucc(String response);
}
