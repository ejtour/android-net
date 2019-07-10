package com.hll_sc_app.app.aftersales.audit;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.aftersales.PurchaserListResp;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/9
 */

public interface IAuditActivityContract {
    interface IAuditActivityView extends ILoadView {
        void cachePurchaserShopList(PurchaserListResp resp);
    }

    interface IAuditActivityPresenter extends IPresenter<IAuditActivityView> {
    }
}
