package com.hxl.xlmovie.ui.douban.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hxl.xlmovie.R;
import com.hxl.xlmovie.base.BaseFragment;
import com.hxl.xlmovie.base.contract.douban.MovieListContract;
import com.hxl.xlmovie.entity.TheaterBean;
import com.hxl.xlmovie.presenter.douban.MovieListPresenter;
import com.hxl.xlmovie.ui.douban.activity.DetailActivity;
import com.hxl.xlmovie.ui.douban.activity.MainTabActivity;
import com.hxl.xlmovie.ui.douban.adapter.TheaterAdapter;
import com.hxl.xlmovie.view.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/24 0024.
 *
 */

public class MovieFragment extends BaseFragment<MovieListPresenter> implements MovieListContract.View {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.banner)
    Banner banner;
    private TheaterBean theaters;
    private TheaterAdapter theaterAdapter;
    private List<TheaterBean.SubjectsBean> subList = new ArrayList();
    private ArrayList<String> images = new ArrayList<>();

    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie;
    }

    @Override
    public void initEventAndData() {
        ((MainTabActivity)(getActivity())).setTitle("热映电影");
        refreshLayout.setRefreshing(true);
        initBanner();
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getMovie();
            }
        });
        initAdapter();
        mPresenter.getMovie();
    }

    private void initBanner() {
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImageLoader(new GlideImageLoader())
                .isAutoPlay(true)
                .setDelayTime(1500)
                .setImages(images)
                .start();
    }

    private void initAdapter() {
        theaterAdapter = new TheaterAdapter(subList);
        theaterAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);  //开启动画
        theaterAdapter.setNotDoAnimationCount(6);
        theaterAdapter.isFirstOnly(false); //重复执行动画
        recycler.setLayoutManager(new LinearLayoutManager(mContext));
        recycler.setAdapter(theaterAdapter);
        ((DefaultItemAnimator) recycler.getItemAnimator()).setSupportsChangeAnimations(false);
        theaterAdapter.setOnItemClickListener(new TheaterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TheaterBean.SubjectsBean subjectsBean = theaters.subjects.get(position);
                Intent intent = new Intent(mContext, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("subject", subjectsBean);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void handleSuccess(TheaterBean theater) {
        refreshLayout.setRefreshing(false);
        theaters = theater;
        subList.clear();
        subList.addAll(theater.subjects);
        theaterAdapter.notifyDataSetChanged();
        recycler.smoothScrollToPosition(0);

        images.clear();
        for (int i = 0; i < 3; i++) {
            images.add(theater.subjects.get(i).images.medium);
        }
        banner.setImages(images).start();
    }

    @Override
    public void handleFailure(String msg) {
        refreshLayout.setRefreshing(false);
        showToast(msg);
    }


    @Override
    public String getCity() {
        return "成都";
    }

    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public int getStar() {
        return 0;
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
        refreshLayout.setRefreshing(false);
    }


    @Override
    public MovieListPresenter initPresenter() {
        return new MovieListPresenter(this);
    }
}
