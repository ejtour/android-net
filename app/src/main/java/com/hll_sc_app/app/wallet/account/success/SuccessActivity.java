package com.hll_sc_app.app.wallet.account.success;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/11/11
 */

@Route(path = RouterConfig.WALLET_ACCOUNT_SUCCESS)
public class SuccessActivity extends BaseActivity {
    @BindView(R.id.oas_title_bar)
    TitleBar mTitleBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.fragment_wallet_open_account_success);
        ButterKnife.bind(this);
        mTitleBar.setLeftBtnClick(v -> onBackPressed());
    }

    @OnClick(R.id.txt_ok)
    @Override
    public void onBackPressed() {
        RouterUtil.goToActivity(RouterConfig.WALLET);
    }
}
