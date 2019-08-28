package com.hll_sc_app.app.report.purchase.input;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/28
 */

public interface IPurchaseInputContract {
    interface IPurchaseInputView extends ILoadView {
        void saveSuccess();
    }

    interface IPurchaseInputPresenter extends IPresenter<IPurchaseInputView> {
        /**
         * @param type 0-录入采购金额 1-录入采购物流
         */
        void save(int type, String num, String amount);
    }
}
