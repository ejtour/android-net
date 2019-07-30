package com.hll_sc_app.app.wallet.recharge;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.wallet.RechargeResp;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/29
 */

public interface IRechargeContract {
    interface IRechargeView extends ILoadView {
        void rechargeSuccess(RechargeResp rechargeResp);
    }

    interface IRechargePresenter extends IPresenter<IRechargeView> {
        void recharge(double money);
    }
}
