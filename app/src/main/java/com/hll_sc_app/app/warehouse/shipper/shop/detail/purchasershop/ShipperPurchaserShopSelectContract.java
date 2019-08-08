package com.hll_sc_app.app.warehouse.shipper.shop.detail.purchasershop;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;

import java.util.List;

/**
 * 选择合作采购商-选择门店
 *
 * @author zhuyingsong
 * @date 2019/8/8
 */
public interface ShipperPurchaserShopSelectContract {

    interface IPurchaserListView extends ILoadView {
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

    interface IPurchaserListPresenter extends IPresenter<IPurchaserListView> {
        /**
         * 查询合作采购商列表
         *
         * @param purchaserId 采购商 Id
         */
        void queryWarehousePurchaserShopList(String purchaserId);

        /**
         * 获取代仓签约详情
         *
         * @param purchaserId 代仓集团 Id
         */
        void queryCooperationWarehouseDetail(String purchaserId);
    }
}
