package com.hxl.xlmovie.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;


/**
 * Created by Administrator on 2018/1/24 0024.
 */

public abstract class BaseFragment<V, T extends BasePresenter<V>> extends SimpleFragment {

    protected T mPresenter;


    // 实例化presenter
    public abstract T initPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = initPresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.attachView((V) this);
    }


    @Override
    public void onDestroyView() {
        if (mPresenter != null) mPresenter.detachView();
        super.onDestroyView();
    }

}
