package com.hxl.xlmovie.ui.douban.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hxl.xlmovie.R;
import com.hxl.xlmovie.base.BaseActivity;
import com.hxl.xlmovie.base.contract.douban.MovieDetailContratct;
import com.hxl.xlmovie.entity.DetailBean;
import com.hxl.xlmovie.http.BaseSubscriber;
import com.hxl.xlmovie.presenter.douban.MovieDetailPresenter;
import com.hxl.xlmovie.ui.douban.adapter.ActorAdapter;
import com.hxl.xlmovie.entity.ActorBean;
import com.hxl.xlmovie.entity.TheaterBean;
import com.hxl.xlmovie.http.RetrofitFactory;
import com.hxl.xlmovie.ui.douban.adapter.ResourceAdapter;
import com.hxl.xlmovie.util.SPUtil;
import com.hxl.xlmovie.view.LoadingView;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailActivity extends BaseActivity<MovieDetailPresenter> implements MovieDetailContratct.View {

    //    @Bind(R.id.aviLoading)
//    AVLoadingIndicatorView aviLoading;
//    @Bind(R.id.layout_main)
//    LinearLayout layoutMain;
    @BindView(R.id.iv_detail)
    ImageView ivDetail;
    @BindView(R.id.tv_name)
    TextView tvTitle;
    @BindView(R.id.tv_tag)
    TextView tvTag;
    @BindView(R.id.tv_trailers)
    TextView tvTrailers;
    @BindView(R.id.tv_pubDate)
    TextView tvPubDate;
    @BindView(R.id.tv_duration)
    TextView tvDuration;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_summary)
    TextView tvSummary;
    @BindView(R.id.tv_sold)
    TextView tvSold;
    @BindView(R.id.actor_recycler)
    RecyclerView actorRecycler;
    @BindView(R.id.tv_average)
    TextView tvAvarage;
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.layout_city)
    LinearLayout layoutCity;
    @BindView(R.id.loadingView)
    LoadingView loadingView;
    @BindView(R.id.resource_recycler)
    RecyclerView resourceRecycler;

    private TheaterBean.SubjectsBean subjects;
    private DetailBean details;
    private String city;


    @Override
    protected int getLayout() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initView() {
        ivBack.setVisibility(View.VISIBLE);
        layoutCity.setVisibility(View.GONE);
        title.setText("详情");

        city = (String) SPUtil.get(DetailActivity.this, "city", "");
        subjects = (TheaterBean.SubjectsBean) getIntent().getExtras().getSerializable("subject");
        getDetail();

        loadingView.setRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDetail();
            }
        });

        LinearLayoutManager actorLayoutManager = new LinearLayoutManager(this);
        actorLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager resourceLayoutManager = new LinearLayoutManager(this);
        resourceLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        actorRecycler.setLayoutManager(actorLayoutManager);
        resourceRecycler.setLayoutManager(resourceLayoutManager);


    }

    /**
     * 获取电影详情跳转
     */
    private void getDetail() {
        loadingView.setStatus(LoadingView.STATUS_LOADING);
        mPresenter.getDetail();
    }

    private void setData() {
        Glide.with(this)
                .load(details.images.large)
                .fitCenter()
                .into(ivDetail);
        tvTitle.setText(subjects.title);
        String genres = "";
        for (int i = 0; i < subjects.genres.size(); i++) {
            genres += " / " + subjects.genres.get(i);
        }
        tvTag.setText(subjects.year + genres);
        tvTrailers.setText("原名：" + subjects.original_title);
        tvPubDate.setText("上映时间：" + subjects.mainland_pubdate + "(中国大陆)");
        tvDuration.setText("片长：" + subjects.durations.get(0));
        ratingBar.setRating(Float.parseFloat(subjects.rating.stars) / 10);
        tvCount.setText(subjects.collect_count + "人");
        if (subjects.rating.average > 0) {
            tvAvarage.setText(subjects.rating.average + "");
        } else {
            tvAvarage.setText("暂无评分");
        }
        tvSummary.setText("\t\t" + details.summary);

        ActorAdapter actorAdapter = new ActorAdapter(this, subjects.directors, details.casts);
        actorRecycler.setAdapter(actorAdapter);

        ResourceAdapter resourceAdapter = new ResourceAdapter(R.layout.item_resource, details.photos);
        resourceRecycler.setAdapter(resourceAdapter);

//        resourceAdapter.addHeaderView()

        actorAdapter.setOnItemClickListener(new ActorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                getActor(position);
            }
        });


        resourceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(DetailActivity.this, PhotosActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("urls", details);
                bundle.putInt("position", position);
                bundle.putBoolean("tag", false);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    /**
     * 获取演员详情url
     *
     * @param position
     */
    private void getActor(int position) {
        showLoadingDialog();
        String id;
        if (position == 0) {
            id = subjects.directors.get(position).id;
        } else {
            id = details.casts.get(position - 1).id;
        }
        RetrofitFactory.getMovieService()
                .getActor(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ActorBean>() {
                    @Override
                    public void onSuccess(ActorBean actorRsp) {
                        hideLoadingDialog();
                        Intent intent = new Intent(DetailActivity.this, ActorActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("url", actorRsp.mobile_url);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(String msg) {
                        hideLoadingDialog();
                        showToast(msg);
                    }
                });
    }

    @OnClick({R.id.iv_back, R.id.tv_sold, R.id.layout_photo})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
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

            case R.id.layout_photo:
                if (details.photos.size() != 0) {
                    Intent intent = new Intent(DetailActivity.this, PhotosActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("urls", details);
                    bundle.putInt("position", 0);
                    bundle.putBoolean("tag", true);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public MovieDetailPresenter initPresenter() {
        return new MovieDetailPresenter(this);
    }

    @Override
    public String getId() {
        return subjects.id;
    }

    @Override
    public String getCity() {
        return city;
    }



    @Override
    public void handleSuccess(DetailBean detail) {
        if (detail == null) {
            loadingView.setStatus(LoadingView.STATUS_EMPTY);
        }
        loadingView.setStatus(LoadingView.STATUS_DONE);
        details = detail;
        setData();
    }

    @Override
    public void handleFailure(String msg) {
        loadingView.setStatus(LoadingView.STATUS_ERROR);
        showToast(msg);
    }
}
