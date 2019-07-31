package com.hll_sc_app.app.wallet.withdraw;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/30
 */

public interface IWithdrawContract {
    interface IWithdrawView extends ILoadView {
        void withdrawSuccess();
    }

    interface IWithdrawPresenter extends IPresenter<IWithdrawView> {
        void withdraw(double money);
    }
}
