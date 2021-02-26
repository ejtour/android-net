package com.hll_sc_app.base.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import com.hll_sc_app.base.utils.UIUtils;

/**
 * MaxHeightNestedScrollView
 *
 * @author zhuyingsong
 * @date 2019-07-01
 */
public class MaxHeightNestedScrollView extends NestedScrollView {
    private int maxHeight = UIUtils.dip2px(300);

    public MaxHeightNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public MaxHeightNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (maxHeight > 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
