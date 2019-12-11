package com.hll_sc_app.app.report.customerSale;

import com.hll_sc_app.app.report.dailySale.DailyAggregationContract;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.req.CustomerSaleReq;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesResp;

public interface CustomerSaleContract {
    interface ICustomerSaleView extends ILoadView {
        /**
         * 展示客户销售汇总的数据
         * @param customerSalesResp
         */
        void showCustomerSaleGather(CustomerSalesResp customerSalesResp);

        /**
         * 获取参数
         *
         * @return 参数
         */
        CustomerSaleReq getReq();

    }

    interface ICustomerSaleManagePresenter extends IPresenter<CustomerSaleContract.ICustomerSaleView> {
    }
}
