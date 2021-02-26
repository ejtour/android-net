package com.hll_sc_app.base.utils.glide;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import com.bumptech.glide.request.target.DrawableImageViewTarget;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 12/4/20.
 */
public class PlaceholderFixedTarget extends DrawableImageViewTarget {

    private final Drawable mDefaultBg;

    public PlaceholderFixedTarget(ImageView view) {
        super(view);
        mDefaultBg = view.getBackground();
    }

    @Override
    protected void setResource(@Nullable Drawable resource) {
        if (mDefaultBg != view.getBackground()) {
            ViewCompat.setBackground(view, mDefaultBg);
        }
        super.setResource(resource);
    }

    @Override
    public void setDrawable(Drawable drawable) {
        if (drawable instanceof NinePatchDrawable) {
            ViewCompat.setBackground(view, drawable);
        } else {
            super.setDrawable(drawable);
        }
    }
}
