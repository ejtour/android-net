package com.hll_sc_app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;


/**
 * 画一个三角形
 *
 * @author jianjian
 */
public class TriangleView extends View {
    public static final int TOP = 0;
    public static final int BOTTOM = 1;
    private static final int RIGHT = 2;
    private static final int LEFT = 3;
    private static final int DEFAULT_WIDTH = 10;
    private static final int DEFAULT_HEIGHT = 6;
    private static final int DEFAULT_COLOR = android.R.color.white;
    private Paint mPaint;
    private int mColor;
    private int mWidth;
    private int mHeight;
    private int mDirection;
    private Path mPath;

    public TriangleView(final Context context) {
        this(context, null);
    }

    public TriangleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TriangleView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TriangleView, 0, 0);
        mColor = typedArray.getColor(R.styleable.TriangleView_trv_color, ContextCompat.getColor(getContext(), DEFAULT_COLOR));
        mDirection = typedArray.getInt(R.styleable.TriangleView_trv_direction, mDirection);
        typedArray.recycle();
        mPaint.setColor(mColor);
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPath = new Path();
        mDirection = TOP;
    }

    public void update(int direction, int color) {
        mPaint.setColor(color);
        mDirection = direction;
        mPath.reset();
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (mWidth == 0 || widthMode != MeasureSpec.EXACTLY) {
            mWidth = UIUtils.dip2px(DEFAULT_WIDTH);
        }
        if (mHeight == 0 || heightMode != MeasureSpec.EXACTLY) {
            mHeight = UIUtils.dip2px(DEFAULT_HEIGHT);
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mDirection) {
            case TOP:
                mPath.moveTo(0, mHeight);
                mPath.lineTo(mWidth, mHeight);
                mPath.lineTo(mWidth / 2, 0);
                break;
            case BOTTOM:
                mPath.moveTo(0, 0);
                mPath.lineTo(mWidth / 2, mHeight);
                mPath.lineTo(mWidth, 0);
                break;
            case RIGHT:
                mPath.moveTo(0, 0);
                mPath.lineTo(0, mHeight);
                mPath.lineTo(mWidth, mHeight / 2);
                break;
            case LEFT:
                mPath.moveTo(0, mHeight / 2);
                mPath.lineTo(mWidth, mHeight);
                mPath.lineTo(mWidth, 0);
                break;
            default:
                break;
        }

        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }
}