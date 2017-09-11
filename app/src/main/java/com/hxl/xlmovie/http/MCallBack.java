package com.hxl.xlmovie.http;

import com.hxl.xlmovie.App;
import com.hxl.xlmovie.util.LogUtils;
import com.hxl.xlmovie.entity.CSConfig;
import com.hxl.xlmovie.entity.RspDto;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public abstract class MCallBack implements Callback {

    @Override
    public void onFailure(Call call, IOException e) {
        e.printStackTrace();
        RspDto.BaseRsp rsp = new RspDto.BaseRsp();
        rsp.code = -100;
        if (e instanceof SocketTimeoutException) {
            rsp.msg = "连接超时,请重试！";////如果超时，则重新连接
        } else {
            rsp.msg = "服务器繁忙，请稍后重试!";
        }
        LogUtils.d("onResponse", rsp.msg);
        onResponse(rsp, "");

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        RspDto.BaseRsp rsp = new RspDto.BaseRsp();
        rsp.code = 1;
        String data = "";
        String rspStr = "";
        rspStr = response.body().string();

        LogUtils.d("onResponse", "data:" + rspStr);

//获取session的操作，session放在cookie头，且取出后含有“；”，取出后为下面的 s （也就是jsesseionid）
        Headers headers = response.headers();
        List<String> cookies = headers.values("x-auth-token");
        if (cookies != null && !cookies.isEmpty()) {
            String session = cookies.get(0);
            CSConfig.instance().setSession(App.getApplication(), session);
        }

        onResponse(rsp, rspStr);

    }

    public abstract void onResponse(RspDto.BaseRsp rsp, String data);
}
