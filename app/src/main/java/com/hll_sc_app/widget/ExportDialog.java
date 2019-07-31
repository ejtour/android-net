package com.hll_sc_app.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.constraint.Group;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/14
 */

public class ExportDialog extends BaseDialog {

    @BindView(R.id.de_title)
    TextView mTitle;
    @BindView(R.id.de_state)
    ImageView mState;
    @BindView(R.id.de_tip)
    TextView mTip;
    @BindView(R.id.de_edit)
    EditText mEdit;
    @BindView(R.id.de_action)
    TextView mAction;
    @BindView(R.id.de_tip_group)
    Group mTipGroup;
    @BindView(R.id.de_edit_group)
    Group mEditGroup;
    private OnClickListener mListener;

    private ExportDialog(@NonNull Activity context) {
        super(context);
    }

    public static Builder newBuilder(Activity context) {
        return new Builder(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
    }

    private void initWindow() {
        if (getWindow() == null) {
            return;
        }
        getWindow().setBackgroundDrawableResource(R.drawable.base_bg_white_radius_5_solid);
        if (mEdit.getVisibility() == View.VISIBLE)
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        getWindow().getAttributes().width = UIUtils.getScreenWidth(getContext()) - UIUtils.dip2px(110);
        setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_export, null);
        ButterKnife.bind(this, view);
        return view;
    }

    private void setTip(CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            mEditGroup.setVisibility(View.VISIBLE);
            mTipGroup.setVisibility(View.GONE);
            ((ViewGroup.MarginLayoutParams) mAction.getLayoutParams()).topMargin = UIUtils.dip2px(5);
        } else {
            mEdit.setVisibility(View.GONE);
            mTipGroup.setVisibility(View.VISIBLE);
            ((ViewGroup.MarginLayoutParams) mAction.getLayoutParams()).topMargin = UIUtils.dip2px(0);
            mTip.setText(text);
        }
    }

    private void setTitleText(CharSequence text) {
        mTitle.setText(text);
    }

    private void setTitleState(@DrawableRes int resId) {
        mState.setImageResource(resId);
    }

    private void setButton(CharSequence text, OnClickListener listener) {
        mListener = listener;
        mAction.setText(text);
    }

    @OnClick(R.id.de_close)
    public void close() {
        dismiss();
    }

    public interface OnClickListener {
        void onClick(String email);
    }


    @OnClick(R.id.de_action)
    public void action() {
        String content = null;
        if (mEdit.getVisibility() == View.VISIBLE) {
            content = mEdit.getText().toString();
            if (content.length() == 0) {
                ToastUtils.showShort(getContext(), "邮箱不能为空");
                return;
            } else if (!Utils.checkEmail(content)) {
                ToastUtils.showShort(getContext(), "邮箱格式不正确");
                return;
            }
        }
        if (mListener != null) {
            mListener.onClick(content);
        }
        dismiss();
    }


    public static class Builder {
        private final Params P;

        private Builder(Activity context) {
            P = new Params();
            P.mContext = context;
            P.mCancelable = true;
        }

        /**
         * ic_dialog_state_success
         * ic_dialog_state_failure
         *
         * @param resId resId
         * @return Builder
         */
        public Builder setState(int resId) {
            P.mState = resId;
            return this;
        }

        public Builder setTitle(CharSequence msg) {
            P.mTitle = msg;
            return this;
        }

        public Builder setTip(CharSequence msg) {
            P.mTip = msg;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }

        public Builder setButton(CharSequence text, OnClickListener listener) {
            P.mOnClickListener = listener;
            P.actionText = text;
            return this;
        }

        public ExportDialog create() {
            final ExportDialog dialog = new ExportDialog(P.mContext);
            P.apply(dialog);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            return dialog;
        }
    }

    static class Params {
        int mState;
        Activity mContext;
        boolean mCancelable;
        OnClickListener mOnClickListener;
        CharSequence mTitle;
        CharSequence mTip;
        CharSequence actionText;

        void apply(ExportDialog dialog) {
            dialog.setButton(actionText, mOnClickListener);
            dialog.setTitleState(mState);
            dialog.setTitleText(mTitle);
            dialog.setTip(mTip);
        }
    }
}
