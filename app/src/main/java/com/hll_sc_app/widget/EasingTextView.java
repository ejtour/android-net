package com.hll_sc_app.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/29
 */

public class EasingTextView extends AppCompatTextView {
    private ValueAnimator mAnimator;
    private boolean mIsMoney;

    public EasingTextView(Context context) {
        this(context, null);
    }

    public EasingTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EasingTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void easingText(float endValue, boolean isMoney) {
        mIsMoney = isMoney;
        if (mAnimator != null) {
            mAnimator.cancel();
        }
        String curText = getText().toString();
        float curValue = 0;
        if (!TextUtils.isEmpty(curText)) {
            curText = curText.replaceAll(",", "");
            curValue = Float.valueOf(curText);
        }
        if (curValue != endValue) {
            mAnimator = ValueAnimator.ofFloat(curValue, endValue);
            mAnimator.setDuration(500);
            mAnimator.addUpdateListener(animation -> {
                float value = Float.valueOf(animation.getAnimatedValue().toString());
                setText(mIsMoney ? CommonUtils.formatMoney(value) : CommonUtils.formatNumber(value));
            });
            mAnimator.start();
        } else {
            setText(mIsMoney ? CommonUtils.formatMoney(endValue) : CommonUtils.formatNumber(endValue));
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }
        super.onDetachedFromWindow();
    }
}
