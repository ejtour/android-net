package com.hll_sc_app.widget.order;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hll_sc_app.R;
import com.hll_sc_app.base.dialog.BaseDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/3
 */

public class ModifyUnitDialog extends BaseDialog {
    @BindView(R.id.dmu_auxiliary_unit)
    RadioButton mAuxiliaryUnit;
    @BindView(R.id.dmu_sale_unit)
    RadioButton mSaleUnit;
    @BindView(R.id.dmu_radio_group)
    RadioGroup mRadioGroup;
    private OnModifyUnitListener mListener;

    public static ModifyUnitDialog create(Activity context, String auxiliaryUnit, String saleUnit, String selectUnit, OnModifyUnitListener listener) {
        ModifyUnitDialog dialog = new ModifyUnitDialog(context);
        dialog.mAuxiliaryUnit.setText(auxiliaryUnit);
        dialog.mSaleUnit.setText(saleUnit);
        dialog.mListener = listener;
        if (auxiliaryUnit.equals(selectUnit)) dialog.mAuxiliaryUnit.setChecked(true);
        else dialog.mSaleUnit.setChecked(true);
        return dialog;
    }

    private ModifyUnitDialog(@NonNull Activity context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
    }

    private void initWindow() {
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.BasePopupAnimation);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.gravity = Gravity.BOTTOM;
            window.setAttributes(attributes);
            window.setBackgroundDrawableResource(android.R.color.white);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_modify_unit, null);
        ButterKnife.bind(this, view);
        mRadioGroup.setOnCheckedChangeListener(this::onCheckedChanged);
        return view;
    }

    public interface OnModifyUnitListener {
        void onUnitChanged(String unit);
    }

    @OnClick(R.id.dmu_close)
    public void close() {
        dismiss();
    }

    private void onCheckedChanged(RadioGroup group, int checkedId) {
        if (mListener == null || !isShowing()) {
            return;
        }
        dismiss();
        switch (checkedId) {
            case R.id.dmu_auxiliary_unit:
                mListener.onUnitChanged(mAuxiliaryUnit.getText().toString());
                break;
            case R.id.dmu_sale_unit:
                mListener.onUnitChanged(mSaleUnit.getText().toString());
                break;
        }
    }
}
