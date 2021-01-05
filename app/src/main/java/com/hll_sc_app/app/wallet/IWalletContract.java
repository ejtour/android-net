package com.hll_sc_app.app.wallet;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.wallet.WalletInfo;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/27
 */

public interface IWalletContract {
    public String WALLET_STATUS = "status";

    interface IView extends ILoadView {
        /**
         * 获取结算主体信息
         */
        void getInfoSuccess(WalletInfo info);

    }

    interface IPresent extends IPresenter<IView> {
        /**
         * 获取钱包账号状态
         */
        void getWalletInfo(boolean isShowLoading);

        void rechargeReport(String docID,String settleUnitID);
    }


}