package com.hll_sc_app.app.deliverymanage.deliverytype;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;
import com.hll_sc_app.bean.delivery.DeliveryBean;

/**
 * 配送方式设置
 *
 * @author zhuyingsong
 * @date 2019/7/27
 */
public interface DeliveryTypeSetContract {

    interface IDeliveryTypeSetView extends ILoadView {
        /**
         * 展示选中的配送方式
         *
         * @param bean 配送方式
         */
        void showDeliveryList(DeliveryBean bean);

        /**
         * 修改成功
         */
        void editSuccess();
    }

    interface IDeliveryTypeSetPresenter extends IPresenter<IDeliveryTypeSetView> {
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
