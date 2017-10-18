package com.bixing.tiannews.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.bixing.tiannews.Base.BaseFragment;
import com.bixing.tiannews.R;

/**
 * Created by sjw on 2017/9/25.
 */

public class ReadNewsFragment extends BaseFragment {
    private WebView webView;
    private String newsUrl = "http://www.tjcaw.gov.cn/zt/detail-ifycwyxr8751711.shtml";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_read, null);
    }

    private void initView(View view) {
        webView = (WebView)view.findViewById(R.id.wb);
        webView.loadUrl(newsUrl);
        // 声明WebSettings子类
        WebSettings webSettings = webView.getSettings();

        // 如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);

        // 设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); // 将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        // 缩放操作
        webSettings.setSupportZoom(true); // 支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); // 设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); // 隐藏原生的缩放控件

        // 其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); // 关闭webview中缓存
        webSettings.setAllowFileAccess(true); // 设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); // 支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); // 支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");// 设置编码格式

    }
}
