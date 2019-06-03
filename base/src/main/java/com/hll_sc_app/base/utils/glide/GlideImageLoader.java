package com.hll_sc_app.base.utils.glide;

import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;

/**
 * Banner处用到的ImageLoader
 *
 * @author zhuyingsong
 * @date 2019-6-3
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        GlideApp
            .with(context)
            .load("http://res.hualala.com/" + path)
            .into(imageView);
    }
}
