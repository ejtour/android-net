package com.hll_sc_app.app.blacklist.cooperation.shop;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;

import java.util.List;

public interface ICooperationShopContract {

    interface ICooperationShopView extends ILoadView {
        /**
         * 展示合作采购商门店列表
         *
         * @param list list
         */
        void showPurchaserShopList(List<PurchaserShopBean> list);

        /**
         * 获取搜索词
         *
         * @return 搜索词
         */
        String getSearchParam();
    }

    interface ICooperationShopPresenter extends IPresenter<ICooperationShopContract.ICooperationShopView> {
        /**
         * 查询合作采购商列表
         *
         * @param purchaserId 采购商 Id
         */
        void queryPurchaserShopList(String purchaserId);
    }
}
