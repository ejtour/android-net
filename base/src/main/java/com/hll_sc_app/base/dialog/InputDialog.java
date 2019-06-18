package com.hll_sc_app.base.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hll_sc_app.base.R;
import com.hll_sc_app.base.utils.UIUtils;

/**
 * 输入对话框
 *
 * @author zhuyingsong
 * @date 2019-06-18
 */
public class InputDialog extends BaseDialog {

    protected InputDialog(@NonNull Activity context) {
        super(context);
    }

    private InputDialog(@NonNull Activity context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected InputDialog(@NonNull Activity context, boolean cancelable,
                          @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static Builder newBuilder(Activity context) {
        return new Builder(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.base_dialog_input, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.base_bg_white_radius_5_solid);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = UIUtils.dip2px(275);
        }
    }

    public void setHint(String hint) {
        EditText editText = mRootView.findViewById(R.id.edt_content);
        editText.setHint(hint);
    }

    public void setText(String text) {
        EditText editText = mRootView.findViewById(R.id.edt_content);
        editText.setText(text);
        editText.setSelection(!TextUtils.isEmpty(text) ? text.length() : 0);
    }

    public void setTextTitle(String text) {
        TextView textView = mRootView.findViewById(R.id.txt_title);
        textView.setText(text);
    }

    public void setMaxLength(int maxLength) {
        EditText editText = mRootView.findViewById(R.id.edt_content);
        InputFilter[] filters = {new InputFilter.LengthFilter(maxLength)};
        editText.setFilters(filters);
    }

    public String getInputString() {
        EditText editText = mRootView.findViewById(R.id.edt_content);
        return editText.getText().toString().trim();
    }

    public void setRootView(View view) {
        mRootView = view;
    }

    public void setButton(final OnClickListener listener, String[] items) {
        TextView txtCancel = mRootView.findViewById(R.id.txt_cancel);
        txtCancel.setText(items[0]);
        txtCancel.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItem(InputDialog.this, 0);
            }
        });

        TextView txtConfirm = mRootView.findViewById(R.id.txt_confirm);
        txtConfirm.setText(items[1]);
        txtConfirm.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItem(InputDialog.this, 1);
            }
        });
    }

    public interface OnClickListener {
        void onItem(InputDialog dialog, int item);
    }

    public static class Builder {
        private final Params p;

        private Builder(Activity context) {
            p = new Params();
            p.mContext = context;
            p.mCancelable = true;
        }

        public Builder setButton(OnClickListener listener, String cancel, String verify) {
            p.mOnClickListener = listener;
            p.items = new String[]{cancel, verify};
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            p.mCancelable = cancelable;
            return this;
        }

        public Builder setHint(String hint) {
            p.mHint = hint;
            return this;
        }

        public Builder setMaxLength(int maxLength) {
            p.mMaxLength = maxLength;
            return this;
        }

        public Builder setText(String text) {
            p.mText = text;
            return this;
        }

        public Builder setTextTitle(String textTitle) {
            p.mTextTitle = textTitle;
            return this;
        }

        public InputDialog create() {
            final InputDialog dialog = new InputDialog(p.mContext, R.style.BaseDialog);
            dialog.setButton(p.mOnClickListener, p.items);
            dialog.setCancelable(p.mCancelable);
            dialog.setCanceledOnTouchOutside(p.mCancelable);
            dialog.setHint(p.mHint);
            dialog.setText(p.mText);
            dialog.setMaxLength(p.mMaxLength);
            dialog.setTextTitle(p.mTextTitle);
            return dialog;
        }
    }

    static class Params {
        Activity mContext;
        boolean mCancelable;
        OnClickListener mOnClickListener;
        String[] items;
        String mHint;
        String mText;
        String mTextTitle;
        int mMaxLength;
    }
}
