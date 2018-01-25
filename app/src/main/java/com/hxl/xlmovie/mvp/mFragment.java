package com.hxl.xlmovie.mvp;

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
import com.hxl.xlmovie.base.BasePresenter;
import com.hxl.xlmovie.base.contract.MovieContract;
import com.hxl.xlmovie.entity.Theater;
import com.hxl.xlmovie.ui.activity.DetailActivity;
import com.hxl.xlmovie.ui.adapter.TheaterAdapter;
import com.hxl.xlmovie.view.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/24 0024.
 */

public class mFragment extends BaseFragment<MovieContract.View, MoviePresenter> implements MovieContract.View {
    private View rootView;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    private Theater theaters;
    private TheaterAdapter theaterAdapter;
    private List<Theater.SubjectsBean> subList = new ArrayList();

    public static mFragment newInstance() {
        return new mFragment();
    }

//    @Override
//    protected void initInject() {
//
//    }


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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(mContext, R.color.lineColor)));
        recycler.setAdapter(theaterAdapter);

        ((DefaultItemAnimator) recycler.getItemAnimator()).setSupportsChangeAnimations(false);

        theaterAdapter.setOnItemClickListener(new TheaterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Theater.SubjectsBean subjectsBean = theaters.subjects.get(position);
                Intent intent = new Intent(mContext, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("subject", subjectsBean);
                intent.putExtras(bundle);
                startActivity(intent);
//                getDetail(subjectsBean);
            }
        });

        mPresenter.getMovie();
    }


    @Override
    public void handleSuccess(Theater theater) {
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
    public MoviePresenter initPresenter() {
        return new MoviePresenter(this);
    }
}
