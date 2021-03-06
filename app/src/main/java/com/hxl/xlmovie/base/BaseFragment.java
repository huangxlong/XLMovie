package com.hxl.xlmovie.base;

import android.os.Bundle;
import android.support.annotation.Nullable;


/**
 * Created by Administrator on 2018/1/24 0024.
 */

public abstract class BaseFragment<T extends BasePresenter> extends SimpleFragment implements IBaseView {

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
        mPresenter.attachView(this);
    }


    @Override
    public void onDestroyView() {
        if (mPresenter != null) mPresenter.detachView();
        super.onDestroyView();
    }

}
