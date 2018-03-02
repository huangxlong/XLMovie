package com.hxl.xlmovie.base.contract.douban;

import com.hxl.xlmovie.base.BasePresenter;
import com.hxl.xlmovie.base.IBaseView;
import com.hxl.xlmovie.entity.TheaterBean;

/**
 * Created by Administrator on 2018/1/24 0024.
 */

public interface MovieListContract {

    interface View extends IBaseView {

        void handleSuccess(TheaterBean theater);

        void handleFailure(String msg);



        String getCity();

        int getCount();

        int getStar();

    }

    interface Presenter extends BasePresenter<View> {
        void getMovie();
    }


}
