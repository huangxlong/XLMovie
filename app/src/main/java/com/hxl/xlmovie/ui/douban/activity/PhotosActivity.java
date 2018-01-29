package com.hxl.xlmovie.ui.douban.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.hxl.xlmovie.R;
import com.hxl.xlmovie.base.SimpleActivity;
import com.hxl.xlmovie.entity.DetailBean;
import com.hxl.xlmovie.ui.douban.fragment.PhotosViewFragment;
import com.hxl.xlmovie.view.MyViewPager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018/1/25 0025.
 */

public class PhotosActivity extends AppCompatActivity {

    private DetailBean detail;
    private List<String> urlList = new ArrayList<>();
    MyViewPager viewPager;

    TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        viewPager = (MyViewPager) findViewById(R.id.viewPager);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();
    }


    protected void initView() {
        detail = (DetailBean) getIntent().getExtras().getSerializable("urls");
        urlList.add(detail.images.large);
        for (DetailBean.PhotosBean url : detail.photos) {
            urlList.add(url.image);
        }

        viewPager.setAdapter(new mPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tvTitle.setText("查看图片(" + (position + 1) + "/" + urlList.size() + ")");
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private class mPagerAdapter extends FragmentStatePagerAdapter {

        public mPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PhotosViewFragment.newInstance(urlList.get(position));
        }

        @Override
        public int getCount() {
            return urlList.size();
        }
    }
}
