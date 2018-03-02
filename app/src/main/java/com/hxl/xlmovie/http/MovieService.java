package com.hxl.xlmovie.http;

import com.hxl.xlmovie.entity.ActorBean;
import com.hxl.xlmovie.entity.DetailBean;
import com.hxl.xlmovie.entity.TheaterBean;


import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/9/4 0004.
 */

public interface MovieService {

    //获取上映电影列表
    @GET("in_theaters")
    Observable<TheaterBean> getTheater(@Query("city") String city,
                                       @Query("start") int start,
                                       @Query("count") int count);

    //获取电影详情
    @GET("subject/{id}")
    Observable<DetailBean> getDetail(@Path("id") String id,
                                     @Query("city") String city);

    //获取演员详情
    @GET("celebrity/{id}")
    Observable<ActorBean> getActor(@Path("id") String id);

}
