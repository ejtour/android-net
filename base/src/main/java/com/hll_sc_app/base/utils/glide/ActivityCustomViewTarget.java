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
    private String activityName;
    private GlideImageView mView;

    ActivityCustomViewTarget(GlideImageView view, String activityName) {
        super(view);
        this.mView = view;
        this.activityName = activityName;
    }

    ActivityCustomViewTarget(GlideImageView view) {
        super(view);
        this.mView = view;
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
        if (TextUtils.isEmpty(activityName)) {
            view.setImageDrawable(new SellOutDrawable(resource.getBitmap()));
        } else {
            view.setImageDrawable(new ActivityMarkDrawable(mView.getContext(), resource.getBitmap(), activityName));
        }
    }
}
