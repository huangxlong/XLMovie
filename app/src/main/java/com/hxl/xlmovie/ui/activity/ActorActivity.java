package com.hxl.xlmovie.ui.activity;

import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.hxl.xlmovie.base.SimpleActivity;
import com.hxl.xlmovie.R;
import com.hxl.xlmovie.view.ProgressWebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActorActivity extends SimpleActivity {
    @BindView(R.id.layout_main)
    LinearLayout layoutMain;
    @BindView(R.id.webView)
    ProgressWebView webView;
    private String url;

    @Override
    protected int getLayout() {
        return R.layout.activity_actor;
    }

    @Override
    protected void initView() {
        setWindowStatusBarColor(this, R.color.detailColor);
        url = getIntent().getExtras().getString("url");
        initWebView();
        webView.loadUrl(url);
    }

    private void initWebView() {
        webView.clearCache(true);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        settings.setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
//        settings.setSupportZoom(true);//是否可以缩放，默认true
//        settings.setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        settings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        settings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        settings.setAppCacheEnabled(false);//是否使用缓存
        settings.setDomStorageEnabled(true);//DOM Storage
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();// 接受所有网站的证书
            }
        });
    }


    @OnClick({R.id.layout_back})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                onBackPressed();
                break;
        }
    }


}
