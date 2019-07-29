package com.hll_sc_app.app.deliverymanage.ageing;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.delivery.DeliveryPeriodBean;

import java.util.List;

/**
 * 配送时效管理-配送时效
 *
 * @author 朱英松
 * @date 2019/7/29
 */
interface DeliveryAgeingFragmentContract {

    interface IDeliveryAgeingBookView extends ILoadView {
        /**
         * 展示配送时效列表
         *
         * @param list 配送时效数据
         */
        void showList(List<DeliveryPeriodBean> list);
    }

    interface IDeliveryAgeingBookPresenter extends IPresenter<IDeliveryAgeingBookView> {
        /**
         * 配送时效列表查询
         */
        void queryDeliveryAgeingList();
    }
}
