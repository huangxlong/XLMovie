package com.hxl.xlmovie.ui.douban.fragment;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hxl.xlmovie.R;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Administrator on 2018/1/25 0025.
 */

public class PhotosViewFragment extends Fragment {

    private String url;
    private PhotoViewAttacher attacher;
    private PhotoViewAttacher photoViewAttacher;

    public static PhotosViewFragment newInstance(String url) {
        PhotosViewFragment viewFragment = new PhotosViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        viewFragment.setArguments(bundle);

        return viewFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString("url");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        final ImageView ivPhoto = (ImageView) view.findViewById(R.id.iv_photo);
        if (attacher == null) {
            attacher = new PhotoViewAttacher(ivPhoto);
        }
        if (url != null) {
            Glide.with(getActivity()).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    ivPhoto.setImageBitmap(resource);
                    photoViewAttacher = new PhotoViewAttacher(ivPhoto);
                }
            });
        }
        return view;
    }
}
