package com.hll_sc_app.widget.wallet;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.citymall.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/29
 */

public class RechargeDialog extends BaseDialog {

    @BindView(R.id.dr_money)
    TextView mMoney;
    @BindView(R.id.dr_bank_card)
    TextView mBankCard;

    public RechargeDialog(@NonNull Activity context, View.OnClickListener listener) {
        super(context);
        mBankCard.setOnClickListener(listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.BasePopupAnimation);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.height = UIUtils.dip2px(400);
            attributes.gravity = Gravity.BOTTOM;
            window.setAttributes(attributes);
            window.setBackgroundDrawableResource(android.R.color.white);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_recharge, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.dr_close)
    public void close() {
        dismiss();
    }

    public void show(double money) {
        mBankCard.setTag(money);
        mMoney.setText(String.format("¥ %s", CommonUtils.formatMoney(money)));
        super.show();
    }
}
