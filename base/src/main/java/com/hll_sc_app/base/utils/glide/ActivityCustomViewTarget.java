package com.hll_sc_app.base.utils.glide;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hll_sc_app.base.utils.UIUtils;

import java.util.Objects;

/**
 * 活动、商品售罄
 */
class ActivityCustomViewTarget extends CustomViewTarget<GlideImageView, Drawable> {
    private String mType;

    ActivityCustomViewTarget(GlideImageView view, String type) {
        super(view);
        this.mType = type;
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
        toShowImage(Objects.requireNonNull(UIUtils.drawable2Bitmap(errorDrawable)));
    }

    @Override
    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition transition) {
        toShowImage(UIUtils.drawable2Bitmap(resource));
    }

    private void toShowImage(@NonNull Bitmap bitmap) {
        if (TextUtils.equals(mType, GlideImageView.DISABLE_IMAGE)) {
            view.setImageDrawable(new DisableDrawable(view.getContext(), bitmap));
        } else if (TextUtils.equals(mType, GlideImageView.DISABLE_SHOP)) {
            view.setImageDrawable(new ShopDisableDrawable(bitmap, "门店已停用"));
        } else if (TextUtils.equals(mType, GlideImageView.GROUP_BLOCK_UP)) {
            view.setImageDrawable(new ShopDisableDrawable(bitmap, "集团停用"));
        } else if (TextUtils.equals(mType, GlideImageView.GROUP_LOG_OUT)) {
            view.setImageDrawable(new ShopDisableDrawable(bitmap, "集团注销"));
        }
    }
}
