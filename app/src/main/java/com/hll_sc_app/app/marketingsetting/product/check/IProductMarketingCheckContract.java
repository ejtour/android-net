package com.hll_sc_app.app.marketingsetting.product.check;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.bean.marketingsetting.MarketingDetailCheckResp;

/**
 * 商品促销查看页面
 */
public interface IProductMarketingCheckContract {
    interface IView extends ILoadView {
        String getDiscountId();

        void showDetai(MarketingDetailCheckResp resp);
    }

    interface IPresenter extends com.hll_sc_app.base.IPresenter<IView> {
        void getMarketingDetail();
    }
}
