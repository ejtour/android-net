package com.hll_sc_app.app.report.salesman.sales;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.salesman.SalesManSalesResp;
import com.hll_sc_app.impl.IExportView;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/12
 */

public interface ISalesManSalesContract {
    interface ISalesManSalesView extends IExportView {
        void setData(SalesManSalesResp resp, boolean append);

        BaseMapReq.Builder getReq();
    }

    interface ISalesManSalesPresenter extends IPresenter<ISalesManSalesView> {
        void refresh();

        void loadMore();

        void export(String email);
    }
}
