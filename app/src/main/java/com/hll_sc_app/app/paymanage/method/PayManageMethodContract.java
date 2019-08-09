package com.hll_sc_app.app.paymanage.method;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

/**
 * 支付管理-设置账期日
 *
 * @author zhuyingsong
 * @date 2019/8/9
 */
public interface PayManageMethodContract {

    interface IAccountView extends ILoadView {
        /**
         * 修改成功
         */
        void editSuccess();
    }

    interface IAccountPresenter extends IPresenter<IAccountView> {
        /**
         * 修改支付方式
         *
         * @param payType   1.在线支付2.货到付款
         * @param payMethod 支付方式列表
         */
        void editSettlementMethod(String payType, String payMethod);
    }
}
