package com.hxl.xlmovie.mvp;

import com.hxl.xlmovie.base.BasePresenter;
import com.hxl.xlmovie.base.contract.MovieContract;
import com.hxl.xlmovie.entity.Theater;
import com.hxl.xlmovie.http.BaseSubscriber;
import com.hxl.xlmovie.http.RetrofitFactory;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/1/24 0024.
 */

public class MoviePresenter extends BasePresenter<MovieContract.View> implements MovieContract.Presenter {

    private MovieContract.View movieView;

    public MoviePresenter(MovieContract.View view){
        movieView =view;
    }

    @Override
    public void getMovie() {
        RetrofitFactory.getMovieService()
                .getTheater(movieView.getApikey(), movieView.getCity(), movieView.getStar(), movieView.getCount(), movieView.getClient(), movieView.getUdid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Theater>() {
                    @Override
                    public void onSuccess(Theater theater) {
                        mView.handleSuccess(theater);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mView.handleFailure(msg);
                    }
                });
    }


}
