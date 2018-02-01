package com.hxl.xlmovie.http;

import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;
import com.hxl.xlmovie.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class RetrofitFactory {
    private static final String MOVIE_URL = "http://api.douban.com/v2/movie/";
    private static final String TAG = "XLMovie";
    private final static int CONNECT_TIMEOUT = 30;

    private static OkHttpClient okHttpClient;
    private static MovieService movieService;

    private static OkHttpClient getHttp() {
        if (okHttpClient == null) {
            //打印请求日志
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.d(TAG, message);
                }
            });
            if (BuildConfig.DEBUG)
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            else httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(httpLoggingInterceptor)
                    .build();
        }
        return okHttpClient;
    }

    public static synchronized MovieService getMovieService() {
        if (movieService == null) {
            movieService = new Retrofit.Builder().baseUrl(MOVIE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .client(getHttp())
                    .build()
                    .create(MovieService.class);
        }
        return movieService;
    }

}
