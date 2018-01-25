package com.hxl.xlmovie.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxl.xlmovie.util.ToastUtil;
import com.hxl.xlmovie.view.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Administrator on 2017/9/18 0018.
 */

public abstract class SimpleFragment extends Fragment {
    protected View mView;
    protected Activity mContext;
    private Unbinder unbinder;
    protected boolean isInited = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isInited = true;
        initEventAndData();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void showToast(final String msg) {
        mContext = getActivity();
        if (isValidContext(mContext)) {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.show(mContext, msg);
                }
            });
        }
    }

    protected LoadingDialog mLoadingDialog;

    protected void showLoadingDialog() {
        mContext = getActivity();
        if (isValidContext(mContext)) {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mLoadingDialog == null) {
                        mLoadingDialog = new LoadingDialog(getActivity());
                    }

                    mLoadingDialog.setMessage("加载中...");
                    mLoadingDialog.show();
                }
            });
        }
    }

    protected void showLoadingDialog(final String text) {
        mContext = getActivity();
        if (isValidContext(mContext)) {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mLoadingDialog == null) {
                        mLoadingDialog = new LoadingDialog(getActivity());
                    }

                    mLoadingDialog.setMessage(text);
                    mLoadingDialog.show();
                }
            });
        }
    }

    protected void hideLoadingDialog() {
        mContext = getActivity();
        if (isValidContext(mContext)) {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mLoadingDialog != null) {
                        mLoadingDialog.dismiss();
                    }
                }
            });
        }
    }

    private boolean isValidContext(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return !(activity == null || activity.isDestroyed() || activity.isFinishing());
        } else {
            return !(activity == null || activity.isFinishing());
        }
    }


    protected abstract int getLayoutId();

    protected abstract void initEventAndData();
}
