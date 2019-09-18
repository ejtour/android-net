package com.hll_sc_app.app.staffmanage.detail.depart;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.utils.UIUtils;

public class InputDialog extends BaseDialog {
    private EditText mEdtName;
    private TextView mTxtLeft;
    private TextView mTxtRight;
    private TextView mTitle;
    private InputDialogConfig mConfig;

    public InputDialog(@NonNull Activity context, InputDialogConfig config) {
        super(context);
        mConfig = config;
        initView();
    }

    public void initView() {
        mTitle.setText(mConfig.getTitle());
        mEdtName.setHint(mConfig.getHint());
        mEdtName.setText(mConfig.getValue());
        if (mConfig.getMaxInputLength() > 0) {
            mEdtName.setFilters(new InputFilter[]{
                    new InputFilter.LengthFilter(mConfig.getMaxInputLength())
            });
        }
        mTxtLeft.setOnClickListener(v -> {
            mConfig.click(this, mEdtName.getText().toString(), 0);
        });
        mTxtLeft.setText(mConfig.getLeftButtonText());
        mTxtRight.setOnClickListener(v -> {
            mConfig.click(this, mEdtName.getText().toString(), 1);
        });
        mTxtRight.setText(mConfig.getRightButtonText());

    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.dialog_depart_add, null);
        mEdtName = root.findViewById(R.id.edt_name);
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
            attributes.height = UIUtils.dip2px(230);
            attributes.gravity = Gravity.CENTER;
            window.setAttributes(attributes);
            window.setBackgroundDrawableResource(R.drawable.base_bg_white_radius_5_solid);
        }
        setCancelable(false);
    }


    public interface InputDialogConfig {
        String getTitle();

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
            return "容我再想想";
        }

        default String getRightButtonText() {
            return "确认";
        }

    }
}
