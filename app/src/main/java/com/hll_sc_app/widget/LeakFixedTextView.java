package com.hll_sc_app.widget;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author <a href="mailto:xzx8023@vip.qq.com">Vixb</a>
 * @since 2020/3/8
 */

public class LeakFixedTextView extends AppCompatTextView {
    private boolean mSingleLine;

    public LeakFixedTextView(Context context) {
        this(context, null);
    }

    public LeakFixedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeakFixedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getMovementMethod() == null && !mSingleLine) {
            setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    @Override
    public void setSingleLine(boolean singleLine) {
        mSingleLine = singleLine;
        super.setSingleLine(singleLine);
    }

    @Override
    protected void onDetachedFromWindow() {
        setMovementMethod(null);
        super.onDetachedFromWindow();
    }
}
