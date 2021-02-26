package com.hll_sc_app.base.utils.glide;

import android.content.Context;

import androidx.core.content.ContextCompat;

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
        imageView.setPlaceholder(ContextCompat.getDrawable(context, R.drawable.ic_placeholder_large));
        imageView.setImageURL(path.toString());
        imageView.setUrls(mUrls);
    }

    @Override
    public GlideImageView createImageView(Context context) {
        return new GlideImageView(context);
    }
}
