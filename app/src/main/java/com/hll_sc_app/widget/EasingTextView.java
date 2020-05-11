package com.hll_sc_app.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.IntRange;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/29
 */

public class EasingTextView extends AppCompatTextView {
    public static final int INTEGER = 0;
    public static final int FLOAT = 1;
    public static final int MONEY = 2;
    private ValueAnimator mAnimator;
    /**
     * 0-整数 1-小数 2-钱
     */
    private int mFlag;
    private float mCurValue;
    private ITextProcessor mProcessor;

    public EasingTextView(Context context) {
        this(context, null);
    }

    public EasingTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EasingTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTag(getTag() == null ? "%s" : getTag().toString());
        setProcessor(String::toString);
    }

    @Override
    public void setTag(Object tag) {
        checkArg(tag.toString());
        super.setTag(tag);
    }

    private void checkArg(String placeholder) {
        String result = placeholder.replaceAll("%s", "");
        if (placeholder.length() - result.length() != 2) {
            throw new IllegalArgumentException("占位符参数值必须且只能一个字符占位符");
        }
    }

    public void setProcessor(ITextProcessor processor) {
        mProcessor = processor;
    }

    /**
     * @param flag 0-整数 1-小数 2-钱
     */
    public void easingText(double endValue, @IntRange(from = 0, to = 2) int flag) {
        mFlag = flag;
        if (mAnimator != null) {
            mAnimator.cancel();
        }
        if (mCurValue != endValue) {
            mAnimator = ValueAnimator.ofFloat(mCurValue, (float) endValue);
            mAnimator.setDuration(200);
            mAnimator.addUpdateListener(animation -> {
                mCurValue = Float.parseFloat(animation.getAnimatedValue().toString());
                updateText();
            });
            mAnimator.start();
        } else {
            updateText();
        }
    }

    private void updateText() {
        String value = mFlag == 2 ? CommonUtils.formatMoney(mCurValue) :
                mFlag == 1 ? CommonUtils.formatNumber(mCurValue) :
                        String.valueOf((int) Math.floor(mCurValue));
        setText(mProcessor.getText(String.format(getTag().toString(), value)));
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }
        super.onDetachedFromWindow();
    }

    public interface ITextProcessor {
        CharSequence getText(String rawText);
    }
}
