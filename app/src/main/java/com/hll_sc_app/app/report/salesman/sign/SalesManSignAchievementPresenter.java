package com.hll_sc_app.app.report.salesman.sign;


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
import com.hll_sc_app.bean.report.salesman.SalesManAchievementReq;
import com.hll_sc_app.bean.report.salesman.SalesManSignResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.rest.ReportRest;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;


public class SalesManSignAchievementPresenter implements SalesManSignAchievementContract.ISalesManSignAchievementPresenter {

    private SalesManSignAchievementContract.ISalesManSignAchievementView mView;
    private int mPageNum;
    private int mTempPageNum;

    static SalesManSignAchievementPresenter newInstance() {
        return new SalesManSignAchievementPresenter();
    }


    public void start() {
        SalesManAchievementReq params = mView.getParams();
        params.setTimeType(1);
        params.setTimeFlag(0);
        querySalesManSignAchievementList(true);
    }

    @Override
    public void querySalesManSignAchievementList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQuerySalesManSignAchievementList(showLoading);
    }

    @Override
    public void queryMoreSalesManSignAchievementList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQuerySalesManSignAchievementList(false);
    }

    @Override
    public void exportSalesManSignAchievement(String email, String reqParams) {
        if (!TextUtils.isEmpty(email)) {
            bindEmail(email);
            return;
        }
        Report.exportReport(reqParams, "111009", email, new SimpleObserver<ExportResp>(mView) {
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

    private void toQuerySalesManSignAchievementList(boolean showLoading) {
        SalesManAchievementReq params = mView.getParams();
        params.setGroupID(UserConfig.getGroupID());
        params.setPageNum(mTempPageNum);
        params.setPageSize(20);
        ReportRest.querySalesmanSignAchievement(params, new SimpleObserver<SalesManSignResp>(mView, showLoading) {
            @Override
            public void onSuccess(SalesManSignResp salesManSignResp) {
                mPageNum = mTempPageNum;
                mView.showSalesManSignAchievementList(salesManSignResp.getRecords(), mPageNum != 1,
                    salesManSignResp.getTotalSize());
                if (salesManSignResp.getTotalSize() > 0) {
                    mView.showSalesManSignTotalDatas(salesManSignResp);
                }
            }
        });
    }

    @Override
    public void register(SalesManSignAchievementContract.ISalesManSignAchievementView view) {
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
                SalesManAchievementReq params = mView.getParams();
                params.setGroupID(UserConfig.getGroupID());
                params.setPageNum(mTempPageNum);
                params.setPageSize(20);
                Gson gson = new Gson();
                String reqParams = gson.toJson(params);
                exportSalesManSignAchievement(null, reqParams);
            }
        };
        UserService.INSTANCE.bindEmail(req)
            .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(observer);
    }

}
