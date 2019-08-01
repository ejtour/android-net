package com.hll_sc_app.widget.wallet.create;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.hll_sc_app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/31
 */

public class CreateNameInputView extends ConstraintLayout {
    @BindView(R.id.cni_name_edit)
    EditText mNameEdit;
    @BindView(R.id.cni_next)
    TextView mNext;

    public CreateNameInputView(Context context) {
        this(context, null);
    }

    public CreateNameInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CreateNameInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_wallet_create_name_input, this);
        ButterKnife.bind(this, view);
    }

    public void setNextClickListener(OnClickListener listener) {
        mNext.setOnClickListener(listener);
    }

    @OnTextChanged(R.id.cni_name_edit)
    public void onTextChanged(CharSequence s) {
        mNext.setEnabled(!TextUtils.isEmpty(s));
    }

    @OnEditorAction(R.id.cni_name_edit)
    public boolean onEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_NEXT && mNext.isEnabled())
            mNext.performClick();
        return true;
    }

    public CharSequence getCompanyName() {
        return mNameEdit.getText();
    }

    public void setCompanyName(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            mNameEdit.setText(text);
            mNameEdit.setSelection(text.length());
        }
    }
}
