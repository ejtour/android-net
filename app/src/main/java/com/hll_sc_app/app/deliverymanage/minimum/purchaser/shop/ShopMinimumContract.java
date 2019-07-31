package com.hll_sc_app.app.deliverymanage.minimum.purchaser.shop;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.delivery.ShopMinimumBean;

import java.util.List;

/**
 * 选择合作采购商门店-最小起购量设置
 *
 * @author zhuyingsong
 * @date 2019/7/31
 */
public interface ShopMinimumContract {

    interface IShopMinimumView extends ILoadView {
        /**
         * 获取采购商id
         *
         * @return 采购商 id
         */
        String getPurchaserId();

        /**
         * 展示合作采购商门店列表
         *
         * @param list list
         */
        void showPurchaserShopList(List<ShopMinimumBean> list);
    }

    interface IShopMinimumPresenter extends IPresenter<IShopMinimumView> {
        /**
         * 查询其他分组已经添加的门店
         */
        void querySelectShop();

        /**
         * 获取地区门店列表
         */
        void queryAreaShopList();
    }
}
