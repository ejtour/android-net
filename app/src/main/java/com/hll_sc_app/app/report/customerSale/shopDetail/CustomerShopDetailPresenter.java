package com.hll_sc_app.app.report.customerSale.shopDetail;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.report.req.CustomerSaleReq;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.ReportRest;

public class CustomerShopDetailPresenter implements CustomerShopDetailContract.ICustomerShopDetailPresenter {
    private CustomerShopDetailContract.ICustomerShopDetailView mView;
    private int mPageNum;
    private int mTempPageNum;

    static CustomerShopDetailPresenter newInstance() {
        return new CustomerShopDetailPresenter();
    }

    /**
     * 初始化请求
     */
    public void start(){
        queryCustomerShopDetailList(true);
    }
    @Override
    public void queryCustomerShopDetailList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryCustomerShopDetailList(showLoading);
    }

    @Override
    public void queryMoreCustomerShopDetailList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryCustomerShopDetailList(false);
    }

    @Override
    public void exportCustomerShopDetail(String email) {

    }

    @Override
    public void register(CustomerShopDetailContract.ICustomerShopDetailView view) {
        this.mView = CommonUtils.requireNonNull(view);
    }

    private void toQueryCustomerShopDetailList(boolean showLoading) {
        CustomerSaleReq params = mView.getParams();
        params.setGroupID(UserConfig.getGroupID());
        params.setPageNum(mTempPageNum);
        params.setPageSize(20);
        ReportRest.queryCustomerSales(params, new SimpleObserver<CustomerSalesResp>(mView,showLoading) {
            @Override
            public void onSuccess(CustomerSalesResp customerSalesResp) {
                mPageNum = mTempPageNum;
                mView.showCustomerShopDetailList(customerSalesResp.getRecords(),mPageNum != 1, customerSalesResp.getTotalSize());
            }
        });
    }
}
