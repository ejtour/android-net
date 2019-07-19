package com.hll_sc_app.app.cooperation.detail.shopdetail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.cooperation.DeliveryBean;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;

/**
 * 合作采购商详情-门店详情
 *
 * @author zhuyingsong
 * @date 2019/7/19
 */
public interface CooperationShopDetailContract {

    interface ICooperationShopDeliveryView extends ILoadView {
        /**
         * 展示支持的配送方式
         *
         * @param bean 配送方式
         */
        void showDeliveryList(DeliveryBean bean);

        /**
         * 修改成功
         */
        void editSuccess();
    }

    interface ICooperationShopDeliveryPresenter extends IPresenter<ICooperationShopDeliveryView> {
        /**
         * 查询配送方式
         */
        void queryDeliveryList();

        /**
         * 批量修改配送方式
         *
         * @param req req
         */
        void editShopDelivery(ShopSettlementReq req);
    }
}
