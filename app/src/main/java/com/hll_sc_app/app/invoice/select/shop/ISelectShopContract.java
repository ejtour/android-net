package com.hll_sc_app.app.invoice.select.shop;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/12
 */

public interface ISelectShopContract {
    interface ISelectShopView extends ILoadView {
        void setListData(List<PurchaserShopBean> beans, boolean isMore);

        String getSearchWords();

        boolean isShop();
    }

    interface ISelectShopPresenter extends IPresenter<ISelectShopView> {
        void refresh();

        void loadMore();
    }
}
