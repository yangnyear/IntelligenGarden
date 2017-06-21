package com.swpuiot.pastoralwisdom.View.controller;

import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;

/**
 * Created by 羊荣毅_L on 2017/1/3.
 */
public class BannerLoader extends ImageLoader{
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        int imageid= (int) path;
        imageView.setImageResource(imageid);
    }
}
