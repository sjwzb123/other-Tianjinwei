package com.bixing.tiannews;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.bixing.tiannews.Base.BaseActivity;
import com.bixing.tiannews.utils.DebugLog;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by sjw on 2017/9/27.
 */

public class WebActivity extends BaseActivity {
    private WebView webView;
    private View shareView;
    private String mUrl;
    @Override
    protected int getContviewId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        shareView = findViewById(R.id.btn_share);
        shareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });
        mUrl = getIntent().getStringExtra("url");
        boolean b = getIntent().getBooleanExtra("isShare", false);
        if (b) {
            shareView.setVisibility(View.VISIBLE);
        } else {
            shareView.setVisibility(View.GONE);
        }
        DebugLog.d("url ", mUrl);
        webView = (WebView)findViewById(R.id.wb);
        webView.loadUrl(mUrl);
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

    private void share() {

        OnekeyShare oks = new OnekeyShare();
        // 关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字 2.5.9以后的版本不 调用此方法
        // oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("分享");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(mUrl);
        // text是分享文本，所有平台都需要这个字段
       // oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");// 确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(mUrl);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
       // oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(mUrl);

        // 启动分享GUI
        oks.show(this);

    }

    @Override
    protected void iniData() {

    }
}
