package com.hxl.xlmovie.entity;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class RspDto {
    public static class BaseRsp {
        public boolean success = false;
        public int code = -100;
        public String msg = "数据异常，请重试~";
    }
}
