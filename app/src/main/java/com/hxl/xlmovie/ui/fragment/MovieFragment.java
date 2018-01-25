package com.hxl.xlmovie.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxl.xlmovie.R;
import com.hxl.xlmovie.http.BaseSubscriber;
import com.hxl.xlmovie.ui.activity.DetailActivity;
import com.hxl.xlmovie.ui.activity.MainTabActivity;
import com.hxl.xlmovie.util.SPUtil;
import com.hxl.xlmovie.view.RecyclerViewDivider;
import com.hxl.xlmovie.ui.adapter.TheaterAdapter;
import com.hxl.xlmovie.base.SimpleFragment;
import com.hxl.xlmovie.entity.Theater;
import com.hxl.xlmovie.http.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/9/18 0018.
 */

public class MovieFragment extends SimpleFragment {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    private Theater theaters;
    private TheaterAdapter theaterAdapter;
    private List<Theater.SubjectsBean> subList = new ArrayList();
    private View rootView;
    private String city;


    public static MovieFragment newInstance() {
        return new MovieFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movie;
    }



    //
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        initView();
//    }
    @Override
    protected void initEventAndData() {
        city = (String) SPUtil.get(mContext, "city", "成都");
        ((MainTabActivity) (getActivity())).setTitle("电影");
        refreshLayout.setRefreshing(true);
        request();
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                request();
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
    }


    private void request() {
        RetrofitFactory.getMovieService()
                .getTheater("0b2bdeda43b5688921839c8ecb20399b", city, 0, 100, "", "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Theater>() {
                    @Override
                    public void onSuccess(Theater theater) {
                        refreshLayout.setRefreshing(false);
                        theaters = theater;
                        subList.clear();
                        subList.addAll(theater.subjects);
                        theaterAdapter.notifyDataSetChanged();
                        recycler.smoothScrollToPosition(0);
                    }

                    @Override
                    public void onFailure(String msg) {
                        refreshLayout.setRefreshing(false);
                        showToast(msg);
                    }
                });
    }
}
