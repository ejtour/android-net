package com.hll_sc_app.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/10/23
 */
public class AntiHalfCircleView extends View {
    private final Paint mPaint;
    private int mWidth;
    private int mHeight;
    private final Path mPath;

    public AntiHalfCircleView(Context context) {
        this(context, null);
    }

    public AntiHalfCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AntiHalfCircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.lineTo(mWidth, 0);
        mPath.lineTo(mWidth, mHeight);
        mPath.lineTo(0, mHeight);
        mPath.quadTo(mWidth * 2, mHeight / 2f, 0, 0);
        canvas.drawPath(mPath, mPaint);
    }
}
