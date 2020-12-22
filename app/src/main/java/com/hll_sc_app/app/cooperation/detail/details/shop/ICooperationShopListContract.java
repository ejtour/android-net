package com.hll_sc_app.app.cooperation.detail.details.shop;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 12/15/20.
 */
interface ICooperationShopListContract {
    interface ICooperationShopListView extends ILoadView {
        void success();
    }

    interface ICooperationShopListPresenter extends IPresenter<ICooperationShopListView> {
        void agree(PurchaserShopBean bean);
    }
}
