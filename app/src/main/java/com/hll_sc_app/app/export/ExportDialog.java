package com.hll_sc_app.app.export;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hll_sc_app.R;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.utils.UIUtils;

public class ExportDialog extends BaseDialog {

    private TextView mTxtLeft;
    private TextView mTxtRight;
    private TextView mTitle;

    private InputDialogConfig mConfig;

    public ExportDialog(@NonNull Activity context, InputDialogConfig config) {
        super(context);
        mConfig = config;
        initView();
    }

    public void initView() {
        if (!TextUtils.isEmpty(mConfig.getCharSequenceTitle())) {
            mTitle.setText(mConfig.getCharSequenceTitle());
        } else {
            mTitle.setText(mConfig.getTitle());
        }
        mTxtLeft.setOnClickListener(v -> {
            mConfig.click(this, "", 0);
        });
        mTxtLeft.setText(mConfig.getLeftButtonText());
        mTxtRight.setOnClickListener(v -> {
            mConfig.click(this, "", 1);
        });
        mTxtRight.setText(mConfig.getRightButtonText());

        mConfig.setTitleGravity((ConstraintLayout.LayoutParams) mTitle.getLayoutParams());
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.dialog_export_way, null);
        mTxtLeft = root.findViewById(R.id.txt_left);
        mTxtRight = root.findViewById(R.id.txt_right);
        mTitle = root.findViewById(R.id.txt_title);
        return root;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.BasePopupAnimation);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.height = UIUtils.dip2px(130);
            attributes.gravity = Gravity.CENTER;
            window.setAttributes(attributes);
            window.setBackgroundDrawableResource(R.drawable.base_bg_white_radius_5_solid);
        }
        setCancelable(false);
    }


    public interface InputDialogConfig {
        default String getTitle() {
            return "";
        }

        default CharSequence getCharSequenceTitle() {
            return "";
        }

        default String getValue() {
            return "";
        }

        default String getHint() {
            return null;
        }

        default int getMaxInputLength() {
            return -1;
        }

        void click(BaseDialog dialog, String content, int index);

        default String getLeftButtonText() {
            return "直接下载";
        }

        default String getRightButtonText() {
            return "发送到邮箱";
        }

        default @DrawableRes
        int getEdtBackgroundRes() {
            return R.drawable.bg_gray_solid_radius_5;
        }

        default boolean isEdtFill() {
            return false;
        }

        default void setTitleGravity(ConstraintLayout.LayoutParams layoutParams) {

        }
    }
}
