package com.hll_sc_app.app.report.daily;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.report.daily.SalesDailyBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Utils;

/**
 * 销售日报
 *
 * @author 初坤
 * @date 2019/09/09
 */
public class SalesDailyPresenter implements SalesDailyContract.ISalesDailyPresenter {
    private SalesDailyContract.ISalesDailyView mView;
    private int mPageNum;

    static SalesDailyPresenter newInstance() {
        return new SalesDailyPresenter();
    }

    private SalesDailyPresenter() {
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void register(SalesDailyContract.ISalesDailyView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void export(String email) {
        if (!TextUtils.isEmpty(email)) {
            User.bindEmail(email, new SimpleObserver<Object>(mView) {
                @Override
                public void onSuccess(Object o) {
                    export(null);
                }
            });
            return;
        }
        Report.exportReport(mView.getReq()
                .put("pageNum", "")
                .put("pageSize", "")
                .create().getData(), "111045", email, Utils.getExportObserver(mView, "shopmall-supplier"));
    }

    private void load(boolean showLoading) {
        Report.querySalesDaily(mView.getReq()
                        .put("pageNum", String.valueOf(mPageNum))
                        .put("pageSize", "20")
                        .create(),
                new SimpleObserver<SingleListResp<SalesDailyBean>>(mView, showLoading) {
                    @Override
                    public void onSuccess(SingleListResp<SalesDailyBean> salesReportBeanSingleListResp) {
                        mView.setData(salesReportBeanSingleListResp.getRecords(), mPageNum > 1);
                        if (CommonUtils.isEmpty(salesReportBeanSingleListResp.getRecords())) return;
                        mPageNum++;
                    }
                });
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
}
