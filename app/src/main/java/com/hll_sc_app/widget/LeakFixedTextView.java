package com.hll_sc_app.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;

/**
 * @author <a href="mailto:xzx8023@vip.qq.com">Vixb</a>
 * @since 2020/3/8
 */

public class LeakFixedTextView extends AppCompatTextView {
    public LeakFixedTextView(Context context) {
        this(context, null);
    }

    public LeakFixedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeakFixedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void onDetachedFromWindow() {
        setMovementMethod(null);
        setText("");
        super.onDetachedFromWindow();
    }
}
