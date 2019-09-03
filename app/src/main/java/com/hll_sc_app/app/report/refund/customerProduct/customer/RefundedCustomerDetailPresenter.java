package com.hll_sc_app.app.report.refund.customerProduct.customer;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.report.refund.RefundedCustomerReq;
import com.hll_sc_app.bean.report.refund.RefundedCustomerResp;
import com.hll_sc_app.bean.report.refund.WaitRefundReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 *
 * 退货客户明细
 * @author 初坤
 * @date 2019/7/20
 */
public class RefundedCustomerDetailPresenter implements RefundedCustomerDetailContract.IRefundedCustomerDetailPresenter {

    private RefundedCustomerDetailContract.IRefundedCustomerDetailView mView;
    private int mPageNum;
    private int mTempPageNum;

    static RefundedCustomerDetailPresenter newInstance() {
        return new RefundedCustomerDetailPresenter();
    }

    @Override
    public void start() {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        queryRefundedCustomerDetail(true);
    }



    @Override
    public void register(RefundedCustomerDetailContract.IRefundedCustomerDetailView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryRefundedCustomerDetail(boolean showLoading) {
        toQueryRefundedCustomerDetail(showLoading);
    }

    @Override
    public void loadMoreRefundedCustomerDetail(){
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryRefundedCustomerDetail(true);
    }

    private void toQueryRefundedCustomerDetail(boolean showLoading) {
        RefundedCustomerReq requestParams = mView.getRequestParams();
        requestParams.setGroupID(UserConfig.getGroupID());
        requestParams.setPageNum(mTempPageNum);
        requestParams.setPageSize(20);
        Report.queryRefundedCustomerList(requestParams, new SimpleObserver<RefundedCustomerResp>(mView,showLoading) {
            @Override
            public void onSuccess(RefundedCustomerResp refundCustomerResp) {
                boolean isNotEmpty = !CommonUtils.isEmpty(refundCustomerResp.getGroupVoList());
                mView.showRefundedCustomerDetail(refundCustomerResp,mPageNum>1 && isNotEmpty);
                if (isNotEmpty) {
                    mPageNum = mTempPageNum;
                }
            }
            @Override
            public void onFailure(UseCaseException e) {
                mView.showError(e);
            }
        });
    }

    @Override
    public void exportRefundedCustomerDetail(String email, String reqParams) {
        if (!TextUtils.isEmpty(email)) {
            bindEmail(email);
            return;
        }
        Report.exportReport(reqParams, "111020", email, new SimpleObserver<ExportResp>(mView) {
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
                RefundedCustomerReq params = mView.getRequestParams();
                params.setGroupID(UserConfig.getGroupID());
                params.setPageNum(mPageNum);
                params.setPageSize(20);
                Gson gson = new Gson();
                String reqParams = gson.toJson(params);
                exportRefundedCustomerDetail(null, reqParams);
            }
        };
        UserService.INSTANCE.bindEmail(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(observer);
    }

}
