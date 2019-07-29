package com.hll_sc_app.app.wallet;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.wallet.WalletStatusResp;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/27
 */

public interface IWalletContract {
    interface IWalletView extends ILoadView {
        void handleWalletStatus(WalletStatusResp resp);
    }

    interface IWalletPresenter extends IPresenter<IWalletView> {
    }
}
