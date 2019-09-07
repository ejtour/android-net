package com.hll_sc_app.widget;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;

import com.hll_sc_app.R;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/7
 */

public class TabTwoGroupView extends ConstraintLayout {
    public TabTwoGroupView(Context context) {
        this(context, null);
    }

    public TabTwoGroupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabTwoGroupView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View.inflate(context, R.layout.item_tab_two_refresh_layout, this);
    }
}
