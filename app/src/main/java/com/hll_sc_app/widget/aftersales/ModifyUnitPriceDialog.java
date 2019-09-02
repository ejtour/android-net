package com.hll_sc_app.widget.aftersales;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/5/22
 */

public class ModifyUnitPriceDialog extends BaseDialog {
    @BindView(R.id.mup_product_name)
    TextView mProductName;
    @BindView(R.id.mup_product_spec)
    TextView mProductSpec;
    @BindView(R.id.mup_edit_new_price)
    EditText mEditNewPrice;
    @BindView(R.id.mup_raw_price)
    TextView mRawPrice;
    private ModifyCallback mCallback;

    public interface ModifyCallback {
        void modify(String price);
    }

    public ModifyUnitPriceDialog(@NonNull Activity context) {
        super(context);
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getWindow() != null) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            getWindow().getAttributes().width = UIUtils.getScreenWidth(getContext()) - UIUtils.dip2px(110);
            getWindow().setBackgroundDrawableResource(R.drawable.base_bg_white_radius_5_solid);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = View.inflate(getContext(), R.layout.dialog_modify_unit_price, null);
        ButterKnife.bind(this, view);
        return view;
    }

    public ModifyUnitPriceDialog setProductName(String productName) {
        mProductName.setText(productName);
        return this;
    }

    public ModifyUnitPriceDialog setProductSpec(String spec) {
        mProductSpec.setText(String.format("规格：%s", spec));
        return this;
    }

    public ModifyUnitPriceDialog setRawPrice(double price) {
        mRawPrice.setText(String.format("原价：¥%s", CommonUtils.formatMoney(price)));
        mEditNewPrice.setText(CommonUtils.formatNumber(price));
        return this;
    }

    public ModifyUnitPriceDialog setModifyCallback(ModifyCallback callback) {
        mCallback = callback;
        return this;
    }

    @OnTextChanged(value = R.id.mup_edit_new_price, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged(Editable s) {
        Utils.processMoney(s, false);
    }

    @OnClick(R.id.mup_cancel)
    public void cancel() {
        dismiss();
    }

    @OnClick(R.id.mup_ok)
    public void ok() {
        if (TextUtils.isEmpty(mEditNewPrice.getText().toString())) {
            ToastUtils.showShort(getContext(), "请输入新单价");
            return;
        }
        dismiss();
        mCallback.modify(mEditNewPrice.getText().toString());
    }
}
