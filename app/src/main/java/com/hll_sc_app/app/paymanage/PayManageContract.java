package com.hll_sc_app.app.paymanage;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.delivery.DeliveryBean;

/**
 * 支付管理
 *
 * @author zhuyingsong
 * @date 2019/8/8
 */
public interface PayManageContract {

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
         * 修改配送方式
         *
         * @param actionType  操作类型（insert-添加配送方式，delete-删除配送方式
         * @param deliveryWay 配送方式 1-自有物流配送 2-上门自提 3-第三方配送公司
         */
        void editDeliveryType(String actionType, String deliveryWay);
    }
}
