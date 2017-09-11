package com.hxl.xlmovie;

import com.hxl.xlmovie.entity.Theater;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/9/4 0004.
 */

public interface MovieService {
    @GET()
    Call<Theater> getTheater(@Path("apikey") String apikey,
                             @Path("city") String city,
                             @Path("start") int start,
                             @Path("count") int count,
                             @Path("client") String client,
                             @Path("udid") String udid);
}
