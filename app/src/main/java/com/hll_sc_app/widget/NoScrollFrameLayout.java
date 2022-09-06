package com.hll_sc_app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2021/12/31.
 */
public class NoScrollFrameLayout extends FrameLayout {
    public NoScrollFrameLayout(Context context) {
        this(context, null);
    }

    public NoScrollFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoScrollFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return true;
    }
}
