package com.hll_sc_app.app.goods.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.GoodsBean;

/**
 * 商品详情
 *
 * @author zhuyingsong
 * @date 2019/6/13
 */
public interface GoodsDetailContract {

    interface IGoodsDetailView extends ILoadView {
        /**
         * 显示商品详情
         *
         * @param bean GoodsBean
         */
        void showDetail(GoodsBean bean);
    }

    interface IGoodsDetailPresenter extends IPresenter<IGoodsDetailView> {
        /**
         * 查询商品详情
         *
         * @param productID 商品 ID
         */
        void queryGoodsDetail(String productID);
    }
}
