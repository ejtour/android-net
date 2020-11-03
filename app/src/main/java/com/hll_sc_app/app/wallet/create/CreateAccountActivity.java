package com.hll_sc_app.app.wallet.create;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.widget.wallet.WalletProtocolDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

/**
 * 开通企业钱包
 * 激活状态走这里
 */
@Route(path = RouterConfig.ACTIVTY_WALLET_CREATE_ACCOUNT, extras = Constant.LOGIN_EXTRA)
public class CreateAccountActivity extends BaseLoadActivity {
    @BindView(R.id.edt_company_name)
    EditText mEdtCompanyName;
    @BindView(R.id.txt_next)
    TextView mNext;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_open_protol);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        unbinder = ButterKnife.bind(this);
        mEdtCompanyName.setText(GreenDaoUtils.getUser().getGroupName());
        new WalletProtocolDialog(this).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public String getGroupName() {
        return mEdtCompanyName.getText().toString().trim();
    }

    @OnTextChanged(R.id.edt_company_name)
    void onTextChanged(CharSequence s) {
        mNext.setEnabled(s.length() > 0);
    }

    @OnClick(R.id.txt_next)
    void createSuccess() {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_WALLET_AUTHEN_ACCOUNT, getGroupName());
    }
}
