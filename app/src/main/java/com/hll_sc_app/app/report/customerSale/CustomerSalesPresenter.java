package com.hll_sc_app.app.report.customerSale;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.report.req.CustomerSaleReq;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.ReportRest;

public class CustomerSalesPresenter implements  CustomerSaleContract.ICustomerSaleManagePresenter{

    private  CustomerSaleContract.ICustomerSaleView mView;
    private int mPageNum;
    private int mTempPageNum;

    private CustomerSalesPresenter(){}

    public static CustomerSalesPresenter newInstance() {
        return new CustomerSalesPresenter();
    }
    @Override
    public void start() {
        queryCustomerSaleGather(true);
    }

    @Override
    public void register(CustomerSaleContract.ICustomerSaleView view) {
       mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void queryCustomerSaleGather(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toCustomerSaleList(showLoading);
    }

    @Override
    public void exportCustomerSaleReport(String email) {

    }

    private void toCustomerSaleList(boolean showLoading){
        CustomerSaleReq params = mView.getParams();
        params.setGroupID(UserConfig.getGroupID());
        params.setPageNum(mTempPageNum);
        params.setPageSize(20);
        ReportRest.queryCustomerSales(params, new SimpleObserver<CustomerSalesResp>(mView,showLoading) {
            @Override
            public void onSuccess(CustomerSalesResp customerSalesResp) {
                mPageNum = mTempPageNum;
                mView.showCustomerSaleGather(customerSalesResp);
            }
        });
    }
}
