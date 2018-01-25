package com.hxl.xlmovie.http;

import com.hxl.xlmovie.entity.ActorRsp;
import com.hxl.xlmovie.entity.Detail;
import com.hxl.xlmovie.entity.Theater;


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
    Observable<Theater> getTheater(@Query("apikey") String apikey,
                                   @Query("city") String city,
                                   @Query("start") int start,
                                   @Query("count") int count,
                                   @Query("client") String client,
                                   @Query("udid") String udid);

    //获取电影详情
    @GET("subject/{id}")
    Observable<Detail> getDetail(@Path("id") String id,
                                 @Query("apikey") String apikey,
                                 @Query("city") String city,
                                 @Query("client") String client,
                                 @Query("udid") String udid);

    //获取演员详情
    @GET("celebrity/{id}")
    Observable<ActorRsp> getActor(@Path("id") String id);

}