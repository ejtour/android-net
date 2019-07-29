package com.hll_sc_app.app.report.customerSale.saleDetail;


import android.text.TextUtils;

import com.hll_sc_app.api.PriceManageService;
import com.hll_sc_app.api.ReportService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.pricemanage.PriceLogResp;
import com.hll_sc_app.bean.report.req.CustomerSaleReq;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
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
    public void exportCustomerSaleDetail(String email, String reqParams) {
        Report.exportReport(reqParams,"111004",email,new SimpleObserver<ExportResp>(mView){
            @Override
            public void onSuccess(ExportResp exportResp) {
                if (!TextUtils.isEmpty(exportResp.getEmail()))
                    mView.exportSuccess(exportResp.getEmail());
                else mView.exportFailure("噢，服务器暂时开了小差\n攻城狮正在全力抢修");
            }

            @Override
            public void onFailure(UseCaseException e) {
                if ("00120112037".equals(e.getCode())) mView.bindEmail();
                else if ("00120112038".equals(e.getCode()))
                    mView.exportFailure("当前没有可导出的数据");
                else mView.exportFailure("噢，服务器暂时开了小差\n攻城狮正在全力抢修");
            }
        });
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
