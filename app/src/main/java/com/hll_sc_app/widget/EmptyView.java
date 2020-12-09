package com.hll_sc_app.widget;

import android.animation.AnimatorInflater;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 空布局
 *
 * @author 朱英松
 * @date 2018/12/21
 */
public class EmptyView extends ConstraintLayout {
    @BindView(R.id.ve_img)
    ImageView mImage;
    @BindView(R.id.ve_tip)
    TextView mTip;
    @BindView(R.id.ve_action)
    Button mAction;
    @BindView(R.id.ve_label)
    TextView mLabel;
    private OnActionClickListener mOnActionClickListener;

    public EmptyView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        ViewGroup rootView = (ViewGroup) View.inflate(context, R.layout.view_empty, this);
        ButterKnife.bind(this, rootView);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public static EmptyView.Builder newBuilder(Activity context) {
        return new EmptyView.Builder(context);
    }


    public void setTips(CharSequence tips) {
        mTip.setText(tips);
        mTip.setVisibility(TextUtils.isEmpty(tips) ? GONE : VISIBLE);
    }

    /**
     * 网络有问题、重新加载
     */
    public void setNetError() {
        setTipsTitle("网络请求错误");
        setTips("请检查您的网络设置");
        setImage(R.drawable.ic_view_net_empty);
        mAction.getLayoutParams().width = UIUtils.dip2px(130);
        mAction.setVisibility(VISIBLE);
        mAction.setBackgroundResource(R.drawable.bg_reload_button);
        mAction.setText("重新加载");
        mAction.setTextAppearance(getContext(), R.style.TextAppearance_City22_Middle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mAction.setStateListAnimator(mLabel.getStateListAnimator());
        }
    }

    public void setOnActionClickListener(OnActionClickListener listener) {
        mOnActionClickListener = listener;
    }

    @OnClick(R.id.ve_action)
    public void action(Button view) {
        switch (view.getText().toString()) {
            case "重新加载":
                if (mOnActionClickListener != null)
                    mOnActionClickListener.retry();
                break;
            case "立即登录":
                RouterUtil.goToActivity(RouterConfig.USER_LOGIN);
                break;
            default:
                if (mOnActionClickListener != null)
                    mOnActionClickListener.action();
                break;
        }
    }

    public void setTipsTitle(CharSequence tipsTitle) {
        mLabel.setText(tipsTitle);
        mLabel.setVisibility(TextUtils.isEmpty(tipsTitle) ? GONE : VISIBLE);
    }

    public void setImage(int resID) {
        mImage.setImageResource(resID == 0 ? R.drawable.ic_view_empty : resID);
    }

    /**
     * 重置所有组件 均显示为空
     */
    public void reset() {
        setTipsTitle("");
        setTips("");
        setTipsButton("");
        setImage(0);
    }

    public void setTipsButton(CharSequence button) {
        if (TextUtils.isEmpty(button)) {
            mAction.setVisibility(GONE);
        } else {
            mAction.getLayoutParams().width = UIUtils.dip2px(230);
            mAction.setVisibility(VISIBLE);
            mAction.setBackgroundResource(R.drawable.bg_button_large_solid_primary);
            mAction.setTextAppearance(getContext(), R.style.TextAppearance_City22_Middle_White);
            mAction.setText(button);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mAction.setStateListAnimator(AnimatorInflater.loadStateListAnimator(getContext(),
                        getResources().getIdentifier("button_state_list_anim_material",
                                "anim", "android")));
            }
        }
    }

    public static class Builder {
        private final EmptyView.Params P;

        private Builder(Activity context) {
            P = new EmptyView.Params();
            P.mContext = context;
        }

        /**
         * 空页面小字提示
         *
         * @param tips 提示语
         * @return Builder
         */
        public EmptyView.Builder setTips(CharSequence tips) {
            P.mTips = tips;
            return this;
        }

        /**
         * 空页面大字提示
         *
         * @param tipsTitle 提示语
         * @return Builder
         */
        public EmptyView.Builder setTipsTitle(CharSequence tipsTitle) {
            P.mTipsTitle = tipsTitle;
            return this;
        }


        /**
         * 空页面图片
         *
         * @param resID 图片
         * @return Builder
         */
        public EmptyView.Builder setImage(int resID) {
            P.mResID = resID;
            return this;
        }


        /**
         * 空页面图片
         *
         * @param error true-网络问题
         * @return Builder
         */
        public EmptyView.Builder setNetError(boolean error) {
            P.mNetError = error;
            return this;
        }


        /**
         * 设置监听
         *
         * @param listener true-网络问题
         * @return Builder
         */
        public EmptyView.Builder setOnClickListener(OnActionClickListener listener) {
            P.mListener = listener;
            return this;
        }

        /**
         * 按钮提示
         *
         * @param button 提示语
         * @return Builder
         */
        public EmptyView.Builder setTipsButton(CharSequence button) {
            P.mButton = button;
            return this;
        }

        public EmptyView create() {
            final EmptyView view = new EmptyView(P.mContext);
            if (P.mNetError) {
                view.setNetError();
            } else {
                view.setTips(P.mTips);
                view.setTipsTitle(P.mTipsTitle);
                view.setTipsButton(P.mButton);
                view.setImage(P.mResID);
            }
            view.setOnActionClickListener(P.mListener);
            return view;
        }
    }

    static class Params {
        Activity mContext;
        int mResID;
        CharSequence mTips;
        CharSequence mButton;
        CharSequence mTipsTitle;
        boolean mNetError;
        OnActionClickListener mListener;
    }

    public interface OnActionClickListener {
        void retry();

        default void action() {

        }
    }
}
