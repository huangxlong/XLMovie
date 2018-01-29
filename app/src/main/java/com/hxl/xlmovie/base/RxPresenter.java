package com.hxl.xlmovie.base;

/**
 * Created by Administrator on 2018/1/25 0025.
 */

public class RxPresenter<T extends IBaseView> implements BasePresenter<T> {
    public T mView;

    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
