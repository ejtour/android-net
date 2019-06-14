package com.hll_sc_app.base.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hll_sc_app.base.R;
import com.hll_sc_app.base.utils.UIUtils;

public class TipsDialog extends BaseDialog {

    protected TipsDialog(@NonNull Activity context) {
        super(context);
    }

    private TipsDialog(@NonNull Activity context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected TipsDialog(@NonNull Activity context, boolean cancelable,
                         @Nullable DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static Builder newBuilder(Activity context) {
        return new Builder(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.base_dialog_base, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = UIUtils.dip2px(275);
        }
    }

    public void setTitle(String title) {
        TextView titleTxt = mRootView.findViewById(R.id.txt_title);
        titleTxt.setText(title);
    }

    public void setMessage(String msg) {
        TextView msgTxt = mRootView.findViewById(R.id.txt_content);
        msgTxt.setText(msg);
    }

    public void setRootView(View view) {
        mRootView = view;
    }

    public void setButton(final OnClickListener listener, String[] items) {
        if (items.length > 1) {
            mRootView.findViewById(R.id.dialog_verify_v2).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.dialog_verify_v1).setVisibility(View.GONE);
            TextView cancelBtn = mRootView.findViewById(R.id.dialog_cancel);
            cancelBtn.setText(items[0]);
            cancelBtn.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItem(TipsDialog.this, 0);
                }
            });

            TextView verifyBtn = mRootView.findViewById(R.id.dialog_verify);
            verifyBtn.setText(items[1]);
            verifyBtn.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItem(TipsDialog.this, 1);
                }
            });
        } else {
            mRootView.findViewById(R.id.dialog_verify_v2).setVisibility(View.GONE);
            mRootView.findViewById(R.id.dialog_verify_v1).setVisibility(View.VISIBLE);
            TextView verifyBtn = mRootView.findViewById(R.id.dialog_verify_v1);
            verifyBtn.setText(items[0]);
            verifyBtn.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItem(TipsDialog.this, 0);
                }
            });
        }
    }

    public interface OnClickListener {
        void onItem(TipsDialog dialog, int item);
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

        public Builder setButton(OnClickListener listener, String verify) {
            p.mOnClickListener = listener;
            p.items = new String[]{verify};
            return this;
        }

        public Builder setTitle(String title) {
            p.mTitle = title;
            return this;
        }

        public Builder setMessage(String msg) {
            p.mMessage = msg;
            return this;
        }

        public Builder setMessageListener(View.OnClickListener listener) {
            p.mListener = listener;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            p.mCancelable = cancelable;
            return this;
        }

        public TipsDialog create() {
            final TipsDialog dialog = new TipsDialog(p.mContext, R.style.BaseDialog);
            p.apply(dialog);
            dialog.setCancelable(p.mCancelable);
            if (p.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(p.mOnCancelListener);
            dialog.setOnDismissListener(p.mOnDismissListener);
            return dialog;
        }

    }

    static class Params {
        String mTitle;
        String mMessage;
        View.OnClickListener mListener;
        Activity mContext;
        boolean mCancelable;
        DialogInterface.OnCancelListener mOnCancelListener;
        DialogInterface.OnDismissListener mOnDismissListener;
        OnClickListener mOnClickListener;
        String[] items;

        void apply(TipsDialog dialog) {
            dialog.setButton(mOnClickListener, items);
            if (!TextUtils.isEmpty(mTitle)) {
                dialog.setTitle(mTitle);
            }
            if (!TextUtils.isEmpty(mMessage)) {
                dialog.setMessage(mMessage);
            }
        }
    }
}
