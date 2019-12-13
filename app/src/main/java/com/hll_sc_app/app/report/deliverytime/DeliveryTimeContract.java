package com.hll_sc_app.app.report.deliverytime;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.deliverytime.DeliveryTimeResp;

/**
 * 配送及时率
 *
 * @author chukun
 * @date 2019/08/16
 */
public interface DeliveryTimeContract {

    interface IDeliveryTimeView extends ILoadView {
        /**
         * 展示配送及时率的饼图数据
         * @param deliveryTimeResp
         */
        void showDeliveryTimePieCharts(DeliveryTimeResp deliveryTimeResp);

    }

    interface IDeliveryTimePresenter extends IPresenter<IDeliveryTimeView> {
        /**
         * 查询配送及时率饼图数据
         * @param showLoading true-显示对话框
         */
        void queryDeliveryTimePieCharts(boolean showLoading);
    }
}
