package com.hll_sc_app.app.wallet;

import android.os.Bundle;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.wallet.WalletStatusResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Wallet;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/27
 */

public class WalletPresenter implements IWalletContract.IWalletPresenter {
    private IWalletContract.IWalletView mView;

    private WalletPresenter() {
    }

    public static WalletPresenter newInstance() {
        return new WalletPresenter();
    }

    @Override
    public void start() {
        Wallet.queryWalletStatus(new SimpleObserver<WalletStatusResp>(mView) {
            @Override
            public void onSuccess(WalletStatusResp resp) {
                mView.handleWalletStatus(resp);
            }
        });
    }

    @Override
    public void register(IWalletContract.IWalletView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
