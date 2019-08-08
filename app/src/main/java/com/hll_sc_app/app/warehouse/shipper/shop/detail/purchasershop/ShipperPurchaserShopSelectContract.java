package com.hll_sc_app.app.warehouse.shipper.shop.detail.purchasershop;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.warehouse.ShipperShopResp;

import java.util.List;

/**
 * 选择合作采购商-选择门店
 *
 * @author zhuyingsong
 * @date 2019/8/8
 */
public interface ShipperPurchaserShopSelectContract {

    interface IShopListView extends ILoadView {
        /**
         * 展示合作采购商门店列表
         *
         * @param list   list
         * @param append true-追加
         * @param total  总数量
         */
        void showShopList(List<ShipperShopResp.ShopBean> list, boolean append, int total);

        /**
         * 获取搜索词
         *
         * @return 搜索词
         */
        String getSearchParam();

        /**
         * 获取采购商Id
         *
         * @return 采购商Id
         */
        String getPurchaserId();
    }

    interface IShopListPresenter extends IPresenter<IShopListView> {
        /**
         * 查询合作采购商门店列表
         *
         * @param showLoading 展示加载对话框
         */
        void queryShopList(boolean showLoading);

        /**
         * 查询更多合作采购商门店列表
         */
        void queryMoreShopList();
    }
}
