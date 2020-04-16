package com.hll_sc_app.widget.bill;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.aftersales.ModifyUnitPriceDialog;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/9
 */

public class ModifyAmountDialog extends ModifyUnitPriceDialog {
    public ModifyAmountDialog(@NonNull Activity context) {
        super(context);
        mTitle.setText("调整金额");
        mProductSpec.setText("调整为：");
    }

    @Override
    public ModifyAmountDialog setRawPrice(double price) {
        mProductName.setText(String.format("应收金额：¥%s", CommonUtils.formatMoney(price)));
        return this;
    }

    @Override
    protected String getEmptyTip() {
        return "请输入金额";
    }
}
