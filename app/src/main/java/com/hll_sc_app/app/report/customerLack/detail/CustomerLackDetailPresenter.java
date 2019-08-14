package com.hll_sc_app.app.report.customerLack.detail;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.report.customerLack.CustomerLackReq;
import com.hll_sc_app.bean.report.customerLack.CustomerLackResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.utils.Utils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author chukun
 * @since 2019/7/23
 */

public class CustomerLackDetailPresenter implements CustomerLackDetailContract.ICustomerLackDetailPresenter {

    private int mPageNum;
    private CustomerLackDetailContract.ICustomerLackDetailView mView;

    public static CustomerLackDetailPresenter newInstance() {
        CustomerLackDetailPresenter presenter = new CustomerLackDetailPresenter();
        return presenter;
    }

    @Override
    public void start() {
        mPageNum = 1;
        queryList(true);
    }

    private void queryList(boolean showLoading) {
        CustomerLackReq requestParams  = mView.getRequestParams();
        requestParams.setPageNum(mPageNum);
        requestParams.setPageSize(20);
        requestParams.setGroupID(UserConfig.getGroupID());
        Report.queryCustomerLack(requestParams, new SimpleObserver<CustomerLackResp>(mView, showLoading) {
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
        queryList(false);
    }

    @Override
    public void loadMore() {
        queryList(false);
    }

    @Override
    public void export(String params,String email) {
        if (!TextUtils.isEmpty(email)) {
            bindEmail(email);
            return;
        }
        Report.exportReport(params, "111053", email,
                Utils.getExportObserver(mView));
    }

    private void bindEmail(String email) {
        UserBean user = GreenDaoUtils.getUser();
        if (user == null)
            return;
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("email", email)
                .put("employeeID", user.getEmployeeID())
                .create();
        SimpleObserver<Object> observer = new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                CustomerLackReq requestParams = mView.getRequestParams();
                Gson gson = new Gson();
                String reqParams = gson.toJson(requestParams);
                export(reqParams,null);
            }
        };
        UserService.INSTANCE.bindEmail(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(observer);
    }

    @Override
    public void register(CustomerLackDetailContract.ICustomerLackDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
