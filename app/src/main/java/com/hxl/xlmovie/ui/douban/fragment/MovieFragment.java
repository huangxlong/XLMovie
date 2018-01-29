package com.hxl.xlmovie.ui.douban.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hxl.xlmovie.R;
import com.hxl.xlmovie.base.BaseFragment;
import com.hxl.xlmovie.base.contract.douban.MovieListContract;
import com.hxl.xlmovie.entity.TheaterBean;
import com.hxl.xlmovie.presenter.douban.MovieListPresenter;
import com.hxl.xlmovie.ui.douban.activity.DetailActivity;
import com.hxl.xlmovie.ui.douban.adapter.TheaterAdapter;
import com.hxl.xlmovie.view.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/24 0024.
 */

public class MovieFragment extends BaseFragment<MovieListPresenter> implements MovieListContract.View {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    private TheaterBean theaters;
    private TheaterAdapter theaterAdapter;
    private List<TheaterBean.SubjectsBean> subList = new ArrayList();

    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie;
    }

    @Override
    public void initEventAndData() {
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getMovie();
            }
        });
        theaterAdapter = new TheaterAdapter(mContext, subList);
        recycler.setLayoutManager(new LinearLayoutManager(mContext));
        recycler.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(mContext, R.color.lineColor)));
        recycler.setAdapter(theaterAdapter);

        ((DefaultItemAnimator) recycler.getItemAnimator()).setSupportsChangeAnimations(false);

        theaterAdapter.setOnItemClickListener(new TheaterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TheaterBean.SubjectsBean subjectsBean = theaters.subjects.get(position);
                Intent intent = new Intent(mContext, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("subject", subjectsBean);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mPresenter.getMovie();
    }

    @Override
    public void handleSuccess(TheaterBean theater) {
        refreshLayout.setRefreshing(false);
        theaters = theater;
        subList.clear();
        subList.addAll(theater.subjects);
        theaterAdapter.notifyDataSetChanged();
        recycler.smoothScrollToPosition(0);
    }

    @Override
    public void handleFailure(String msg) {
        refreshLayout.setRefreshing(false);
        showToast(msg);
    }

    @Override
    public String getApikey() {
        return "0b2bdeda43b5688921839c8ecb20399b";
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
    public String getUdid() {
        return "";
    }

    @Override
    public String getClient() {
        return "";
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


    @Override
    public MovieListPresenter initPresenter() {
        return new MovieListPresenter(this);
    }
}
