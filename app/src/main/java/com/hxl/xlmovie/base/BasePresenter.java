package com.hxl.xlmovie.base;

/**
 * Created by Administrator on 2018/1/24 0024.
 */

public interface  BasePresenter<T extends IBaseView> {


    void attachView(T view);

    void detachView();

}
