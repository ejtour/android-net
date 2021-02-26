package com.hll_sc_app.widget;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hll_sc_app.R;
import com.hll_sc_app.base.dialog.BaseDialog;

public class AlertsDialog extends BaseDialog {
    private TextView mTitle;
    private TextView mContent;
    private TextView mButtonLeft;
    private TextView mButtonRight;

    public AlertsDialog(@NonNull Activity context) {
        super(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View mRootView = inflater.inflate(R.layout.dialog_alert, null);
        mTitle = mRootView.findViewById(R.id.txt_title);
        mContent = mRootView.findViewById(R.id.txt_content);
        mButtonRight = mRootView.findViewById(R.id.txt_right);
        mButtonLeft = mRootView.findViewById(R.id.txt_left);
        setCancelable(false);
        return mRootView;
    }

    public void setClickListener(OnClickListener onClickListener) {
        mButtonLeft.setOnClickListener(v -> {
            onClickListener.setClick(this, 0);
        });
        mButtonRight.setOnClickListener(v -> {
            onClickListener.setClick(this, 1);
        });
    }

    public void setButtonLeftText(String text) {
        if (TextUtils.isEmpty(text)) {
            mButtonLeft.setVisibility(View.GONE);
        } else {
            mButtonLeft.setText(text);
        }
    }

    public void setButtonRightText(String text) {
        if (TextUtils.isEmpty(text)) {
            mButtonRight.setVisibility(View.GONE);
        } else {
            mButtonRight.setText(text);
        }
    }


    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setContent(String content) {
        mContent.setText(content);
    }

    public interface OnClickListener {
        void setClick(Dialog dialog, int index);
    }

    public static class Builder {
        private String title;
        private String content;
        private OnClickListener onClickListener;
        private Activity activity;
        private String buttonLeft;
        private String buttonRight;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder addClickListener(OnClickListener onClickListener, String buttonLeft, String buttonRight) {
            this.onClickListener = onClickListener;
            this.buttonLeft = buttonLeft;
            this.buttonRight = buttonRight;
            return this;
        }

        public AlertsDialog create() {
            AlertsDialog alertsDialog = new AlertsDialog(activity);
            alertsDialog.setTitle(title);
            alertsDialog.setContent(content);
            alertsDialog.setClickListener(onClickListener);
            alertsDialog.setButtonLeftText(buttonLeft);
            alertsDialog.setButtonRightText(buttonRight);
            return alertsDialog;
        }
    }
}
