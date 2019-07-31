package com.hll_sc_app.app.wallet.withdraw;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Wallet;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/30
 */

public class WithdrawPresenter implements IWithdrawContract.IWithdrawPresenter {
    private String mSettleUnitID;
    private IWithdrawContract.IWithdrawView mView;

    public static WithdrawPresenter newInstance(String settleUnitID) {
        WithdrawPresenter presenter = new WithdrawPresenter();
        presenter.mSettleUnitID = settleUnitID;
        return presenter;
    }

    private WithdrawPresenter() {
    }

    @Override
    public void withdraw(double money) {
        Wallet.withdraw(money, mSettleUnitID, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.withdrawSuccess();
            }
        });
    }

    @Override
    public void register(IWithdrawContract.IWithdrawView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
