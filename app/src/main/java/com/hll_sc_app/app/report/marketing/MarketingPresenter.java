package com.hll_sc_app.app.report.marketing;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.marketing.MarketingResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/27
 */

class MarketingPresenter implements IMarketingContract.IMarketingPresenter {
    private IMarketingContract.IMarketingView mView;
    private int mPageNum;

    private MarketingPresenter() {
    }

    public static MarketingPresenter newInstance() {
        return new MarketingPresenter();
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void register(IMarketingContract.IMarketingView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    private void load(boolean showLoading) {
        Report.queryMarketing(mView.getReq()
                .put("pageNum", String.valueOf(mPageNum))
                .put("pageSize", "20").create(), new SimpleObserver<MarketingResp>(mView, showLoading) {
            @Override
            public void onSuccess(MarketingResp marketingResp) {
                mView.setData(marketingResp, mPageNum > 1);
                if (CommonUtils.isEmpty(marketingResp.getList())) return;
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
