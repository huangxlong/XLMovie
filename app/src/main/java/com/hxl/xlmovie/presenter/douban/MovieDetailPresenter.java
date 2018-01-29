package com.hxl.xlmovie.presenter.douban;

import com.hxl.xlmovie.base.RxPresenter;
import com.hxl.xlmovie.base.contract.douban.MovieDetailContratct;
import com.hxl.xlmovie.entity.DetailBean;
import com.hxl.xlmovie.http.BaseSubscriber;
import com.hxl.xlmovie.http.RetrofitFactory;
import com.hxl.xlmovie.view.LoadingView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/1/25 0025.
 */

public class MovieDetailPresenter extends RxPresenter<MovieDetailContratct.View> implements MovieDetailContratct.Presenter {

    private MovieDetailContratct.View mView;

    public MovieDetailPresenter(MovieDetailContratct.View view) {
        mView = view;
    }


    @Override
    public void getDetail() {
        RetrofitFactory.getMovieService()
                .getDetail(mView.getId(), mView.getApiKey(), mView.getCity(), mView.getClient(), mView.getUdid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<DetailBean>() {
                    @Override
                    public void onSuccess(DetailBean detail) {
                        mView.handleSuccess(detail);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mView.handleFailure(msg);
                    }
                });
    }
}
