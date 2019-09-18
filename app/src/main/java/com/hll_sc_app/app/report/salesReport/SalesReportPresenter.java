package com.hll_sc_app.app.report.salesReport;

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
import com.hll_sc_app.bean.report.salesReport.SalesReportReq;
import com.hll_sc_app.bean.report.salesReport.SalesReportResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import static com.uber.autodispose.AutoDispose.autoDisposable;
/**
 *
 * 销售日报
 * @author 初坤
 * @date 2019/09/09
 */
public class SalesReportPresenter implements SalesReportContract.ISalesReportPresenter {
    private SalesReportContract.ISalesReportView mView;
    private int mPageNum;
    private int mTempPageNum;

    static SalesReportPresenter newInstance() {
        return new SalesReportPresenter();
    }

    @Override
    public void start() {
        querySalesReportList(true);
    }

    @Override
    public void register(SalesReportContract.ISalesReportView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void querySalesReportList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryDailyAggregationList(showLoading);
    }

    @Override
    public void queryMoreSalesReportList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryDailyAggregationList(false);
    }

    @Override
    public void exportSalesReport(String email) {
        if (!TextUtils.isEmpty(email)) {
            bindEmail(email);
            return;
        }
        SalesReportReq salesReportReq = mView.getRequestParams();
        Gson gson = new Gson();
        String reqParams = gson.toJson(salesReportReq);
        Report.exportReport(reqParams,"111045",email,new SimpleObserver<ExportResp>(mView){
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

    private void toQueryDailyAggregationList(boolean showLoading) {
        SalesReportReq reportReq = mView.getRequestParams();
        reportReq.setGroupID(UserConfig.getGroupID());
        reportReq.setPageNum(mTempPageNum);
        reportReq.setPageSize(20);
        Report.querySalesReportList(reportReq, new SimpleObserver<SalesReportResp>(mView,showLoading) {
            @Override
            public void onSuccess(SalesReportResp salesReportResp) {
                mPageNum = mTempPageNum;
                mView.showSalesReportList(salesReportResp,mPageNum!=1,(int)salesReportResp.getTotalSize());
            }
            @Override
            public void onFailure(UseCaseException e) {
                mView.showError(e);
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
                exportSalesReport(null);
            }
        };
        UserService.INSTANCE.bindEmail(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(observer);
    }

}
