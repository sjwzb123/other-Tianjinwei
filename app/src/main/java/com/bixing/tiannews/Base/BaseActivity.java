package com.bixing.tiannews.Base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.bixing.tiannews.utils.SpfManger;

/**
 * Created by sjw on 2017/9/18.
 */

public abstract class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContviewId());
        initView();
        iniData();
    }

    protected abstract int getContviewId();

    protected abstract void initView();

    protected abstract void iniData();

    public boolean isLogin() {
        String token = SpfManger.getToken(this);
        return !TextUtils.isEmpty(token);
    }

}
