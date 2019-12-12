package com.hll_sc_app.app.report.customersales.detail;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Utils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/12
 */

public class CustomerSalesDetailPresenter implements ICustomerSalesDetailContract.ICustomerSalesDetailPresenter {
    private ICustomerSalesDetailContract.ICustomerSalesDetailView mView;
    private int mPageNum;

    public static CustomerSalesDetailPresenter newInstance() {
        return new CustomerSalesDetailPresenter();
    }

    private CustomerSalesDetailPresenter() {
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        load(false);
    }

    @Override
    public void loadMore() {
        load(false);
    }

    private void load(boolean showLoading) {
        Report.queryCustomerSales(mView.getReq()
                .put("pageNum", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .create(), new SimpleObserver<CustomerSalesResp>(mView, showLoading) {
            @Override
            public void onSuccess(CustomerSalesResp customerSalesResp) {
                mView.setData(customerSalesResp, mPageNum > 1);
                if (CommonUtils.isEmpty(customerSalesResp.getRecords())) return;
                mPageNum++;
            }
        });
    }

    @Override
    public void export(String email) {
        if (!TextUtils.isEmpty(email)) {
            bindEmail(email);
            return;
        }
        Report.exportReport(mView.getReq()
                .put("pageNum", "")
                .put("pageSize", "")
                .create().getData(), "111004", email, Utils.getExportObserver(mView));
    }

    private void bindEmail(String email) {
        User.bindEmail(email, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                export(null);
            }
        });
    }

    @Override
    public void register(ICustomerSalesDetailContract.ICustomerSalesDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
