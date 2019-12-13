package com.hll_sc_app.app.report.customersales;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesResp;

public interface CustomerSalesContract {
    interface ICustomerSaleView extends ILoadView {
        /**
         * 展示客户销售汇总的数据
         * @param customerSalesResp
         */
        void setData(CustomerSalesResp customerSalesResp);

        /**
         * 获取参数
         *
         * @return 参数
         */
        BaseMapReq.Builder getReq();
    }

    interface ICustomerSaleManagePresenter extends IPresenter<CustomerSalesContract.ICustomerSaleView> {
    }
}
