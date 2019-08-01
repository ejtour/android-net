package com.hll_sc_app.widget.wallet.auth;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

import com.hll_sc_app.app.wallet.account.IInfoInputView;
import com.hll_sc_app.bean.wallet.AuthInfo;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/1
 */

public class AuthSettlementInputView extends ConstraintLayout implements IInfoInputView {
    public AuthSettlementInputView(Context context) {
        this(context, null);
    }

    public AuthSettlementInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AuthSettlementInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void initData(AuthInfo info) {

    }

    @Override
    public void setImageUrl(String url) {

    }

    @Override
    public String getTitle() {
        return "结算信息(3/4)";
    }
}
