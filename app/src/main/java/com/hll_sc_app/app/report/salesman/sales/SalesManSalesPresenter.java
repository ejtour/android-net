package com.hll_sc_app.app.report.salesman.sales;

import android.text.TextUtils;

import com.hll_sc_app.app.report.salesman.sign.ISalesManSignContract;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.salesman.SalesManSalesResp;
import com.hll_sc_app.bean.report.salesman.SalesManSignResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Utils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/12
 */

public class SalesManSalesPresenter implements ISalesManSalesContract.ISalesManSalesPresenter {
    private ISalesManSalesContract.ISalesManSalesView mView;
    private int mPageNum;

    private SalesManSalesPresenter() {
    }

    public static SalesManSalesPresenter newInstance() {
        return new SalesManSalesPresenter();
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
                .create().getData(), "111003", email, Utils.getExportObserver(mView));
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    private void load(boolean showLoading) {
        Report.querySalesmanSalesAchievement(mView.getReq()
                .put("pageNum", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .create(), new SimpleObserver<SalesManSalesResp>(mView, showLoading) {
            @Override
            public void onSuccess(SalesManSalesResp salesManSalesResp) {
                mView.setData(salesManSalesResp, mPageNum > 1);
                if (CommonUtils.isEmpty(salesManSalesResp.getRecords())) return;
                mPageNum++;
            }
        });
    }

    @Override
    public void register(ISalesManSalesContract.ISalesManSalesView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
