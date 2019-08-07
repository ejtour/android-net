package com.hll_sc_app.app.warehouse.shipper.shop.detail.purchaser;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.warehouse.ShipperShopResp;

import java.util.List;

/**
 * 选择合作采购商
 *
 * @author zhuyingsong
 * @date 2019/8/7
 */
public interface ShipperPurchaserSelectContract {

    interface IPurchaserSelectView extends ILoadView {
        /**
         * 展示合作采购商列表
         *
         * @param list     list
         * @param append   true-追加
         * @param totalNum 总数
         */
        void showPurchaserList(List<ShipperShopResp.ShopBean> list, boolean append, int totalNum);

        /**
         * 获取搜索词
         *
         * @return 搜索词
         */
        String getSearchParam();
    }

    interface IPurchaserSelectPresenter extends IPresenter<IPurchaserSelectView> {
        /**
         * 查询合作采购商集团
         *
         * @param showLoading 展示 loading
         */
        void queryPurchaserList(boolean showLoading);

        /**
         * 查询更多合作采购商集团
         */
        void queryMorePurchaserList();
    }
}
