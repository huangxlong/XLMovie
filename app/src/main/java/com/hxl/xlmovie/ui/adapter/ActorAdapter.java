package com.hxl.xlmovie.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hxl.xlmovie.R;
import com.hxl.xlmovie.entity.Detail;
import com.hxl.xlmovie.entity.Theater;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/7 0007.
 */

public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.ViewHolder> {


    private Context mContext;
    private List<Theater.SubjectsBean.DirectorsBean> mDirectors;
    private List<Detail.CastsBean> mCasts;
    private OnItemClickListener mOnItemClickListener = null;

    public ActorAdapter(Context context, List<Theater.SubjectsBean.DirectorsBean> directors, List<Detail.CastsBean> casts) {
        mContext = context;
        mDirectors = directors;
        mCasts = casts;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_actor, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, (int) v.getTag());
                }
            }
        });

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        if (position == 0) {
            holder.ivActor.setImageURI(mDirectors.get(position).avatars.medium);
            holder.tvName.setText("导演");
            holder.tvActor.setText(mDirectors.get(position).name);
        } else {
            holder.ivActor.setImageURI(mCasts.get(position - 1).avatars.medium);
            holder.tvName.setText(mCasts.get(position - 1).name);
            holder.tvActor.setText("");
        }


    }

    @Override
    public int getItemCount() {
        return mCasts.size() + 1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_actor)
        SimpleDraweeView ivActor;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_actor)
        TextView tvActor;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;

    }
}
