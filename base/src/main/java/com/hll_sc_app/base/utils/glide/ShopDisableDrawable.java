package com.hll_sc_app.base.utils.glide;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextPaint;

import com.hll_sc_app.base.utils.UIUtils;

/**
 * 门店停用
 *
 * @author zhuyingsong
 * @date 2019/7/17
 */
public class ShopDisableDrawable extends Drawable {
    private static final String DISABLE_TEXT = "门店已停用";
    private static final int TEXT_SIZE = UIUtils.dip2px(9);
    private static final int HORIZON_PADDING = UIUtils.dip2px(6);
    private static final int VERTICAL_PADDING = UIUtils.dip2px(3);

    private Paint mBgPaint;
    private TextPaint mPaint;
    private Bitmap mSource;
    private Bitmap mBgBitmap;
    private RectF mRect;
    private int mSourceWidth;
    private int mSourceHeight;

    public ShopDisableDrawable(Bitmap source) {
        this.mSource = source;
        mSourceWidth = mSource.getWidth();
        mSourceHeight = mSource.getHeight();
        mBgBitmap = Bitmap.createBitmap(mSourceWidth, mSourceHeight, Bitmap.Config.ARGB_8888);
        mBgBitmap.eraseColor(0x7FFFFFFF);

        mPaint = new TextPaint();
        mPaint.setTextSize(TEXT_SIZE);
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float textHeight = (-fontMetrics.ascent + fontMetrics.descent + fontMetrics.leading);
        float textWidth = mPaint.measureText(DISABLE_TEXT);

        mBgPaint = new Paint();
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setColor(0x66000000);

        mRect = new RectF();
        mRect.left = 0;
        mRect.top = mSourceHeight - textHeight - 2 * VERTICAL_PADDING;
        mRect.right = mSourceHeight;
        mRect.bottom = mSourceWidth;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawBitmap(mSource, null, getBounds(), null);
        canvas.drawBitmap(mBgBitmap, null, getBounds(), null);
        canvas.drawRect(mRect, mBgPaint);
        canvas.drawText(DISABLE_TEXT, (float) mSourceWidth / 2,
            (float) mSourceHeight - (mRect.bottom - mRect.top) / 2 + (-mPaint.ascent() - mPaint.descent()) / 2, mPaint);
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
