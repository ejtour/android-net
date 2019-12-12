package com.hll_sc_app.app.report.customersales.detail;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesResp;
import com.hll_sc_app.impl.IExportView;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/12
 */

public interface ICustomerSalesDetailContract {
    interface ICustomerSalesDetailView extends IExportView {
        void setData(CustomerSalesResp resp, boolean append);

        BaseMapReq.Builder getReq();
    }

    interface ICustomerSalesDetailPresenter extends IPresenter<ICustomerSalesDetailView> {
        void refresh();

        void loadMore();

        void export(String email);
    }
}
