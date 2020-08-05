package com.hll_sc_app.app.report.marketing.detail;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.marketing.MarketingDetailResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/28
 */

class MarketingDetailPresenter implements IMarketingDetailContract.IMarketingDetailPresenter {
    private IMarketingDetailContract.IMarketingDetailView mView;

    private MarketingDetailPresenter() {
    }

    public static MarketingDetailPresenter newInstance() {
        return new MarketingDetailPresenter();
    }

    @Override
    public void start() {
        Report.queryMarketingDetail(mView.getReq().create(), new SimpleObserver<MarketingDetailResp>(mView) {
            @Override
            public void onSuccess(MarketingDetailResp resp) {
                mView.setData(resp);
            }
        });
    }

    @Override
    public void register(IMarketingDetailContract.IMarketingDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
