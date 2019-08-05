package com.hll_sc_app.widget;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/13
 */

public class RemarkDialog extends BaseDialog {
    @BindView(R.id.dr_edit)
    EditText mEdit;
    @BindView(R.id.txt_remain_num)
    TextView mRemainNum;
    @BindView(R.id.dr_cancel)
    TextView mCancel;
    @BindView(R.id.dr_ok)
    TextView mOk;
    private OnClickListener mListener;

    private RemarkDialog(@NonNull Activity context) {
        super(context);
    }

    public static Builder newBuilder(Activity context) {
        return new Builder(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_remark, null);
        ButterKnife.bind(this, view);
        return view;
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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        getWindow().getAttributes().width = UIUtils.getScreenWidth(getContext()) - UIUtils.dip2px(110);
        setCancelable(false);
    }

    private void setMaxLength(int max) {
        if (max == 0) mRemainNum.setVisibility(View.GONE);
        else {
            mRemainNum.setVisibility(View.VISIBLE);
            mRemainNum.setText(String.valueOf(max));
            mEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(max)});
            mRemainNum.setTag(max);
        }
    }

    private void setHint(CharSequence hint) {
        mEdit.setHint(hint);
    }

    private void setButtons(CharSequence negativeText, CharSequence positiveButton, OnClickListener listener) {
        mCancel.setText(negativeText);
        mOk.setText(positiveButton);
        mListener = listener;
    }

    @OnClick({R.id.dr_cancel, R.id.dr_ok})
    public void onViewClicked(View view) {
        if (mListener == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.dr_cancel:
                mListener.onClick(this, false, mEdit.getText().toString().trim());
                break;
            case R.id.dr_ok:
                mListener.onClick(this, true, mEdit.getText().toString().trim());
                break;
        }
    }

    @OnTextChanged(R.id.dr_edit)
    public void onTextChanged(CharSequence s) {
        if (mRemainNum.getTag() == null) return;
        int max = (int) mRemainNum.getTag();
        mRemainNum.setText(String.valueOf(max - s.length()));
    }

    public interface OnClickListener {
        void onClick(Dialog dialog, boolean positive, String content);
    }

    public static class Builder {
        private final Params P;

        private Builder(Activity context) {
            P = new Params();
            P.mContext = context;
        }

        public Builder setButtons(CharSequence negativeText, CharSequence positiveButton, OnClickListener listener) {
            P.mOnClickListener = listener;
            P.negativeText = negativeText;
            P.positiveButton = positiveButton;
            return this;
        }

        public Builder setHint(CharSequence hint) {
            P.hint = hint;
            return this;
        }

        public Builder setMaxLength(int maxMes) {
            P.maxLength = maxMes;
            return this;
        }

        public RemarkDialog create() {
            final RemarkDialog dialog = new RemarkDialog(P.mContext);
            P.apply(dialog);
            return dialog;
        }
    }

    static class Params {
        Activity mContext;
        CharSequence negativeText;
        CharSequence positiveButton;
        CharSequence hint;
        int maxLength;
        OnClickListener mOnClickListener;

        void apply(RemarkDialog dialog) {
            dialog.setButtons(negativeText, positiveButton, mOnClickListener);
            dialog.setHint(hint);
            dialog.setMaxLength(maxLength);
        }
    }
}
