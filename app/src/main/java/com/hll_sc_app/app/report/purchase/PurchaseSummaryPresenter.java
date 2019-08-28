package com.hll_sc_app.app.report.purchase;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.filter.DateParam;
import com.hll_sc_app.bean.report.purchase.PurchaseSummaryResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;

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
        Report.queryPurchaseSummary(mParam.getFormatStartDate(), mParam.getFormatEndDate(),
                mPageNum, new SimpleObserver<PurchaseSummaryResp>(mView, showLoading) {
                    @Override
                    public void onSuccess(PurchaseSummaryResp purchaseSummaryResp) {
                        mView.setList(purchaseSummaryResp, mPageNum > 1);
                        if (CommonUtils.isEmpty(purchaseSummaryResp.getRecords())) return;
                        mPageNum++;
                    }
                });
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
    public void register(IPurchaseSummaryContract.IPurchaseSummaryView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
