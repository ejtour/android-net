package com.hll_sc_app.app.report.customerSale.saleDetail;


import com.hll_sc_app.api.PriceManageService;
import com.hll_sc_app.api.ReportService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.pricemanage.PriceLogResp;
import com.hll_sc_app.bean.report.req.CustomerSaleReq;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.ReportRest;


public class CustomerSalesDetailPresenter implements CustomerSalesDetailContract.ICustomerSaleDetailPresenter {

    private CustomerSalesDetailContract.ICustomerSalesDetailView mView;
    private int mPageNum;
    private int mTempPageNum;

    static CustomerSalesDetailPresenter newInstance() {
        return new CustomerSalesDetailPresenter();
    }


    public void start(){
        queryCustomerGatherDetailList(true);
    }
    @Override
    public void queryCustomerGatherDetailList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryCustomerSalesDetailList(showLoading);
    }

    @Override
    public void queryMoreCustomerGatherDetailList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryCustomerSalesDetailList(false);
    }

    @Override
    public void exportCustomerSaleDetail(String email) {

    }

    @Override
    public void register(CustomerSalesDetailContract.ICustomerSalesDetailView view) {
      this.mView = CommonUtils.requireNonNull(view);
    }

    private void toQueryCustomerSalesDetailList(boolean showLoading) {
        CustomerSaleReq params = mView.getParams();
        params.setGroupID(UserConfig.getGroupID());
        params.setPageNum(mTempPageNum);
        params.setPageSize(20);
        ReportRest.queryCustomerSales(params, new SimpleObserver<CustomerSalesResp>(mView,showLoading) {
            @Override
            public void onSuccess(CustomerSalesResp customerSalesResp) {
                mPageNum = mTempPageNum;
                mView.showCustomerGatherDetailList(customerSalesResp.getRecords(),mPageNum != 1, customerSalesResp.getTotalSize());
                if(customerSalesResp.getTotalSize()>0){
                    mView.showCustomerSaleGatherDatas(customerSalesResp);
                }
            }
        });
    }
}
