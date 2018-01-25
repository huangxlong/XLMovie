package com.hxl.xlmovie.ui.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.hxl.xlmovie.R;
import com.hxl.xlmovie.base.App;
import com.hxl.xlmovie.base.SimpleActivity;
import com.hxl.xlmovie.entity.Address;
import com.hxl.xlmovie.mvp.mFragment;
import com.hxl.xlmovie.ui.fragment.MovieFragment;
import com.hxl.xlmovie.util.SPUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/22 0022.
 */

public class MainTabActivity extends SimpleActivity {
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private List<Fragment> mFragments = new ArrayList<>();
    private Fragment mContent;

    @Override
    protected int getLayout() {
        return R.layout.avtivity_maintab;
    }

    @Override
    protected void initView() {
        initFragments();
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .addItem(new BottomNavigationItem(R.drawable.ic_movies, "电影").setActiveColorResource(R.color.colorAccent))
                .addItem(new BottomNavigationItem(R.drawable.ic_movies, "电影").setActiveColorResource(R.color.colorPrimary))
                .setFirstSelectedPosition(0)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case 0:
                        switchContent(mContent, mFragments.get(0));
                        break;
                    case 1:
                        switchContent(mContent, mFragments.get(1));
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

        //定位
        App.getApplication().startLocation(MainTabActivity.this, new App.LocationListener() {
                    @Override
                    public void onLocationResult(Address.LocationInfo location) {
                        SPUtil.put(MainTabActivity.this, "city", location.city);
                        tvCity.setText(location.city);
                        switchContent(null, mFragments.get(0));
                    }
                }
        );
    }

    private void initFragments() {
        mFragments.add(MovieFragment.newInstance());
        mFragments.add(mFragment.newInstance());
    }


    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    /**
     * 切换Fragment
     *
     * @param from
     * @param to
     */
    public void switchContent(Fragment from, Fragment to) {
        if (mContent == null || mContent != to) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (mContent != null) {
                mContent.onPause();
            }
            if (to.isAdded()) {
                to.onResume();
            } else {
                transaction.add(R.id.container, to);
            }
            if (from != null) {
                transaction.hide(from);// 隐藏当前的fragment
            }
            transaction.show(to).commitAllowingStateLoss();// 显示下一个
            mContent = to;
        } else {
            mContent.onResume();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE:       //定位授权回调
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    App.getApplication().startLocation(MainTabActivity.this, new App.LocationListener() {
                        @Override
                        public void onLocationResult(Address.LocationInfo location) {
                            SPUtil.put(MainTabActivity.this, "city", location.city);

                            switchContent(null, mFragments.get(0));
                        }
                    });
                } else {
                    showToast("请开启定位权限");
                }
                break;
        }
    }
}
