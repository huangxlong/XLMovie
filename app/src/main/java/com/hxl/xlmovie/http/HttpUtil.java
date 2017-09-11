package com.hxl.xlmovie.http;

import com.hxl.xlmovie.App;
import com.hxl.xlmovie.util.LogUtils;
import com.hxl.xlmovie.util.NetUtil;
import com.hxl.xlmovie.entity.CSConfig;
import com.hxl.xlmovie.entity.RspDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/9/4 0004.
 */

public class HttpUtil {
    private OkHttpClient mClient;
    private static HttpUtil mInstance;

    private final static int CONNECT_TIMEOUT = 30;

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final MediaType TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType TYPE_FORM = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    private static HttpUtil instance() {
        if (mInstance == null) {
            synchronized (HttpUtil.class) {
                if (mInstance == null) {
                    mInstance = new HttpUtil();
                }
            }
        }
        return mInstance;
    }

    private OkHttpClient getHttp() {
        return mClient;
    }

    private HttpUtil() {
        mClient = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .cookieJar(new CookieJar() {
                    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url, cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                }).build();
    }


    public static void postJson(String url, String json, Callback callback) {
        postJson(url, TYPE_JSON, json, callback);

    }

    public static void postJson(String url, MediaType type, String json, Callback callback) {
        if (!NetUtil.isConnected(App.getApplication())) {   //检查网络连接

            return;
        }
    }

    public static void postParams(String url, Map<String, String> params, MCallBack callback) {
        if (!NetUtil.isConnected(App.getApplication())) {   //检查网络连接
            RspDto.BaseRsp dto = new RspDto.BaseRsp();
            dto.code = -200;
            dto.msg = "网络不可用，请检查网络设置";
            callback.onResponse(dto, "");
            return;
        }
        String content = "";
        if (params == null) {
            params = new HashMap<>();
        }
        Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            content += entry.getKey() + "=" + entry.getValue();
            if (it.hasNext())
                content += "&";
        }
        LogUtils.d("request", url + "\ncontent:" + content);
        RequestBody body = RequestBody.create(TYPE_FORM, content);
        Request request = new Request.Builder()
                .addHeader("x-auth-token", CSConfig.instance().getSession())
                .url(url)
                .post(body)
                .build();
        instance().getHttp().newCall(request).enqueue(callback);
    }

}
