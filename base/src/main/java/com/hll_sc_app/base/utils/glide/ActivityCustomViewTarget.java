package com.hll_sc_app.base.utils.glide;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.Objects;

/**
 * 活动、商品售罄
 */
class ActivityCustomViewTarget extends CustomViewTarget<GlideImageView, BitmapDrawable> {
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
        toShowImage(Objects.requireNonNull(drawable2Bitmap(errorDrawable)));
    }

    @Override
    public void onResourceReady(@NonNull BitmapDrawable resource, @Nullable Transition transition) {
        toShowImage(resource.getBitmap());
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

    private Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                .createBitmap(
                    drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

}
