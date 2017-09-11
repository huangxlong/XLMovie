package com.hxl.xlmovie.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hxl.xlmovie.BaseActivity;
import com.hxl.xlmovie.R;
import com.hxl.xlmovie.adapter.ActorAdapter;
import com.hxl.xlmovie.constant.UrlConstant;
import com.hxl.xlmovie.entity.Detail;
import com.hxl.xlmovie.entity.RspDto;
import com.hxl.xlmovie.entity.Theater;
import com.hxl.xlmovie.http.HttpUtil;
import com.hxl.xlmovie.http.MCallBack;
import com.hxl.xlmovie.util.ToastUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity {

    @Bind(R.id.aviLoading)
    AVLoadingIndicatorView aviLoading;
    @Bind(R.id.layout_main)
    LinearLayout layoutMain;
    @Bind(R.id.iv_detail)
    SimpleDraweeView ivDetail;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_tag)
    TextView tvTag;
    @Bind(R.id.tv_trailers)
    TextView tvTrailers;
    @Bind(R.id.tv_pubDate)
    TextView tvPubDate;
    @Bind(R.id.tv_duration)
    TextView tvDuration;
    @Bind(R.id.ratingBar)
    RatingBar ratingBar;
    @Bind(R.id.tv_count)
    TextView tvCount;
    @Bind(R.id.tv_summary)
    TextView tvSummary;
    @Bind(R.id.tv_sold)
    TextView tvSold;
    @Bind(R.id.actor_recycler)
    RecyclerView actorRecycler;
    @Bind(R.id.tv_average)
    TextView tvAvarage;

    private Detail detail;
    private Theater.SubjectsBean subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setWindowStatusBarColor(this, R.color.detailColor);
        aviLoading.show();
        subjects = (Theater.SubjectsBean) getIntent().getExtras().getSerializable("theater");

        //请求电影信息
        request();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);


        actorRecycler.setLayoutManager(linearLayoutManager);


    }

    private void request() {
        HashMap<String, String> parms = new HashMap<>();
        parms.put("apikey", "0b2bdeda43b5688921839c8ecb20399b");
        parms.put("city", "成都");
        parms.put("client", "");
        parms.put("udid", "");

        HttpUtil.postParams(UrlConstant.detail_url + subjects.getId(), parms, new MCallBack() {
            @Override
            public void onResponse(final RspDto.BaseRsp rsp, final String data) {
                DetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        aviLoading.hide();
                        aviLoading.setVisibility(View.GONE);
                        layoutMain.setVisibility(View.VISIBLE);
                        if (rsp.code == 1) {
                            detail = new Gson().fromJson(data, Detail.class);
                            setData();
                        } else {
                            ToastUtil.show(DetailActivity.this, rsp.msg);
                        }
                    }
                });

            }
        });
    }

    private void setData() {
        ivDetail.setImageURI(detail.getImages().getLarge());
        tvTitle.setText(subjects.getTitle());
        String genres = "";
        for (int i = 0; i < subjects.getGenres().size(); i++) {
            genres += " / " + subjects.getGenres().get(i);
        }
        tvTag.setText(subjects.getYear() + genres);
        tvTrailers.setText("原名：" + subjects.getOriginal_title());
        tvPubDate.setText("上映时间：" + subjects.getMainland_pubdate() + "(中国大陆)");
        tvDuration.setText("片长：" + subjects.getDurations().get(0));
        ratingBar.setRating(Float.parseFloat(subjects.getRating().getStars()) / 10);
        tvCount.setText(subjects.getCollect_count() + "人");
        if (subjects.getRating().getAverage() > 0) {
            tvAvarage.setText(subjects.getRating().getAverage() + "");
        } else {
            tvAvarage.setText("暂无评分");
        }
        tvSummary.setText("\t\t" + detail.getSummary());

        ActorAdapter actorAdapter = new ActorAdapter(this, subjects.getDirectors(), detail.getCasts());
        actorRecycler.setAdapter(actorAdapter);

        actorAdapter.setOnItemClickListener(new ActorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(DetailActivity.this, ActorActivity.class);
                Bundle bundle = new Bundle();
                if (position == 0) {
                    bundle.putString("id", subjects.getDirectors().get(position).getId());
                } else {
                    bundle.putString("id", detail.getCasts().get(position - 1).getId());
                }
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }


    @OnClick({R.id.layout_back, R.id.tv_sold})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                onBackPressed();
                break;

            case R.id.tv_sold:

                if (tvSold.getText().toString().equals("收起")) {
                    tvSold.setText("展开");
                    Drawable drawable = getResources().getDrawable(R.drawable.arrow_down);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tvSold.setCompoundDrawables(drawable, null, null, null);
                    tvSummary.setMaxLines(4);
                    tvSummary.setEllipsize(TextUtils.TruncateAt.END);
                } else {
                    tvSold.setText("收起");
                    Drawable drawable = getResources().getDrawable(R.drawable.arrow_up);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tvSold.setCompoundDrawables(drawable, null, null, null);
                    tvSummary.setMaxLines(40);
                    tvSummary.setEllipsize(null);
                }
                break;
        }
    }
}
