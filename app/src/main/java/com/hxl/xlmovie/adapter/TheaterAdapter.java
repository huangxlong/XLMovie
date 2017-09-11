package com.hxl.xlmovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hxl.xlmovie.R;
import com.hxl.xlmovie.entity.Theater;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class TheaterAdapter extends RecyclerView.Adapter<TheaterAdapter.ViewHolder> {


    private Context mContext;
    private List<Theater.SubjectsBean> mTheater;
    private OnItemClickListener mOnItemClickListener = null;

    public TheaterAdapter(Context context, List<Theater.SubjectsBean> theater) {
        mContext = context;
        mTheater = theater;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_theater, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    //注意这里使用getTag方法获取position
                    mOnItemClickListener.onItemClick(v, (int) v.getTag());
                }
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);

        Theater.SubjectsBean sub = mTheater.get(position);
        holder.ivPoster.setImageURI(sub.getImages().getLarge());
        holder.tvTitle.setText(sub.getTitle());
        if (sub.getRating().getAverage() > 0) {
            holder.tvGrade.setText(sub.getRating().getAverage() + "");
            holder.ratingBar.setRating(Float.parseFloat(sub.getRating().getStars()) / 10);
            holder.ratingBar.setVisibility(View.VISIBLE);
        } else {
            holder.tvGrade.setText("暂无评分");
            holder.ratingBar.setVisibility(View.GONE);
        }


        holder.tvDirector.setText("导演：" + sub.getDirectors().get(0).getName());
        holder.tvWatcher.setText(sub.getCollect_count() + "人看过");
        if (sub.getCasts().size() > 0) {
            String actor = "";
            for (int i = 0; i < sub.getCasts().size(); i++) {
                if (i == sub.getCasts().size() - 1) {
                    actor = actor + sub.getCasts().get(i).getName();
                } else {
                    actor = actor + sub.getCasts().get(i).getName() + " / ";
                }

            }
            holder.tvActor.setText("主演：" + actor);
            holder.tvActor.setVisibility(View.VISIBLE);
        } else {
            holder.tvActor.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return mTheater.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_poster)
        SimpleDraweeView ivPoster;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_grade)
        TextView tvGrade;
        @Bind(R.id.tv_director)
        TextView tvDirector;
        @Bind(R.id.tv_actor)
        TextView tvActor;
        @Bind(R.id.tv_watcher)
        TextView tvWatcher;
        @Bind(R.id.ratingBar)
        RatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


}
