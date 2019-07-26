package com.hll_sc_app.base.widget;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.view.View;
import android.view.WindowManager;

/**
 * 背景变暗的PopupWindow
 *
 * @author zhuyingsong
 * @date 2017/6/29
 */
public class BaseShadowPopupWindow extends BasePopupWindow {

    public BaseShadowPopupWindow(Activity activity) {
        super(activity);
    }

    @Override
    public void showAsDropDownFix(View anchor) {
        showAnim();
        super.showAsDropDownFix(anchor);
    }


    @Override
    public void showAsDropDown(View anchor, int xOff, int yOff) {
        showAnim();
        super.showAsDropDown(anchor, xOff, yOff);
    }

    private void showAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(1f, 0.6f).setDuration(250);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float cVal = (Float) valueAnimator.getAnimatedValue();
                setWindowAlpha(cVal);
            }
        });
        animator.start();
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
    public void showAtLocation(View parent, int gravity, int x, int y) {
        showAnim();
        super.showAtLocation(parent, gravity, x, y);
    }

    @Override
    public void showAsDropDown(View anchor) {
        showAnim();
        super.showAsDropDown(anchor);
    }

    @Override
    public void dismiss() {
        dismissAnim();
        super.dismiss();
    }

    private void dismissAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(0.6f, 1.0f).setDuration(250);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float cVal = (Float) valueAnimator.getAnimatedValue();
                setWindowAlpha(cVal);
            }
        });
        animator.start();
    }
}
