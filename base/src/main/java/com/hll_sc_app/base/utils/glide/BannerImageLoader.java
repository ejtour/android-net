package com.hll_sc_app.base.utils.glide;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.hll_sc_app.base.R;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.List;

/**
 * Banner处用到的ImageLoader
 *
 * @author zhuyingsong
 * @date 2019-6-3
 */
public class BannerImageLoader implements ImageLoaderInterface<GlideImageView> {
    private List<String> mUrls;

    public BannerImageLoader(List<String> mUrls) {
        this.mUrls = mUrls;
    }

    @Override
    public void displayImage(Context context, Object path, GlideImageView imageView) {
        imageView.isPreview(true);
        imageView.setPlaceholder(ContextCompat.getDrawable(context, R.drawable.base_ic_banner_placeholder));
        imageView.setImageURL(path.toString());
        imageView.setUrls(mUrls);
    }

    @Override
    public GlideImageView createImageView(Context context) {
        return new GlideImageView(context);
    }
}
