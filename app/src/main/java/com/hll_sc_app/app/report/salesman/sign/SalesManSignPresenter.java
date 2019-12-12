package com.hll_sc_app.app.report.salesman.sign;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.salesman.SalesManAchievementReq;
import com.hll_sc_app.bean.report.salesman.SalesManSignResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Utils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/12
 */

public class SalesManSignPresenter implements ISalesManSignContract.ISalesManSignPresenter {
    private ISalesManSignContract.ISalesManSignView mView;
    private int mPageNum;

    private SalesManSignPresenter() {
    }

    public static SalesManSignPresenter newInstance() {
        return new SalesManSignPresenter();
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
        Report.exportReport(mView.getReq(), "111009", email, Utils.getExportObserver(mView));
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    private void load(boolean showLoading) {
        SalesManAchievementReq req = mView.getReq();
        req.setPageNum(mPageNum);
        req.setPageSize(20);
        Report.querySalesmanSignAchievement(req, new SimpleObserver<SalesManSignResp>(mView, showLoading) {
            @Override
            public void onSuccess(SalesManSignResp salesManSignResp) {
                mView.setData(salesManSignResp, mPageNum > 1);
                if (CommonUtils.isEmpty(salesManSignResp.getRecords())) return;
                mPageNum++;
            }
        });
    }

    @Override
    public void register(ISalesManSignContract.ISalesManSignView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
