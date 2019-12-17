package com.hll_sc_app.app.report.lack.customer.details;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.lack.CustomerLackResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Utils;

/**
 * @author chukun
 * @since 2019/7/23
 */

public class CustomerLackDetailPresenter implements ICustomerLackDetailsContract.ICustomerLackDetailPresenter {

    private int mPageNum;
    private ICustomerLackDetailsContract.ICustomerLackDetailsView mView;

    public static CustomerLackDetailPresenter newInstance() {
        return new CustomerLackDetailPresenter();
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    private void load(boolean showLoading) {
        Report.queryCustomerLack(mView.getReq()
                .put("pageNum", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .create(), new SimpleObserver<CustomerLackResp>(mView, showLoading) {
            @Override
            public void onSuccess(CustomerLackResp customerLackResp) {
                mView.setList(customerLackResp.getDetail(), mPageNum > 1);
                if (!CommonUtils.isEmpty(customerLackResp.getDetail())) {
                    mPageNum++;
                }
            }
        });
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

    @Override
    public void export(String email) {
        if (!TextUtils.isEmpty(email)) {
            User.bindEmail(email, new SimpleObserver<Object>(mView) {
                @Override
                public void onSuccess(Object o) {
                    export(null);
                }
            });
            return;
        }
        Report.exportReport(mView.getReq()
                .put("pageNum", "")
                .put("pageSize", "")
                .create().getData(), "111053", email, Utils.getExportObserver(mView));
    }

    @Override
    public void register(ICustomerLackDetailsContract.ICustomerLackDetailsView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
