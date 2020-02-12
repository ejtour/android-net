package com.hll_sc_app.app.warehouse.detail.showpaylist;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.cooperation.SettlementBean;
import com.hll_sc_app.bean.paymanage.PayResp;

public interface IShowPayListContract {
    interface IView extends ILoadView {
        boolean isOwner();

        void showPayList(PayResp payResp, SettlementBean settlementBean);

        String getGroupId();
    }

    interface IPresent extends IPresenter<IView> {
        void getAllPayList();
    }
}
