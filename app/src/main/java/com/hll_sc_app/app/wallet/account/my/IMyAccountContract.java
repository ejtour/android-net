package com.hll_sc_app.app.wallet.account.my;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.wallet.WalletInfo;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/30
 */

public interface IMyAccountContract {
    interface IMyAccountView extends ILoadView{
        void handleWalletInfo(WalletInfo resp);
    }

    interface IMyAccountPresenter extends IPresenter<IMyAccountView>{
    }
}
