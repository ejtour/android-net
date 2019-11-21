package com.hll_sc_app.app.message.chat;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hll_sc_app.R;

import cn.jiguang.imui.commons.ImageLoader;


/**
 * IM图片加载器
 *
 * @author zhuyingsong
 * @date 2019/3/26
 */
public class GlideMsgImageLoader implements ImageLoader {
    private Activity mActivity;
    private float density;
    private float minWidth;
    private float maxWidth;
    private float minHeight;
    private float maxHeight;

    public GlideMsgImageLoader(Activity mActivity) {
        this.mActivity = mActivity;
        density = mActivity.getResources().getDisplayMetrics().density;
        minWidth = 60 * density;
        maxWidth = 200 * density;
        minHeight = 60 * density;
        maxHeight = 200 * density;
    }

    @Override
    public void loadAvatarImage(ImageView avatarImageView, String string) {
        if (string.contains("R.drawable")) {
            int resId = mActivity.getResources().getIdentifier(string.replace("R.drawable.", ""), "drawable", mActivity.getPackageName());
            avatarImageView.setImageResource(resId);
        } else {
            Glide.with(mActivity)
                    .load("http://res.hualala.com/" + string)
                    .apply(new RequestOptions().placeholder(R.drawable.aurora_headicon_default))
                    .into(avatarImageView);
        }
    }

    @Override
    public void loadImage(final ImageView imageView, String string) {
        Glide.with(mActivity)
                .asBitmap()
                .load("http://res.hualala.com/" + string)
                .apply(new RequestOptions().fitCenter().placeholder(R.drawable.aurora_picture_not_found))
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        int imageWidth = resource.getWidth();
                        int imageHeight = resource.getHeight();
                        // 裁剪 bitmap
                        float width, height;
                        if (imageWidth > imageHeight) {
                            if (imageWidth > maxWidth) {
                                float temp = maxWidth / imageWidth * imageHeight;
                                height = temp > minHeight ? temp : minHeight;
                                width = maxWidth;
                            } else if (imageWidth < minWidth) {
                                float temp = minWidth / imageWidth * imageHeight;
                                height = temp < maxHeight ? temp : maxHeight;
                                width = minWidth;
                            } else {
                                float ratio = imageWidth / imageHeight;
                                if (ratio > 3) {
                                    ratio = 3;
                                }
                                height = imageHeight * ratio;
                                width = imageWidth;
                            }
                        } else {
                            if (imageHeight > maxHeight) {
                                float temp = maxHeight / imageHeight * imageWidth;
                                width = temp > minWidth ? temp : minWidth;
                                height = maxHeight;
                            } else if (imageHeight < minHeight) {
                                float temp = minHeight / imageHeight * imageWidth;
                                width = temp < maxWidth ? temp : maxWidth;
                                height = minHeight;
                            } else {
                                float ratio = imageHeight / imageWidth;
                                if (ratio > 3) {
                                    ratio = 3;
                                }
                                width = imageWidth * ratio;
                                height = imageHeight;
                            }
                        }
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        params.width = (int) width;
                        params.height = (int) height;
                        imageView.setLayoutParams(params);
                        Matrix matrix = new Matrix();
                        float scaleWidth = width / imageWidth;
                        float scaleHeight = height / imageHeight;
                        matrix.postScale(scaleWidth, scaleHeight);
                        imageView.setImageBitmap(Bitmap.createBitmap(resource, 0, 0, imageWidth, imageHeight, matrix, true));
                    }
                });
    }

    @Override
    public void loadVideo(ImageView imageCover, String uri) {
        long interval = 5000 * 1000;
        Glide.with(mActivity)
                .asBitmap()
                .load("http://res.hualala.com/" + uri)
                .apply(new RequestOptions().frame(interval).override(200, 400))
                .into(imageCover);
    }
}
