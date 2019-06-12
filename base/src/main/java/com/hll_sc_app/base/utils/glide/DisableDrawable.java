package com.hll_sc_app.base.utils.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hll_sc_app.base.R;

/**
 * 禁用
 *
 * @author zhuyingsong
 * @date 2019/6/12
 */
public class DisableDrawable extends Drawable {
    private Bitmap mSource;
    private Bitmap mBgBitmap;
    private Bitmap mDisableBitmap;
    private int mLeft;
    private int mTop;

    public DisableDrawable(Context context, Bitmap source) {
        this.mSource = source;
        int mSourceWidth = mSource.getWidth();
        int mSourceHeight = mSource.getHeight();
        mDisableBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.base_ic_goods_disable);
        int disableHeight = mDisableBitmap.getHeight();
        int disableWidth = mDisableBitmap.getWidth();
        mBgBitmap = Bitmap.createBitmap(mSourceWidth, mSourceHeight, Bitmap.Config.ARGB_8888);
        mBgBitmap.eraseColor(0x66000000);

        mLeft = mSourceWidth / 2 - disableWidth / 2;
        mTop = mSourceHeight / 2 - disableHeight / 2;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawBitmap(mSource, null, getBounds(), null);
        canvas.drawBitmap(mBgBitmap, null, getBounds(), null);
        canvas.drawBitmap(mDisableBitmap, mLeft, mTop, null);
    }

    @Override
    public void setAlpha(int alpha) {
        // no-op
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        // no-op
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
