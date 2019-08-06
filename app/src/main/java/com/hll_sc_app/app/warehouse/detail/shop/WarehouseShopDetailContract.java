package com.hll_sc_app.app.warehouse.detail.shop;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.warehouse.ShopParameterBean;

/**
 * 代仓货主门店详情
 *
 * @author zhuyingsong
 * @date 2019/8/5
 */
public interface WarehouseShopDetailContract {

    interface IWarehouseShopDetailView extends ILoadView {
        /**
         * 展示代仓支付设置
         *
         * @param bean bean
         */
        void showDetail(ShopParameterBean bean);

        /**
         * 获取门店ids
         *
         * @return 门店Ids
         */
        String getShopIds();

        /**
         * 获取采购商id
         *
         * @return 采购商id
         */
        String getPurchaserId();
    }

    interface IWarehouseShopDetailPresenter extends IPresenter<IWarehouseShopDetailView> {
        /**
         * 查询代仓门店
         */
        void queryWarehouseShop();

        /**
         * 编辑代仓门店
         */
        void editWarehouseShop();
    }
}
