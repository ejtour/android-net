package com.hll_sc_app.app.report.credit.details;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.credit.CreditDetailsResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Utils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/18
 */

public class CreditDetailsPresenter implements ICreditDetailsContract.ICreditDetailsPresenter {
    private ICreditDetailsContract.ICreditDetailsView mView;
    private int mPageNum;

    public static CreditDetailsPresenter newInstance() {
        return new CreditDetailsPresenter();
    }

    private CreditDetailsPresenter() {
    }

    @Override
    public void start() {
        mPageNum = 1;
        queryList(true);
    }

    private void queryList(boolean showLoading) {
        Report.queryCreditDetails(mView.isDaily(), mView.getReq()
                .put("pageNum", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .create(), new SimpleObserver<CreditDetailsResp>(mView, showLoading) {
            @Override
            public void onSuccess(CreditDetailsResp resp) {
                mView.setData(resp, mPageNum > 1);
                if (!CommonUtils.isEmpty(resp.getRecords())) {
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
                .create().getData(), mView.isDaily() ? "111018" : "111017", email, Utils.getExportObserver(mView, "shopmall-supplier"));
    }

    @Override
    public void register(ICreditDetailsContract.ICreditDetailsView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
