package com.hxl.xlmovie.base.contract.douban;

import com.hxl.xlmovie.base.BasePresenter;
import com.hxl.xlmovie.base.IBaseView;
import com.hxl.xlmovie.entity.DetailBean;

/**
 * Created by Administrator on 2018/1/25 0025.
 */

public interface MovieDetailContratct {

    interface View extends IBaseView {
        String getId();


        String getCity();


        void handleSuccess(DetailBean detail);

        void handleFailure(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        void getDetail();
    }
}
