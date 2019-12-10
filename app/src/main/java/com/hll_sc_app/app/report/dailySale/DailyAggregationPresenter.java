package com.hll_sc_app.app.report.dailySale;

import android.text.TextUtils;

import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.report.req.BaseReportReqParam;
import com.hll_sc_app.bean.report.resp.bill.DateSaleAmountResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.utils.Utils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 *
 *日销售汇总
 * @author 初坤
 * @date 2019/7/20
 */
public class DailyAggregationPresenter implements DailyAggregationContract.IDailyAggregationManagePresenter {
    private DailyAggregationContract.IDailyAggregationView mView;
    private int mPageNum;

    static DailyAggregationPresenter newInstance() {
        return new DailyAggregationPresenter();
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void register(DailyAggregationContract.IDailyAggregationView view) {
        this.mView = CommonUtils.checkNotNull(view);
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
    public void exportDailyReport(String email) {
        if (!TextUtils.isEmpty(email)) {
            bindEmail(email);
            return;
        }
        BaseReportReqParam dailyReq = new BaseReportReqParam();
        dailyReq.setTimeType(1);
        dailyReq.setStartDate(mView.getStartDate());
        dailyReq.setEndDate(mView.getEndDate());
        dailyReq.setGroupID(UserConfig.getGroupID());
        Report.exportReport(dailyReq, "111005", email, Utils.getExportObserver(mView));
    }

    private void load(boolean showLoading) {
        BaseReportReqParam dailyReq = new BaseReportReqParam();
        dailyReq.setTimeType(1);
        dailyReq.setStartDate(mView.getStartDate());
        dailyReq.setEndDate(mView.getEndDate());
        dailyReq.setGroupID(UserConfig.getGroupID());
        dailyReq.setPageNum(mPageNum);
        dailyReq.setPageSize(20);
        Report.queryDateSaleAmount(dailyReq, new SimpleObserver<DateSaleAmountResp>(mView,showLoading) {
            @Override
            public void onSuccess(DateSaleAmountResp dateSaleAmountResp) {
                mView.showDailyAggregationList(dateSaleAmountResp,mPageNum!=1,(int)dateSaleAmountResp.getTotalSize());
                if (CommonUtils.isEmpty(dateSaleAmountResp.getRecords())) return;
                mPageNum++;
            }
        });
    }

    private void bindEmail(String email) {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("email", email)
                .put("employeeID", GreenDaoUtils.getUser().getEmployeeID())
                .create();
        SimpleObserver<Object> observer = new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                exportDailyReport(null);
            }
        };
        UserService.INSTANCE.bindEmail(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(observer);
    }

}
