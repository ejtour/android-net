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
    private static final int TEXT_SIZE = UIUtils.dip2px(9);
    private static final int ROUND_CORNER = UIUtils.dip2px(10);
    private static final int HORIZON_PADDING = UIUtils.dip2px(6);
    private static final int VERTICAL_PADDING = UIUtils.dip2px(3);

    private Paint mBgPaint;
    private TextPaint mPaint;
    private Bitmap mSource;
    private Bitmap mBgBitmap;
    private RectF mRect;
    private int mSourceWidth;
    private int mSourceHeight;
    private String mTipsText;

    public ShopDisableDrawable(Bitmap source, String text) {
        this.mTipsText = text;
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
        float textWidth = mPaint.measureText(mTipsText);

        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setColor(0x66000000);
        mRect = new RectF();
        mRect.left = (float) mSourceWidth / 2 - textWidth / 2 - HORIZON_PADDING;
        mRect.top = (float) mSourceHeight / 2 - textHeight / 2 - VERTICAL_PADDING;
        mRect.right = mRect.left + textWidth + 2 * HORIZON_PADDING;
        mRect.bottom = mRect.top + textHeight + 2 * VERTICAL_PADDING;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawBitmap(mSource, null, getBounds(), null);
        canvas.drawBitmap(mBgBitmap, null, getBounds(), null);
        canvas.drawRoundRect(mRect, ROUND_CORNER, ROUND_CORNER, mBgPaint);
        canvas.drawText(mTipsText, (float) mSourceWidth / 2,
            (float) mSourceHeight / 2 + (-mPaint.ascent() - mPaint.descent()) / 2, mPaint);
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
