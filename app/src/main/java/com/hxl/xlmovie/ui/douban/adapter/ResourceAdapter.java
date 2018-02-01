package com.hxl.xlmovie.ui.douban.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hxl.xlmovie.R;
import com.hxl.xlmovie.entity.DetailBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/29 0029.
 */

public class ResourceAdapter extends BaseQuickAdapter<DetailBean.PhotosBean, BaseViewHolder> {
    public ResourceAdapter(int layoutResId, @Nullable List<DetailBean.PhotosBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DetailBean.PhotosBean item) {
        int position = helper.getLayoutPosition();
        if(position==0){
            //显示预告片

        }else {

        }
        String url = item.image;

        Glide.with(mContext).load(url).into((ImageView)helper.getView(R.id.iv_resource));

    }
}
