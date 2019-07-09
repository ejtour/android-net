package com.hll_sc_app.app.agreementprice.quotation.add.purchaser.shop;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;

import java.util.List;

/**
 * 选择合作采购商
 *
 * @author zhuyingsong
 * @date 2019/7/9
 */
public interface PurchaserShopListContract {

    interface IPurchaserListView extends ILoadView {
        /**
         * 展示合作采购商门店列表
         *
         * @param list list
         */
        void showPurchaserShopList(List<PurchaserShopBean> list);
    }

    interface IPurchaserListPresenter extends IPresenter<IPurchaserListView> {
        /**
         * 查询合作采购商列表
         *
         * @param purchaserId 采购商 Id
         * @param searchParam 搜索词
         */
        void queryPurchaserShopList(String purchaserId, String searchParam);
    }
}
