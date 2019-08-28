package com.hll_sc_app.app.report.purchase;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.purchase.PurchaseSummaryResp;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/27
 */

public interface IPurchaseSummaryContract {
    interface IPurchaseSummaryView extends ILoadView {
        void setList(PurchaseSummaryResp resp, boolean append);
    }

    interface IPurchaseSummaryPresenter extends IPresenter<IPurchaseSummaryView> {
        void refresh();

        void loadMore();
    }
}
