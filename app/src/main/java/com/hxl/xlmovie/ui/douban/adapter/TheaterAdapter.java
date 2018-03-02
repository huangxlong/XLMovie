package com.hxl.xlmovie.ui.douban.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hxl.xlmovie.R;
import com.hxl.xlmovie.entity.TheaterBean;

import java.util.List;

/**
 * Created by Administrator
 * on 2018/3/2 星期五.
 */

public class TheaterAdapter extends BaseQuickAdapter<TheaterBean.SubjectsBean, BaseViewHolder> {


    public TheaterAdapter(@Nullable List<TheaterBean.SubjectsBean> data) {
        super(R.layout.item_theater, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TheaterBean.SubjectsBean item) {
        int position = helper.getLayoutPosition();
        TheaterBean.SubjectsBean sub = getData().get(position);
        String grade;
        float bar;
        if (sub.rating.average > 0) {
            grade = sub.rating.average + "";
            bar = Float.parseFloat(sub.rating.stars) / 10;
            helper.setGone(R.id.ratingBar, true);
        } else {
            grade = "暂无评分";
            bar = 0;
            helper.setGone(R.id.ratingBar, false);
        }
        if (sub.casts.size() > 0) {
            String actor = "";
            for (int i = 0; i < sub.casts.size(); i++) {
                if (i == sub.casts.size() - 1) {
                    actor = actor + sub.casts.get(i).name;
                } else {
                    actor += sub.casts.get(i).name + " / ";
                }
            }
            helper.setText(R.id.tv_actor, "主演：" + actor)
                    .setGone(R.id.tv_actor, true);
        } else {
            helper.setGone(R.id.tv_actor, false);
        }
        helper.setText(R.id.tv_grade, grade)
                .setText(R.id.tv_title, sub.title)
                .setRating(R.id.ratingBar, bar)
                .setText(R.id.tv_director, "导演：" + sub.directors.get(0).name)
                .setText(R.id.tv_watcher, sub.collect_count + "人看过");
        Glide.with(mContext).load(sub.images.small).centerCrop().into((ImageView) helper.getView(R.id.iv_poster));
    }


}
