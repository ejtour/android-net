package com.hll_sc_app.app.wallet.recharge;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.wallet.RechargeResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Wallet;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/29
 */

public class RechargePresenter implements IRechargeContract.IRechargePresenter {
    private IRechargeContract.IRechargeView mView;
    private String settleUnitID;

    public static RechargePresenter newInstance(String settleUnitID) {
        RechargePresenter presenter = new RechargePresenter();
        presenter.settleUnitID = settleUnitID;
        return presenter;
    }

    @Override
    public void register(IRechargeContract.IRechargeView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void recharge(double money) {
        Wallet.recharge(money, settleUnitID, new SimpleObserver<RechargeResp>(mView) {
            @Override
            public void onSuccess(RechargeResp rechargeResp) {
                mView.rechargeSuccess(rechargeResp);
            }
        });
    }
}
