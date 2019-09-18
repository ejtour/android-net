package com.hll_sc_app.app.report.deliveryLack;


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
import com.hll_sc_app.bean.report.deliveryLack.DeliveryLackGatherResp;
import com.hll_sc_app.bean.report.req.BaseReportReqParam;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;


public class DeliveryLackGatherPresenter implements DeliveryLackGatherContract.IDeliveryLackGatherPresenter {

    private DeliveryLackGatherContract.IDeliveryLackGatherView mView;
    private int mPageNum;
    private int mTempPageNum;

    static DeliveryLackGatherPresenter newInstance() {
        return new DeliveryLackGatherPresenter();
    }


    public void start() {
        queryDeliveryLackGatherList(true);
    }

    @Override
    public void queryDeliveryLackGatherList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toDeliveryLackGathertList(showLoading);
    }

    @Override
    public void queryMoreDeliveryLackGatherList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toDeliveryLackGathertList(false);
    }

    @Override
    public void exportDeliveryLackGather(String email, String reqParams) {
        if (!TextUtils.isEmpty(email)) {
            bindEmail(email);
            return;
        }
        Report.exportReport(reqParams, "111006", email, new SimpleObserver<ExportResp>(mView) {
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

    private void toDeliveryLackGathertList(boolean showLoading) {
        BaseReportReqParam params = mView.getReqParams();
        params.setGroupID(UserConfig.getGroupID());
        params.setPageNum(mTempPageNum);
        params.setPageSize(20);
        Report.queryDeliveryLackGather(params, new SimpleObserver<DeliveryLackGatherResp>(mView, showLoading) {
            @Override
            public void onSuccess(DeliveryLackGatherResp deliveryLackGatherResp) {
                mPageNum = mTempPageNum;
                mView.showDeliveryLackGatherList(deliveryLackGatherResp.getRecords(), mPageNum != 1,
                    deliveryLackGatherResp.getTotalSize());
                if (deliveryLackGatherResp.getTotalSize() > 0) {
                    mView.showTotalDeliveryGatherDatas(deliveryLackGatherResp);
                }
            }
        });
    }

    @Override
    public void register(DeliveryLackGatherContract.IDeliveryLackGatherView view) {
        this.mView = CommonUtils.requireNonNull(view);
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
                BaseReportReqParam params = mView.getReqParams();
                params.setGroupID(UserConfig.getGroupID());
                params.setPageNum(mTempPageNum);
                params.setPageSize(20);
                Gson gson = new Gson();
                String reqParams = gson.toJson(params);
                exportDeliveryLackGather(null, reqParams);
            }
        };
        UserService.INSTANCE.bindEmail(req)
            .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(observer);
    }

}
