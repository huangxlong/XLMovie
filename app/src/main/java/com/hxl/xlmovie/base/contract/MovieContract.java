package com.hxl.xlmovie.base.contract;

import android.view.View;

import com.hxl.xlmovie.base.BasePresenter;
import com.hxl.xlmovie.base.IBaseView;
import com.hxl.xlmovie.entity.Theater;

/**
 * Created by Administrator on 2018/1/24 0024.
 */

public interface MovieContract {

    interface View extends IBaseView {

        void handleSuccess(Theater theater);

        void handleFailure(String msg);


        String getApikey();

        String getCity();

        int getCount();

        int getStar();

        String getUdid();

        String getClient();
    }

    interface Presenter  {
        void getMovie();
    }


}
