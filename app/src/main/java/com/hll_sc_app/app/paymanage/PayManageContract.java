package com.hll_sc_app.app.paymanage;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.cooperation.SettlementBean;
import com.hll_sc_app.bean.paymanage.PayBean;

import java.util.List;

/**
 * 支付管理
 *
 * @author zhuyingsong
 * @date 2019/8/8
 */
public interface PayManageContract {

    interface IPayManageView extends ILoadView {
        /**
         * 展示选中的支付方式
         *
         * @param bean 支付方式
         */
        void showPayList(SettlementBean bean);

        /**
         * 展示选中的支付方式
         */
        void showPayList();

        /**
         * 修改成功
         */
        void editSuccess();

        /**
         * 设置默认支付方式
         *
         * @param list 默认支付方式列表
         */
        void setDefaultPayMethod(List<PayBean> list);
    }

    interface IPayManagePresenter extends IPresenter<IPayManageView> {
        /**
         * 查询支付方式
         */
        void querySettlementList();



        /**
         * 查询默认支付方式列表
         */
        void querySettlementMethodList();
    }
}
