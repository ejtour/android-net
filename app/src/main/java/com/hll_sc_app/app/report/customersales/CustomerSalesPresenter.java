package com.hll_sc_app.app.report.customersales;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;

public class CustomerSalesPresenter implements  CustomerSalesContract.ICustomerSaleManagePresenter{

    private  CustomerSalesContract.ICustomerSaleView mView;

    private CustomerSalesPresenter(){}

    public static CustomerSalesPresenter newInstance() {
        return new CustomerSalesPresenter();
    }
    @Override
    public void start() {
        Report.queryCustomerSales(mView.getReq().create(), new SimpleObserver<CustomerSalesResp>(mView) {
            @Override
            public void onSuccess(CustomerSalesResp customerSalesResp) {
                mView.setData(customerSalesResp);
            }
        });
    }

    @Override
    public void register(CustomerSalesContract.ICustomerSaleView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
