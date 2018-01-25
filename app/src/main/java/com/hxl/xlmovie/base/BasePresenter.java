package com.hxl.xlmovie.base;

/**
 * Created by Administrator on 2018/1/24 0024.
 */

public abstract class BasePresenter<T> {

    public T mView;

    void attachView(T view) {
        mView = view;
    }

    void detachView() {
        mView = null;
    }
}
