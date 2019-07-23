package com.hll_sc_app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.HorizontalScrollView;

/**
 * HorizontalScrollView
 */
public class SyncHorizontalScrollView extends HorizontalScrollView {
    private View[] mLinkageViews;
    private boolean isFling = true;
    private float startY;
    private int mTouchSlop;

    public SyncHorizontalScrollView(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }


    public SyncHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs((int) ev.getRawY() - startY) > mTouchSlop) {
                    return false;
                }
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void fling(int velocityX) {
        if (isFling) {
            super.fling(velocityX);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //设置控件滚动监听，得到滚动的距离，然后让传进来的view也设置相同的滚动具体
        if (mLinkageViews != null) {
            for (View view : mLinkageViews) {
                if (view != null) {
                    view.scrollTo(l, t);
                }
            }
        }
    }

    /**
     * 设置跟它水平联动的 view
     *
     * @param linkageViews 连动view
     */
    public void setLinkageViews(View... linkageViews) {
        mLinkageViews = linkageViews;
    }

    public void setFling(boolean fling) {
        isFling = fling;
    }
}