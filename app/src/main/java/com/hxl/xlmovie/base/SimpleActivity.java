package com.hxl.xlmovie.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.hxl.xlmovie.R;
import com.hxl.xlmovie.util.ToastUtil;
import com.hxl.xlmovie.view.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public abstract class SimpleActivity extends AppCompatActivity {

    protected String TAG = "SimpleActivity";
    private Unbinder mUnBinder;
    public static final int PERMISSION_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
        setContentView(getLayout());
        mUnBinder = ButterKnife.bind(this);
        setWindowStatusBarColor(this, R.color.colorPrimaryDark);
        App.addActivity(this);
        initView();
    }

    public static void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));

                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    protected LoadingDialog mLoadingDialog;

    protected void showLoadingDialog() {
        if (isDestroy()) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mLoadingDialog == null) {
                    mLoadingDialog = new LoadingDialog(SimpleActivity.this);
                }

                mLoadingDialog.setMessage("加载中...");
                mLoadingDialog.show();
//                    DialogUtil.showLoading(mContext);
            }
        });
    }

    protected void showLoadingDialog(final String text) {
        if (isDestroy()) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mLoadingDialog == null) {
                    mLoadingDialog = new LoadingDialog(SimpleActivity.this);
                }

                mLoadingDialog.setMessage(text);
                mLoadingDialog.show();
//                    DialogUtil.showLoading(mContext, text);
            }
        });
    }


    protected void hideLoadingDialog() {
        if (isDestroy()) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mLoadingDialog != null) {
                    mLoadingDialog.dismiss();
                }
            }
        });
    }


    protected boolean isDestroy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (this.isFinishing() || this.isDestroyed()) {
                return true;
            }
        } else {
            if (this.isFinishing()) {
                return true;
            }
        }
        return false;
    }

    public void showToast(final String msg) {
        if (isDestroy()) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.show(SimpleActivity.this, msg + "");
            }
        });
    }


    @Override
    public void onBackPressed() {
        closeKeyboard();
        super.onBackPressed();
    }


    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        View view = getCurrentFocus();
        if (view != null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.removeActivity(this);
        mUnBinder.unbind();
    }

    protected abstract int getLayout();

    protected abstract void initView();
}
