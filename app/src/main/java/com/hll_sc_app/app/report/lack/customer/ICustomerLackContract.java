package com.hll_sc_app.app.report.lack.customer;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.lack.CustomerLackBean;
import com.hll_sc_app.impl.IExportView;
import com.hll_sc_app.impl.IPurchaserContract;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/17
 */

public interface ICustomerLackContract {
    interface ICustomerLackView extends IExportView, IPurchaserContract.IPurchaserView {
        void showSummaryList(List<CustomerLackBean> list, boolean append);

        BaseMapReq.Builder getReq();
    }

    interface ICustomerLackPresenter extends IPresenter<ICustomerLackView>, IPurchaserContract.IPurchaserPresenter {
        void reload();

        void loadMore();

        void refresh();

        void export(String email);
    }
}
