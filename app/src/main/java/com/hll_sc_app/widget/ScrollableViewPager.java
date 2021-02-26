package com.hll_sc_app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class ScrollableViewPager extends ViewPager {
    private boolean isScrollable = false;

    public ScrollableViewPager(Context context) {
        this(context, null);
    }

    public ScrollableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isScrollable)
            return super.onInterceptTouchEvent(ev);
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isScrollable)
            return super.onTouchEvent(ev);
        return true;
    }

    public void setScrollable(boolean scrollable) {
        isScrollable = scrollable;
    }
}