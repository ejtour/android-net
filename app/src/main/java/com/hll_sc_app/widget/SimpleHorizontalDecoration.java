package com.hll_sc_app.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 为 RecyclerView　指定分割线
 */

public class SimpleHorizontalDecoration extends RecyclerView.ItemDecoration {
    private Paint mLinePaint;
    private int mLineWidth;
    private int mLeftMargin;
    private int mRightMargin;
    private int mTopMargin;
    private int mBottomMargin;

    /**
     * @param lineColor 分割线颜色
     * @param lineWidth 线宽
     */
    public SimpleHorizontalDecoration(int lineColor, int lineWidth) {
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(lineColor);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeCap(Paint.Cap.SQUARE);
        mLinePaint.setStrokeWidth(lineWidth);
        mLineWidth = lineWidth;
    }

    /**
     * 分别设置分割线四周的边距
     *
     * @param left   左边距
     * @param top    上边距
     * @param right  右边距
     * @param bottom 下边距
     */
    public void setLineMargin(int left, int top, int right, int bottom) {
        mLeftMargin = left;
        mTopMargin = top;
        mRightMargin = right;
        mBottomMargin = bottom;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) parent.getLayoutManager()).getOrientation() == RecyclerView.HORIZONTAL) {
                int childCount = parent.getChildCount();
                int step = 1;
                if (parent.getLayoutManager() instanceof GridLayoutManager) {
                    step = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
                }
                for (int i = 0; i < childCount - 1; i += step) {
                    View child = parent.getChildAt(i);
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
                    int startX = child.getRight() + params.rightMargin + mLeftMargin;
                    int stopX = startX + mLineWidth;
                    int startY = (child.getBottom() + params.bottomMargin) / 2;
                    mLinePaint.setStrokeWidth(child.getBottom() + params.bottomMargin - mTopMargin - mBottomMargin);
                    mLinePaint.setStrokeCap(Paint.Cap.BUTT);
                    c.drawLine(startX, startY, stopX, startY, mLinePaint);
                }
            } else {
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
                    int startY = child.getBottom() + params.bottomMargin + mTopMargin + mLineWidth / 2;
                    c.drawLine(startX, startY, stopX, startY, mLinePaint);
                }
            }
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
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (((LinearLayoutManager) parent.getLayoutManager()).getOrientation() == RecyclerView.HORIZONTAL) {
            outRect.set(lp.leftMargin, lp.topMargin, lp.rightMargin + mLineWidth + mRightMargin + mLeftMargin, lp.bottomMargin);
        } else {
            outRect.set(lp.leftMargin, lp.topMargin, lp.rightMargin, lp.bottomMargin + mLineWidth + mBottomMargin + mTopMargin);
        }
    }
}
