package com.hll_sc_app.app.paymanage.account;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

/**
 * 支付管理-设置账期日
 *
 * @author zhuyingsong
 * @date 2019/8/9
 */
public interface PayAccountManageContract {

    interface IAccountView extends ILoadView {
        /**
         * 修改成功
         */
        void editSuccess();
    }

    interface IAccountPresenter extends IPresenter<IAccountView> {
        /**
         * 修改账期支付方式
         *
         * @param payTermType 账期支付方式;0-未设置,1-按周,2-按月
         * @param payTerm     账期支付具体日期
         * @param settleDate  结算日
         */
        void editAccount(String payTermType, String payTerm, String settleDate);
    }
}
