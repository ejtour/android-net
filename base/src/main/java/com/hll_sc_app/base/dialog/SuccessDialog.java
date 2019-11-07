package com.hll_sc_app.base.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hll_sc_app.base.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.citymall.App;

/**
 * Dialog显示
 *
 * @author zhuyingsong
 * @date 2018/12/18
 */
public class SuccessDialog extends BaseDialog {

    protected SuccessDialog(@NonNull Activity context) {
        super(context);
    }

    private SuccessDialog(@NonNull Activity context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected SuccessDialog(@NonNull Activity context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static Builder newBuilder(Activity context) {
        return new Builder(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.base_dialog_success_layout, null);
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

    private void setImageTitle(int resId) {
        ImageView imageView = mRootView.findViewById(R.id.img_title);
        if (resId == 0) {
            imageView.setVisibility(View.GONE);
            return;
        }
        imageView.setImageResource(resId);
    }

    private void setImageState(int resId) {
        ImageView imageView = mRootView.findViewById(R.id.img_state);
        imageView.setImageResource(resId);
    }

    private void setMessageTitle(String messageTitle) {
        TextView msgTxt = mRootView.findViewById(R.id.txt_message_title);
        msgTxt.setText(messageTitle);
    }

    public void setMessage(CharSequence message, View.OnClickListener clickListener) {
        setMessage(message);
        TextView msgTxt = mRootView.findViewById(R.id.txt_message);
        msgTxt.setOnClickListener(clickListener);
    }

    public void setMessage(CharSequence message) {
        TextView msgTxt = mRootView.findViewById(R.id.txt_message);
        if (TextUtils.isEmpty(message)) {
            msgTxt.setVisibility(View.INVISIBLE);
        } else {
            msgTxt.setVisibility(View.VISIBLE);
        }
        msgTxt.setText(message);
    }

    private void setContent(String mContent) {
        TextView msgTxt = mRootView.findViewById(R.id.txt_content);
        msgTxt.setVisibility(View.VISIBLE);
        msgTxt.setText(mContent);
    }

    private void setButton(final OnClickListener listener, String[] items) {
        if (items.length > 1) {
            mRootView.findViewById(R.id.ll_button).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.ll_button_one).setVisibility(View.GONE);
            TextView txtCancel = mRootView.findViewById(R.id.txt_cancel);
            txtCancel.setText(items[0]);
            txtCancel.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItem(SuccessDialog.this, 0);
                }
            });

            TextView txtConfirm = mRootView.findViewById(R.id.txt_confirm);
            txtConfirm.setText(items[1]);
            txtConfirm.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItem(SuccessDialog.this, 1);
                }
            });
        } else {
            mRootView.findViewById(R.id.ll_button_one).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.ll_button).setVisibility(View.GONE);
            TextView txtFinish = mRootView.findViewById(R.id.txt_finish);
            txtFinish.setText(items[0]);
            txtFinish.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItem(SuccessDialog.this, 0);
                }
            });
        }
    }

    public interface OnClickListener {
        /**
         * 点击监听
         *
         * @param dialog dialog
         * @param item   item
         */
        void onItem(SuccessDialog dialog, int item);
    }

    public static class Builder {
        private final Params P;

        private Builder(Activity context) {
            P = new Params();
            P.mContext = context;
            P.mCancelable = true;
        }

        /**
         * ic_dialog_failure
         * ic_dialog_success
         *
         * @param resId resId
         * @return Builder
         */
        public Builder setImageTitle(int resId) {
            P.mImgTitle = resId;
            return this;
        }

        /**
         * ic_dialog_state_success
         * ic_dialog_state_failure
         *
         * @param resId resId
         * @return Builder
         */
        public Builder setImageState(int resId) {
            P.mImgState = resId;
            return this;
        }

        public Builder setMessageTitle(String msg) {
            P.mMessageTitle = msg;
            return this;
        }

        public Builder setMessage(CharSequence msg) {
            P.mMessage = msg;
            return this;
        }

        public Builder setContent(String content) {
            P.mContent = content;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }

        public Builder setButton(OnClickListener listener, String cancel, String verify) {
            P.mOnClickListener = listener;
            P.items = new String[]{cancel, verify};
            return this;
        }

        public Builder setButton(OnClickListener listener, String verify) {
            P.mOnClickListener = listener;
            P.items = new String[]{verify};
            return this;
        }

        public Builder setMessage(CharSequence msg, View.OnClickListener clickListener) {
            P.mMessage = msg;
            P.mMsgOnClickListener = clickListener;
            return this;
        }

        public SuccessDialog create() {
            final SuccessDialog dialog = new SuccessDialog(P.mContext, R.style.BaseDialog);
            P.apply(dialog);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            Window window = dialog.getWindow();
            if (window != null) {
                WindowManager.LayoutParams params = window.getAttributes();
                params.gravity = Gravity.TOP;
                params.y = (int) (UIUtils.getScreenHeight(App.INSTANCE) * 0.08);
                window.setAttributes(params);
            }
            return dialog;
        }
    }

    static class Params {
        int mImgTitle;
        int mImgState;
        String mMessageTitle;
        CharSequence mMessage;
        String mContent;
        Activity mContext;
        boolean mCancelable;
        OnCancelListener mOnCancelListener;
        OnDismissListener mOnDismissListener;
        OnClickListener mOnClickListener;
        View.OnClickListener mMsgOnClickListener;
        String[] items;

        void apply(SuccessDialog dialog) {
            dialog.setButton(mOnClickListener, items);
            dialog.setImageTitle(mImgTitle);
            dialog.setImageState(mImgState);
            dialog.setMessage(mMessage);
            dialog.setMessageTitle(mMessageTitle);
            if (mMsgOnClickListener != null) {
                dialog.setMessage(mMessage, mMsgOnClickListener);
            }
            if (!TextUtils.isEmpty(mContent)) {
                dialog.setContent(mContent);
            }
        }
    }


}
