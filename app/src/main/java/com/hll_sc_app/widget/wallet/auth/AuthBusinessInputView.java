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

public class AuthBusinessInputView extends ConstraintLayout implements IInfoInputView {
    public AuthBusinessInputView(Context context) {
        this(context, null);
    }

    public AuthBusinessInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AuthBusinessInputView(Context context, AttributeSet attrs, int defStyle) {
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
        return "经营信息(4/4)";
    }
}
