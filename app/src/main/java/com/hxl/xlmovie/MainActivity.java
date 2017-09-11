package com.hxl.xlmovie;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.hxl.xlmovie.adapter.TheaterAdapter;
import com.hxl.xlmovie.constant.UrlConstant;
import com.hxl.xlmovie.entity.RspDto;
import com.hxl.xlmovie.entity.Theater;
import com.hxl.xlmovie.http.HttpUtil;
import com.hxl.xlmovie.http.MCallBack;
import com.hxl.xlmovie.ui.DetailActivity;
import com.hxl.xlmovie.util.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_request)
    Button btnRequest;
    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    private Theater theater;
    private TheaterAdapter theaterAdapter;
    private List<Theater.SubjectsBean> subList = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        refreshLayout.setRefreshing(true);
        request();

        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                request();
            }
        });

        theaterAdapter = new TheaterAdapter(MainActivity.this, subList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(this, R.color.lineColor)));
        recycler.setAdapter(theaterAdapter);

        ((DefaultItemAnimator) recycler.getItemAnimator()).setSupportsChangeAnimations(false);

        theaterAdapter.setOnItemClickListener(new TheaterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("theater", theater.getSubjects().get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    @OnClick({R.id.btn_request})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_request:

                request();


                break;
        }
    }

    private void request() {
        HashMap<String, String> parms = new HashMap<>();
        parms.put("apikey", "0b2bdeda43b5688921839c8ecb20399b");
        parms.put("city", "成都");
        parms.put("start", "0");
        parms.put("count", "100");
        parms.put("client", "");
        parms.put("udid", "");

        HttpUtil.postParams(UrlConstant.theater_url, parms, new MCallBack() {
            @Override
            public void onResponse(final RspDto.BaseRsp rsp, final String data) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                        if (rsp.code == 1) {
                            theater = new Gson().fromJson(data, Theater.class);
                            subList.clear();
                            subList.addAll(theater.getSubjects());
                            theaterAdapter.notifyDataSetChanged();
                            recycler.smoothScrollToPosition(0);
                        } else {
                            ToastUtil.show(MainActivity.this, rsp.msg);
                        }
                    }
                });

            }
        });
    }
}
