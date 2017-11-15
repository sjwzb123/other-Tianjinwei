package com.bixing.tiannews;

import com.bixing.tiannews.Base.BaseActivity;
import com.bixing.tiannews.utils.DebugLog;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by sjw on 2017/9/27.
 */

public class WebActivity extends BaseActivity {
    private WebView webView;
    private View shareView;
    private String mUrl;
    private String APP_ID = "wx28b986b749561a99";
    private IWXAPI api;
    // private int mTargetScene = SendMessageToWX.Req.WXSceneSession;
    private String newsTitle;

    @Override
    protected int getContviewId() {
        return R.layout.activity_web;
    }

    private LinearLayout llShare;
    private ImageView ivChat;
    private ImageView ivF;

    @Override
    protected void initView() {
        api = WXAPIFactory.createWXAPI(this, APP_ID);
        llShare = (LinearLayout)findViewById(R.id.ll_share);

        ivChat = (ImageView)findViewById(R.id.iv_weixin);
        ivF = (ImageView)findViewById(R.id.iv_weixin_m);
        ivChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareToWxinChat(SendMessageToWX.Req.WXSceneSession);
                llShare.setVisibility(View.GONE);
            }
        });
        ivF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareToWxinChat(SendMessageToWX.Req.WXSceneTimeline);
                llShare.setVisibility(View.GONE);
            }
        });
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        shareView = findViewById(R.id.btn_share);
        findViewById(R.id.iv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llShare.setVisibility(View.GONE);
            }
        });
        shareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // share();
                share();
            }
        });
        mUrl = getIntent().getStringExtra("url");
        newsTitle = getIntent().getStringExtra("title");
        boolean b = getIntent().getBooleanExtra("isShare", false);
        if (b) {
            shareView.setVisibility(View.VISIBLE);
        } else {
            // shareView.setVisibility(View.GONE);
        }
        DebugLog.d("url ", mUrl);
        webView = (WebView)findViewById(R.id.wb);
        webView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llShare.setVisibility(View.GONE);
            }
        });
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
        llShare.setVisibility(View.VISIBLE);

    }

    @Override
    protected void iniData() {

    }

    private void shareToWxinChat(int targetScene) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = mUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        newsTitle = TextUtils.isEmpty(newsTitle) ? "天津卫新闻" : newsTitle;
        msg.title = newsTitle;
        msg.description = newsTitle;

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        // Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        // bmp.recycle();
        msg.thumbData = WXUitls.bmpToByteArray(bmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();

        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = targetScene;
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
