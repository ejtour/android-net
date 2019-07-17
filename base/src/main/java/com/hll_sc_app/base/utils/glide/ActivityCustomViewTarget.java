package com.hll_sc_app.base.utils.glide;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * 活动、商品售罄
 */
class ActivityCustomViewTarget extends CustomViewTarget<GlideImageView, BitmapDrawable> {
    private String type;

    ActivityCustomViewTarget(GlideImageView view, String activityName) {
        super(view);
        this.type = activityName;
    }

    ActivityCustomViewTarget(GlideImageView view) {
        super(view);
    }

    @Override
    protected void onResourceCleared(@Nullable Drawable placeholder) {
        view.setImageDrawable(placeholder);
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        view.setImageDrawable(errorDrawable);
    }

    @Override
    public void onResourceReady(@NonNull BitmapDrawable resource, @Nullable Transition transition) {
        if (TextUtils.equals(type, GlideImageView.DISABLE_IMAGE)) {
            view.setImageDrawable(new DisableDrawable(view.getContext(), resource.getBitmap()));
        } else if (TextUtils.equals(type, GlideImageView.DISABLE_SHOP)) {
            view.setImageDrawable(new ShopDisableDrawable(resource.getBitmap()));
        }
    }
}
