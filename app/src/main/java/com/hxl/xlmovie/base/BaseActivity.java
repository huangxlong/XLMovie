package com.hxl.xlmovie.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2018/1/25 0025.
 */

public abstract class BaseActivity<T extends BasePresenter> extends SimpleActivity implements IBaseView {

    protected T mPresenter;

    // 实例化presenter
    public abstract T initPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = initPresenter();
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        mPresenter.attachView(this);
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        if (mPresenter != null) mPresenter.detachView();
        super.onDestroy();
    }

}
