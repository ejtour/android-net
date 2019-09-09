package com.hll_sc_app.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 为 RecyclerView　指定分割线
 */

public class SimpleDecoration extends RecyclerView.ItemDecoration {
    private int mLineColor;
    private Paint mLinePaint;
    private int mLineWidth;
    private int mLeftMargin;
    private int mRightMargin;
    private int mTopMargin;
    private int mBottomMargin;
    private int mDecorColor;

    /**
     * @param lineColor 分割线颜色
     * @param lineWidth 线宽
     */
    public SimpleDecoration(int lineColor, int lineWidth) {
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLineColor = lineColor;
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeCap(Paint.Cap.BUTT);
        mLinePaint.setStrokeWidth(lineWidth);
        mLineWidth = lineWidth;
    }

    /**
     * 分别设置分割线四周的边距
     *
     * @param left       左边距
     * @param top        上边距
     * @param right      右边距
     * @param bottom     下边距
     * @param decorColor 装饰颜色，用于为分割线左右（竖直布局）或上下（水平布局）的间距区域绘制颜色
     */
    public void setLineMargin(int left, int top, int right, int bottom, @ColorInt int decorColor) {
        mLeftMargin = left;
        mTopMargin = top;
        mRightMargin = right;
        mBottomMargin = bottom;
        mDecorColor = decorColor;
    }

    public void setLineMargin(int left, int top, int right, int bottom) {
        setLineMargin(left, top, right, bottom, Color.TRANSPARENT);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) parent.getLayoutManager()).getOrientation() == RecyclerView.HORIZONTAL) {
                drawHorizontal(c, parent);
            } else {
                drawVertical(c, parent);
            }
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        int startX = parent.getPaddingLeft() + mLeftMargin;
        int stopX = parent.getWidth() - parent.getPaddingRight() - mRightMargin;
        int step = 1;
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            step = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
        }
        for (int i = 0; i < childCount - 1; i += step) {
            View child = parent.getChildAt(i);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
            float posY = child.getBottom() + params.bottomMargin + mTopMargin + mLineWidth / 2f;
            if (mDecorColor != Color.TRANSPARENT) {
                mLinePaint.setColor(mDecorColor);
                c.drawLine(0, posY, parent.getWidth(), posY, mLinePaint);
            }
            mLinePaint.setColor(mLineColor);
            c.drawLine(startX, posY, stopX, posY, mLinePaint);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        int startY = parent.getPaddingTop() + mTopMargin;
        int stopY = parent.getHeight() - parent.getPaddingBottom() - mBottomMargin;
        int step = 1;
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            step = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
        }
        for (int i = 0; i < childCount - 1; i += step) {
            View child = parent.getChildAt(i);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
            float posX = child.getRight() + params.rightMargin + mLeftMargin + mLineWidth / 2f;
            if (mDecorColor != Color.TRANSPARENT) {
                mLinePaint.setColor(mDecorColor);
                c.drawLine(posX, 0, posX, parent.getHeight(), mLinePaint);
            }
            mLinePaint.setColor(mLineColor);
            c.drawLine(posX, startY, posX, stopY, mLinePaint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getAdapter() == null) {
            return;
        }
        int childCount = parent.getAdapter().getItemCount();
        if (parent.getChildAdapterPosition(view) == childCount - 1) {
            return;
        }
        if (((LinearLayoutManager) parent.getLayoutManager()).getOrientation() == RecyclerView.HORIZONTAL) {
            outRect.set(0, 0, mLineWidth + mRightMargin + mLeftMargin, 0);
        } else {
            outRect.set(0, 0, 0, mLineWidth + mBottomMargin + mTopMargin);
        }
    }
}
