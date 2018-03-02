package com.hxl.xlmovie.presenter.douban;

import com.hxl.xlmovie.base.RxPresenter;
import com.hxl.xlmovie.base.contract.douban.MovieListContract;
import com.hxl.xlmovie.entity.TheaterBean;
import com.hxl.xlmovie.http.BaseSubscriber;
import com.hxl.xlmovie.http.RetrofitFactory;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/1/24 0024.
 */

public class MovieListPresenter extends RxPresenter<MovieListContract.View> implements MovieListContract.Presenter {

    private MovieListContract.View movieView;

    public MovieListPresenter(MovieListContract.View view){
        movieView =view;
    }

    @Override
    public void getMovie() {
        RetrofitFactory.getMovieService()
                .getTheater( movieView.getCity(), movieView.getStar(), movieView.getCount())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<TheaterBean>() {
                    @Override
                    public void onSuccess(TheaterBean theater) {
                        mView.handleSuccess(theater);
                        mView.hideLoading();
                    }

                    @Override
                    public void onFailure(String msg) {
                        mView.handleFailure(msg);
                        mView.hideLoading();
                    }
                });
    }


}
