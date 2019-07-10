package com.hll_sc_app.app.aftersales.negotiationhistory;


import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.aftersales.NegotiationHistoryResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.AfterSales;

public class NegotiationHistoryPresenter implements INegotiationHistoryContract.INegotiationHistoryPresenter {
    private INegotiationHistoryContract.INegotiationHistoryView mView;

    public static NegotiationHistoryPresenter newInstance() {
        return new NegotiationHistoryPresenter();
    }

    private NegotiationHistoryPresenter() {
    }

    @Override
    public void getHistoryList(String refundBillID, String subBillID) {
        AfterSales.getNegotiationHistory(refundBillID, subBillID, new SimpleObserver<NegotiationHistoryResp>(mView) {
            @Override
            public void onSuccess(NegotiationHistoryResp negotiationHistoryResp) {
                mView.showHistoryList(negotiationHistoryResp);
            }
        });
    }

    @Override
    public void start() {
        // no-op
    }

    @Override
    public void register(INegotiationHistoryContract.INegotiationHistoryView view) {
        mView = CommonUtils.checkNotNull(view);
    }
}
