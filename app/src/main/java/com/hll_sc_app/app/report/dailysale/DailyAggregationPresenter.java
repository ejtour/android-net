package com.hll_sc_app.app.report.dailysale;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.resp.bill.DateSaleAmountResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Utils;

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
        Report.exportReport(mView.getReqBuilder()
                .put("pageNum", "")
                .put("pageSize", "")
                .create().getData(), "111005", email, Utils.getExportObserver(mView, "shopmall-supplier"));
    }

    private void load(boolean showLoading) {
        Report.queryDateSaleAmount(mView.getReqBuilder()
                .put("pageNum", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .create(), new SimpleObserver<DateSaleAmountResp>(mView, showLoading) {
            @Override
            public void onSuccess(DateSaleAmountResp dateSaleAmountResp) {
                mView.showDailyAggregationList(dateSaleAmountResp, mPageNum > 1);
                if (CommonUtils.isEmpty(dateSaleAmountResp.getRecords())) return;
                mPageNum++;
            }
        });
    }

    private void bindEmail(String email) {
        User.bindEmail(email, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                exportDailyReport(null);
            }
        });
    }
}
