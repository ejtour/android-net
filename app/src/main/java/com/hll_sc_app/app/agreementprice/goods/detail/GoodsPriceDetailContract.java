package com.hll_sc_app.app.agreementprice.goods.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.PurchaserBean;

import java.util.List;

/**
 * 协议价管理-商品详情
 *
 * @author zhuyingsong
 * @date 2019/7/11
 */
public interface GoodsPriceDetailContract {

    interface IGoodsPriceDetailView extends ILoadView {
        /**
         * 显示报价商品列表
         *
         * @param list 报价商品列表数据
         */
        void showPurchaserList(List<PurchaserBean> list);
    }

    interface IGoodsPriceDetailPresenter extends IPresenter<IGoodsPriceDetailView> {
        /**
         * 获取商品详情
         *
         * @param productSpecId 规格 Id
         */
        void queryPriceUsePurchaser(String productSpecId);

        /**
         * 获取商品详情内容
         *
         * @return 商品详情
         */
        List<PurchaserBean> getPriceGoodsList();
    }
}
