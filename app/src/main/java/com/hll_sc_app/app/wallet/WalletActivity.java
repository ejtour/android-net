package com.hll_sc_app.app.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.wallet.recharge.RechargeActivity;
import com.hll_sc_app.app.wallet.status.NoneFragment;
import com.hll_sc_app.app.wallet.status.NormalFragment;
import com.hll_sc_app.app.wallet.status.VerifyFragment;
import com.hll_sc_app.app.wallet.withdraw.WithdrawActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.wallet.WalletStatusResp;
import com.hll_sc_app.widget.EmptyView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/27
 */
@Route(path = RouterConfig.WALLET)
public class WalletActivity extends BaseLoadActivity implements IWalletContract.IWalletView {
    @BindView(R.id.aw_container)
    FrameLayout mContainer;
    @BindView(R.id.aw_empty_view)
    EmptyView mEmptyView;
    private Unbinder unbinder;
    private WalletPresenter mPresenter;

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        unbinder = ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        mPresenter = WalletPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        mEmptyView.setNetError();
        mEmptyView.setOnActionClickListener(mPresenter::start);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void handleWalletStatus(WalletStatusResp resp) {
        mEmptyView.setVisibility(View.GONE);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (resp.getStatus()) {
            case WalletStatusResp.STATUS_NOT_OPEN:
                transaction.replace(R.id.aw_container, NoneFragment.newInstance()).commitAllowingStateLoss();
                break;
            case WalletStatusResp.STATUS_VERIFYING:
            case WalletStatusResp.STATUS_VERIFY_SUCCESS:
            case WalletStatusResp.STATUS_VERIFY_FAIL:
                transaction.replace(R.id.aw_container, VerifyFragment.newInstance(resp.getStatus())).commitAllowingStateLoss();
                break;
            case WalletStatusResp.STATUS_AUTH_SUCCESS:
                Fragment fragment = manager.findFragmentById(R.id.aw_container);
                if (fragment instanceof NormalFragment) {
                    NormalFragment normalFragment = (NormalFragment) fragment;
                    normalFragment.refreshData(resp);
                } else
                    transaction.replace(R.id.aw_container, NormalFragment.newInstance(resp)).commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK &&
                (requestCode == RechargeActivity.REQ_CODE || requestCode == WithdrawActivity.REQ_CODE)) {
            mPresenter.start();
        }
    }
}
