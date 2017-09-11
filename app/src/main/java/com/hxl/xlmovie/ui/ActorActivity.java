package com.hxl.xlmovie.ui;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.hxl.xlmovie.BaseActivity;
import com.hxl.xlmovie.R;
import com.hxl.xlmovie.adapter.ActorAdapter;
import com.hxl.xlmovie.constant.UrlConstant;
import com.hxl.xlmovie.entity.ActorBean;
import com.hxl.xlmovie.entity.RspDto;
import com.hxl.xlmovie.http.HttpUtil;
import com.hxl.xlmovie.http.MCallBack;
import com.hxl.xlmovie.util.ToastUtil;
import com.hxl.xlmovie.view.ProgressWebView;
import com.wang.avi.AVLoadingIndicatorView;

import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActorActivity extends BaseActivity {

    @Bind(R.id.aviLoading)
    AVLoadingIndicatorView aviLoading;
    @Bind(R.id.layout_main)
    LinearLayout layoutMain;
    @Bind(R.id.webView)
    ProgressWebView webView;
    private String id;
    private ActorBean actor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor);
        setWindowStatusBarColor(this, R.color.detailColor);
        ButterKnife.bind(this);
        aviLoading.show();
        id = getIntent().getExtras().getString("id");
        initWebView();
        request();
    }


    private void request() {
        HttpUtil.postParams(UrlConstant.actor_url + id, null, new MCallBack() {
            @Override
            public void onResponse(final RspDto.BaseRsp rsp, final String data) {
                ActorActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        aviLoading.hide();
                        aviLoading.setVisibility(View.GONE);
                        layoutMain.setVisibility(View.VISIBLE);
                        if (rsp.code == 1) {
                            actor = new Gson().fromJson(data, ActorBean.class);

                            webView.loadUrl(actor.getMobile_url());


                        } else {
                            ToastUtil.show(ActorActivity.this, rsp.msg);
                        }
                    }
                });
            }
        });
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
