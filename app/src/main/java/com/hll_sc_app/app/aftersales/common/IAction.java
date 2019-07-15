package com.hll_sc_app.app.aftersales.common;

/**
 * @author <a href="mailto:xzx8023@vip.qq.com">Vixb</a>
 * @since 2019/5/18
 */

public interface IAction {
    void actionReject();

    void actionCancel();

    void actionGoodsOperation();

    void actionCustomerService();

    void actionFinance();

    default void actionViewDetails() {
    }
}
