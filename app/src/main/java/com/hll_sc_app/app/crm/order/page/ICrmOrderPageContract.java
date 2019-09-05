package com.hll_sc_app.app.crm.order.page;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.order.shop.OrderShopResp;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/5
 */

public interface ICrmOrderPageContract {
    interface ICrmOrderPageView extends ILoadView {
        void setShopListData(OrderShopResp resp, boolean append);
    }

    interface ICrmOrderPagePresenter extends IPresenter<ICrmOrderPageView> {
        void refresh();

        void loadMore();
    }
}
