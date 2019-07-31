package com.hll_sc_app.app.deliverymanage.ageing.book;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

/**
 * 配送时效管理-预定设置
 *
 * @author 朱英松
 * @date 2019/7/29
 */
interface DeliveryAgeingBookContract {

    interface IDeliveryAgeingBookView extends ILoadView {
        /**
         * 展示预订天数
         *
         * @param s 预订天数
         */
        void showBookingDate(String s);

        /**
         * 选择可预订天数
         */
        void showDaysSelectWindow();
    }

    interface IDeliveryAgeingBookPresenter extends IPresenter<IDeliveryAgeingBookView> {
        /**
         * 查询可预订天数
         */
        void queryGroupParam();

        /**
         * 编辑可预订天数
         *
         * @param days 日期
         */
        void editGroupParam(String days);
    }
}
