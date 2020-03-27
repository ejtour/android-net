package com.hll_sc_app.app.wallet.create;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 开通企业钱包
 * 激活状态走这里
 */
@Route(path = RouterConfig.ACTIVTY_WALLET_CREATE_ACCOUNT, extras = Constant.LOGIN_EXTRA)
public class CreateAccountActivity extends BaseLoadActivity implements ICreateAccountContract.IView {
    @BindView(R.id.edt_company_name)
    EditText mEdtCompanyName;
    @BindView(R.id.txt_next)
    TextView mNext;

    private Unbinder unbinder;
    private ICreateAccountContract.IPresent mPresent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_open_protol);
        StatusBarCompat.setStatusBarColor(this, 0xFFFFFFFF);
        unbinder = ButterKnife.bind(this);
        mPresent = CreateAccountPresent.newInstance();
        mPresent.register(this);
        showProtocolDialog();
        mNext.setOnClickListener(v -> {
            mPresent.createAccount();
        });
        mEdtCompanyName.setText(GreenDaoUtils.getUser().getGroupName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }



    private void showProtocolDialog() {
        ProtocolDialog protocolDialog = new ProtocolDialog(this);
        protocolDialog.show();
    }

    @Override
    public String getGroupName() {
        return mEdtCompanyName.getText().toString();
    }

    @Override
    public void createSuccess() {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_WALLET_AUTHEN_ACCOUNT);
    }
}
