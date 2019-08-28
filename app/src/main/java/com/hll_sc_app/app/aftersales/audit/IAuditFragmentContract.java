package com.hll_sc_app.app.aftersales.audit;


import com.hll_sc_app.app.aftersales.common.IAction;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.aftersales.AfterSalesBean;
import com.hll_sc_app.bean.filter.AuditParam;
import com.hll_sc_app.impl.IExportView;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/8
 */

public interface IAuditFragmentContract {
    interface IAuditFragmentView extends IExportView, IAction {
        AuditParam getAuditParam();

        Integer getBillStatus();

        void showList(List<AfterSalesBean> recordsBeans, boolean isMore);

        void actionSuccess();

        void updateItem(AfterSalesBean bean);
    }

    interface IAuditFragmentPresenter extends IPresenter<IAuditFragmentView> {
        void refresh();

        void loadMore();

        void doAction(int actionType, String billID, int status, int type, String payType, String reason);

        void requestDetails(String refundBillID);

        void exportOrder(String email);
    }
}
