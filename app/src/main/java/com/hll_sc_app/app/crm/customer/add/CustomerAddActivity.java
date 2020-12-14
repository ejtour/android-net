package com.hll_sc_app.app.crm.customer.add;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.crm.customer.CustomerHelper;
import com.hll_sc_app.base.BaseActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/15
 */

@Route(path = RouterConfig.CRM_CUSTOMER_ADD)
public class CustomerAddActivity extends BaseActivity {
    private static final int REQ_CODE = 0x882;

    @BindView(R.id.cca_unregistered)
    TextView mUnregistered;
    @BindView(R.id.cca_registered)
    TextView mRegistered;
    @BindViews({R.id.cca_intent, R.id.cca_registered, R.id.cca_unregistered, R.id.cca_plan, R.id.cca_record})
    List<TextView> mViews;
    @BindView(R.id.cca_dismiss)
    ImageView mDismiss;
    private ObjectAnimator mDismissAnimator;
    private int mMax;
    private Intent mIntent;

    public static void start(Activity context) {
        ARouter.getInstance().build(RouterConfig.CRM_CUSTOMER_ADD)
                .withOptionsCompat(ActivityOptionsCompat.makeCustomAnimation(context, 0, 0))
                .navigation(context, REQ_CODE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, 0xe6ffffff);
        setContentView(R.layout.activity_crm_customer_add);
        ButterKnife.bind(this);
        handleText(mUnregistered);
        handleText(mRegistered);
        mMax = UIUtils.dip2px(410);
        for (int i = 0; i < mViews.size(); i++) {
            TextView view = mViews.get(i);
            ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY",
                    mMax, 0, -100, 0)
                    .setDuration(400);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    view.setVisibility(View.VISIBLE);
                }
            });
            mDismiss.postDelayed(animator::start, 100 * i);
        }
        mDismissAnimator = ObjectAnimator.ofFloat(mDismiss, "rotation", -45, 0).setDuration(200);
        mDismissAnimator.setInterpolator(new LinearInterpolator());
        mDismissAnimator.start();
    }

    private void handleText(TextView textView) {
        String source = textView.getText().toString();
        int start = source.indexOf("户") + 2;
        int color = ContextCompat.getColor(this, R.color.color_999999);
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new RelativeSizeSpan(0.92f), start, source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(color), start, source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
    }

    @Override
    @OnClick(R.id.cca_root)
    public void onBackPressed() {
        mDismissAnimator.reverse();
        for (int i = mViews.size() - 1; i >= 0; i--) {
            TextView view = mViews.get(i);
            ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY",
                    0, mMax)
                    .setDuration(200);
            animator.setInterpolator(new LinearInterpolator());
            if (i == 0) animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    finish();
                    overridePendingTransition(0, 0);
                }
            });
            mDismiss.postDelayed(animator::start, 50 * (mViews.size() - 1 - i));
        }
    }

    @OnClick(R.id.cca_plan)
    public void plan() {
        mIntent = new Intent();
        mIntent.putExtra(CustomerHelper.GOTO_KEY, CustomerHelper.GOTO_PLAN);
        finishDirectly();
    }

    private void finishDirectly() {
        setResult(RESULT_OK, mIntent);
        finish();
        overridePendingTransition(0, 0);
    }

    @OnClick(R.id.cca_record)
    public void record() {
        mIntent = new Intent();
        mIntent.putExtra(CustomerHelper.GOTO_KEY, CustomerHelper.GOTO_RECORD);
        finishDirectly();
    }

    @OnClick(R.id.cca_unregistered)
    public void unregistered() {
        RouterUtil.goToActivity(RouterConfig.USER_REGISTER, this);
    }

    @OnClick(R.id.cca_registered)
    public void registered() {
        RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_LIST, this);
    }

    @OnClick(R.id.cca_intent)
    public void intent() {
        mIntent = new Intent();
        mIntent.putExtra(CustomerHelper.GOTO_KEY, CustomerHelper.GOTO_INTENT);
        finishDirectly();
    }
}
