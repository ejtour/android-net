package com.hll_sc_app.base.dialog;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.hll_sc_app.base.R;

/**
 *  Dialog基类  
 *  
 *
 * @author  zhuyingsong
 * @date 2019-5-31
 */
public abstract class BaseDialog extends Dialog {
    public View mRootView;
    private Activity mActivity;

    public BaseDialog(@NonNull Activity context) {
        this(context, R.style.BaseDialog);
    }

    public BaseDialog(@NonNull Activity context, @StyleRes int themeResId) {
        super(context, themeResId);
        mActivity = context;
        mRootView = onCreateView(LayoutInflater.from(mActivity));
    }

    public abstract View onCreateView(LayoutInflater inflater);

    public BaseDialog(@NonNull Activity context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mActivity = context;
        mRootView = onCreateView(LayoutInflater.from(mActivity));
    }

    @Override
    public void show() {
        super.show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ValueAnimator animator = ValueAnimator.ofFloat(1f, 0.7f).setDuration(250);
            animator.addUpdateListener(valueAnimator -> {
                float cVal = (Float) valueAnimator.getAnimatedValue();
                setWindowAlpha(cVal);
            });
            animator.start();
        }
    }

    private void setWindowAlpha(float alpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = alpha;
        if (alpha == 1) {
            // 不移除该Flag的话,可能出现黑屏的bug
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        mActivity.getWindow().setAttributes(lp);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ValueAnimator animator = ValueAnimator.ofFloat(0.7f, 1.0f).setDuration(250);
            animator.addUpdateListener(valueAnimator -> {
                float cVal = (Float) valueAnimator.getAnimatedValue();
                setWindowAlpha(cVal);
            });
            animator.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mRootView);
    }
}
