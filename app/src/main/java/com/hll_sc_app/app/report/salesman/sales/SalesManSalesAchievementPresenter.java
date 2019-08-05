package com.hll_sc_app.app.report.salesman.sales;


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
import com.hll_sc_app.bean.report.salesman.SalesManSalesResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.rest.ReportRest;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;


public class SalesManSalesAchievementPresenter implements SalesManSalesAchievementContract.ISalesManSalesAchievementPresenter {

    private SalesManSalesAchievementContract.ISalesManSalesAchievementView mView;
    private int mPageNum;
    private int mTempPageNum;

    static SalesManSalesAchievementPresenter newInstance() {
        return new SalesManSalesAchievementPresenter();
    }


    public void start() {
        querySalesManSalesAchievementList(true);
    }

    @Override
    public void querySalesManSalesAchievementList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQuerySalesManSalesAchievementList(showLoading);
    }

    @Override
    public void queryMoreSalesManSalesAchievementList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQuerySalesManSalesAchievementList(false);
    }

    @Override
    public void exportSalesManSalesAchievement(String email, String reqParams) {
        if (!TextUtils.isEmpty(email)) {
            bindEmail(email);
            return;
        }
        Report.exportReport(reqParams, "111003", email, new SimpleObserver<ExportResp>(mView) {
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

    private void toQuerySalesManSalesAchievementList(boolean showLoading) {
        SalesManAchievementReq params = mView.getParams();
        params.setGroupID(UserConfig.getGroupID());
        params.setPageNum(mTempPageNum);
        params.setPageSize(20);
        ReportRest.querySalesmanSalesAchievement(params, new SimpleObserver<SalesManSalesResp>(mView, showLoading) {
            @Override
            public void onSuccess(SalesManSalesResp salesManSalesResp) {
                mPageNum = mTempPageNum;
                mView.showSalesManSalesAchievementList(salesManSalesResp.getRecords(), mPageNum != 1,
                    salesManSalesResp.getTotalSize());
                if (salesManSalesResp.getTotalSize() > 0) {
                    mView.showSalesManSalesTotalDatas(salesManSalesResp);
                }
            }
        });
    }

    @Override
    public void register(SalesManSalesAchievementContract.ISalesManSalesAchievementView view) {
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
                exportSalesManSalesAchievement(null, reqParams);
            }
        };
        UserService.INSTANCE.bindEmail(req)
            .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(observer);
    }

}
