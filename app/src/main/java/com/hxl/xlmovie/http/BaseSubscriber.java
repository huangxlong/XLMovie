package com.hxl.xlmovie.http;

import com.hxl.xlmovie.base.App;
import com.hxl.xlmovie.util.NetUtil;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by Administrator on 2018/1/24 0024.
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        String msg;
        if (!NetUtil.isConnected(App.getApplication())) {
            msg = "网络不可用，请检查网络设置";
        } else if (e instanceof SocketTimeoutException) {
            msg = "网络不可用，请检查网络设置";
        } else if (e instanceof ConnectException) {
            msg = "网络不可用，请检查网络设置";
        } else {
            msg = e.getMessage().toString();
        }

        onFailure(msg);
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }


    public abstract void onSuccess(T t);

    public abstract void onFailure(String msg);

}
