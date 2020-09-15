package com.hll_sc_app.app.marketingsetting.product.goodslist;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/9/10
 */
public interface IGoodsListContract {
    interface IGoodsListView extends ILoadView {
        void success();
    }

    interface IGoodsListPresenter extends IPresenter<IGoodsListView> {
        void del(String discountID, String productID);
    }
}
