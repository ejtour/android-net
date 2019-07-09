package com.hll_sc_app.app.aftersales.audit;


import com.hll_sc_app.app.aftersales.common.IAction;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.aftersales.AfterSalesBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/8
 */

public interface IAuditFragmentContract {
    interface IAuditFragmentView extends ILoadView, IAction {
        AuditParam getAuditParam();

        Integer getBillStatus();

        void showList(List<AfterSalesBean> recordsBeans, boolean isMore);

        void actionSuccess();

        void updateItem(AfterSalesBean bean);

        void exportSuccess(String email);

        void exportFailure(String msg);

        void bindEmail();
    }

    interface IAuditFragmentPresenter extends IPresenter<IAuditFragmentView> {
        void refresh();

        void loadMore();

        void doAction(int actionType, String billID, int status, int type, String payType, String reason);

        void requestDetails(String refundBillID);

        void exportOrder(String email);
    }
}
