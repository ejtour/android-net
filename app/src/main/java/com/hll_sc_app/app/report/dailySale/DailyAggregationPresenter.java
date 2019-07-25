package com.hll_sc_app.app.report.dailySale;

import android.text.TextUtils;

import com.alibaba.sdk.android.ams.common.util.StringUtil;
import com.google.zxing.common.StringUtils;
import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.api.PriceManageService;
import com.hll_sc_app.api.ReportService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.pricemanage.PriceLogResp;
import com.hll_sc_app.bean.report.req.BaseReportReqParam;
import com.hll_sc_app.bean.report.resp.bill.DateSaleAmountResp;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.ReportRest;
import com.hll_sc_app.utils.DateUtil;
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
    private int mTempPageNum;

    static DailyAggregationPresenter newInstance() {
        return new DailyAggregationPresenter();
    }

    @Override
    public void start() {
        queryDailyAggregationList(true);
    }

    @Override
    public void register(DailyAggregationContract.IDailyAggregationView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryDailyAggregationList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryDailyAggregationList(showLoading);
    }

    @Override
    public void queryMoreDailyAggregationList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryDailyAggregationList(false);
    }

    @Override
    public void exportDailyReport(String email) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        ExportReq req = new ExportReq();
        req.setIsBindEmail(TextUtils.isEmpty(email) ? "0" : "1");
        req.setEmail(email);
        req.setTypeCode("priceFlow");
        req.setUserID(userBean.getEmployeeID());

        ExportReq.ParamsBean paramsBean = new ExportReq.ParamsBean();
        ExportReq.ParamsBean.PriceFlowBean priceFlowBean = new ExportReq.ParamsBean.PriceFlowBean();
        priceFlowBean.setGroupID(userBean.getGroupID());
        priceFlowBean.setStartTime(mView.getStartDate());
        priceFlowBean.setEndTime(mView.getEndDate());
        paramsBean.setPriceFlow(priceFlowBean);
        req.setParams(paramsBean);
        BaseReq<ExportReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        GoodsService.INSTANCE.exportRecord(baseReq)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<ExportResp>() {
                @Override
                public void onSuccess(ExportResp resp) {
                    if (!TextUtils.isEmpty(resp.getEmail())) {
                        mView.exportSuccess(resp.getEmail());
                    } else {
                        mView.exportFailure("噢，服务器暂时开了小差\n攻城狮正在全力抢修");
                    }
                }

                @Override
                public void onFailure(UseCaseException e) {
                    if (TextUtils.equals("00120112037", e.getCode())) {
                        mView.bindEmail();
                    } else if (TextUtils.equals("00120112038", e.getCode())) {
                        mView.exportFailure("当前没有可导出的数据");
                    } else {
                        mView.exportFailure("噢，服务器暂时开了小差\n攻城狮正在全力抢修");
                    }
                }
            });
    }

    private void toQueryDailyAggregationList(boolean showLoading) {
        BaseReportReqParam dailyReq = new BaseReportReqParam();
        dailyReq.setTimeType(1);
        dailyReq.setStartDate(mView.getStartDate());
        dailyReq.setEndDate(mView.getEndDate());
        dailyReq.setGroupID(UserConfig.getGroupID());
        dailyReq.setPageNum(mTempPageNum);
        dailyReq.setPageSize(20);
        ReportRest.queryDateSaleAmount(dailyReq, new SimpleObserver<DateSaleAmountResp>(mView,showLoading) {
            @Override
            public void onSuccess(DateSaleAmountResp dateSaleAmountResp) {
                mPageNum = mTempPageNum;
                mView.showDailyAggregationList(dateSaleAmountResp,mPageNum!=1,(int)dateSaleAmountResp.getTotalSize());
            }
        });
    }
}
