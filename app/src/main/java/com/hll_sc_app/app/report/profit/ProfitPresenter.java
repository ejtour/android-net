package com.hll_sc_app.app.report.profit;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.profit.ProfitResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Utils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/16
 */

public class ProfitPresenter implements IProfitContract.IProfitPresenter {
    private IProfitContract.IProfitView mView;
    private int mPageNum;

    public static ProfitPresenter newInstance() {
        return new ProfitPresenter();
    }

    private ProfitPresenter() {
    }

    @Override
    public void start() {
        mPageNum = 1;
        queryList(true);
    }

    private void queryList(boolean showLoading) {
        Report.queryProfit(mView.getReq()
                .put("pageNum", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .create(), new SimpleObserver<ProfitResp>(mView, showLoading) {
                    @Override
                    public void onSuccess(ProfitResp receiveDiffDetailsResp) {
                        mView.setData(receiveDiffDetailsResp, mPageNum > 1);
                        if (!CommonUtils.isEmpty(receiveDiffDetailsResp.getRecords())) {
                            mPageNum++;
                        }
                    }
                });
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        queryList(false);
    }

    @Override
    public void loadMore() {
        queryList(false);
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
                .create().getData(), "111026", email, Utils.getExportObserver(mView));
    }

    @Override
    public void register(IProfitContract.IProfitView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
