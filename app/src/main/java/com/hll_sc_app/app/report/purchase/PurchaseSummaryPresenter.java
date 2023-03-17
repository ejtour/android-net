package com.hll_sc_app.app.report.purchase;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.filter.DateParam;
import com.hll_sc_app.bean.report.purchase.PurchaseSummaryResp;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.utils.Utils;

import java.util.Date;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/27
 */

public class PurchaseSummaryPresenter implements IPurchaseSummaryContract.IPurchaseSummaryPresenter {
    private int mPageNum;
    private IPurchaseSummaryContract.IPurchaseSummaryView mView;
    private DateParam mParam;

    public static PurchaseSummaryPresenter newInstance(DateParam param) {
        return new PurchaseSummaryPresenter(param);
    }

    private PurchaseSummaryPresenter(DateParam param) {
        mParam = param;
    }

    private void load(boolean showLoading) {
        Report.queryPurchaseSummary(
                getReqParam()
                        .put("pageNum", String.valueOf(mPageNum))
                        .put("pageSize", "20")
                        .create(),
                new SimpleObserver<PurchaseSummaryResp>(mView, showLoading) {
                    @Override
                    public void onSuccess(PurchaseSummaryResp purchaseSummaryResp) {
                        mView.setList(purchaseSummaryResp, mPageNum > 1);
                        if (CommonUtils.isEmpty(purchaseSummaryResp.getRecords())) return;
                        mPageNum++;
                    }
                });
    }

    private BaseMapReq.Builder getReqParam() {
        return BaseMapReq.newBuilder()
                .put("startDate", mParam.getFormatStartDate())
                .put("endDate", mParam.getFormatEndDate())
                .put("groupID", UserConfig.getGroupID());
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
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
        Report.exportReport(getReqParam().create().getData(), "111038", email, Utils.getExportObserver(mView, "shopmall-supplier"));
    }

    @Override
    public void getTodayData() {
        String date = CalendarUtils.toLocalDate(new Date());
        Report.queryPurchaseSummary(
                BaseMapReq.newBuilder()
                        .put("groupID", UserConfig.getGroupID())
                        .put("startDate", date)
                        .put("endDate", date)
                        .put("pageNum", "1")
                        .put("pageSize", "1")
                        .create(),
                new SimpleObserver<PurchaseSummaryResp>(mView) {
                    @Override
                    public void onSuccess(PurchaseSummaryResp purchaseSummaryResp) {
                        mView.handleTodayData(purchaseSummaryResp);
                    }
                });
    }

    @Override
    public void register(IPurchaseSummaryContract.IPurchaseSummaryView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
