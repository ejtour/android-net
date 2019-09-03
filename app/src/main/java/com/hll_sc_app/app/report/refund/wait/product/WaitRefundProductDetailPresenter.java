package com.hll_sc_app.app.report.refund.wait.product;

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
import com.hll_sc_app.bean.report.refund.WaitRefundProductResp;
import com.hll_sc_app.bean.report.refund.WaitRefundReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 *
 * 待退商品明细
 * @author 初坤
 * @date 2019/7/20
 */
public class WaitRefundProductDetailPresenter implements WaitRefundProductDetailContract.IWaitRefundProductDetailPresenter {

    private WaitRefundProductDetailContract.IWaitRefundProductDetailView mView;
    private int mPageNum;
    private int mTempPageNum;

    static WaitRefundProductDetailPresenter newInstance() {
        return new WaitRefundProductDetailPresenter();
    }

    @Override
    public void start() {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        queryRefundProductDetail(true);
    }



    @Override
    public void register(WaitRefundProductDetailContract.IWaitRefundProductDetailView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryRefundProductDetail(boolean showLoading) {
        toQueryRefundProductDetail(showLoading);
    }

    @Override
    public void loadMoreRefundProductDetail(){
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryRefundProductDetail(true);
    }

    private void toQueryRefundProductDetail(boolean showLoading) {
        WaitRefundReq requestParams = mView.getRequestParams();
        requestParams.setGroupID(UserConfig.getGroupID());
        requestParams.setPageNum(mTempPageNum);
        requestParams.setPageSize(20);
        Report.queryWaitRefundProductList(requestParams, new SimpleObserver<WaitRefundProductResp>(mView,showLoading) {
            @Override
            public void onSuccess(WaitRefundProductResp refundProductResp) {
                mView.showRefundProductDetail(refundProductResp,mPageNum>1);
                if (!CommonUtils.isEmpty(refundProductResp.getRecords())) {
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
    public void exportRefundProductDetail(String email, String reqParams) {
        if (!TextUtils.isEmpty(email)) {
            bindEmail(email);
            return;
        }
        Report.exportReport(reqParams, "111015", email, new SimpleObserver<ExportResp>(mView) {
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
                WaitRefundReq params = mView.getRequestParams();
                params.setGroupID(UserConfig.getGroupID());
                params.setPageNum(mPageNum);
                params.setPageSize(20);
                Gson gson = new Gson();
                String reqParams = gson.toJson(params);
                exportRefundProductDetail(null, reqParams);
            }
        };
        UserService.INSTANCE.bindEmail(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(observer);
    }

}
