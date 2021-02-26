package com.hll_sc_app.base.utils.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hll_sc_app.base.R;
import com.hll_sc_app.base.utils.UIUtils;

/**
 * 活动商品图片-添加角标
 *
 * @author zhuyingsong
 * @date 2019/4/18
 */
public class ActivityMarkDrawable extends Drawable {
    private static final int TEXT_PADDING_TOP = UIUtils.dip2px(2);
    private static final int MARK_PADDING_LEFT = UIUtils.dip2px(5);
    private static final float WIDTH_RATIO = 0.233f;
    private static final float HEIGHT_RATIO = 0.517F;
    private static final float TEXT_RATIO = 0.167F;

    private Rect mMarkRect;
    private Bitmap mMarkBitmap;
    private TextPaint mPaint;
    private String mMarkText;

    private Bitmap mSource;
    private int textWidth;
    private int markWidth;

    public ActivityMarkDrawable(Context context, Bitmap source, String text) {
        this.mSource = source;
        this.mMarkText = text;
        this.mMarkBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_activity_tip_yellow);
        markWidth = (int) (mSource.getWidth() * WIDTH_RATIO);
        mMarkRect = new Rect(MARK_PADDING_LEFT, 0, markWidth + MARK_PADDING_LEFT, (int) (mSource.getHeight() * HEIGHT_RATIO));
        mPaint = new TextPaint();
        mPaint.setTextSize(mSource.getWidth() * TEXT_RATIO);
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
        textWidth = (int) mPaint.measureText(mMarkText, 0, 1);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawBitmap(mSource, null, getBounds(), null);
        canvas.drawBitmap(mMarkBitmap, null, mMarkRect, null);
        StaticLayout staticLayout
            = new StaticLayout(mMarkText, mPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        canvas.translate(MARK_PADDING_LEFT + (float) (markWidth / 2), TEXT_PADDING_TOP);
        staticLayout.draw(canvas);
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
