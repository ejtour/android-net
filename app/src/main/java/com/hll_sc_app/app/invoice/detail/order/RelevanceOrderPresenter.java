package com.hll_sc_app.app.invoice.detail.order;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.invoice.InvoiceOrderResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Invoice;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/17
 */

public class RelevanceOrderPresenter implements IRelevanceOrderContract.IRelevanceOrderPresenter {
    private IRelevanceOrderContract.IRelevanceOrderView mView;
    private String mID;
    private int mPageNum;

    public static RelevanceOrderPresenter newInstance(String id) {
        RelevanceOrderPresenter presenter = new RelevanceOrderPresenter();
        presenter.mID = id;
        return presenter;
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        reqRelevanceOrder(false);
    }

    @Override
    public void loadMore() {
        reqRelevanceOrder(false);
    }

    private void reqRelevanceOrder(boolean showLoading) {
        Invoice.reqRelevanceOrderList(mID, mPageNum, new SimpleObserver<InvoiceOrderResp>(mView) {
            @Override
            public void onSuccess(InvoiceOrderResp resp) {
                mView.setListData(resp.getList(), mPageNum > 1);
                if (CommonUtils.isEmpty(resp.getList())) return;
                mPageNum++;
            }
        });
    }

    @Override
    public void start() {
        mPageNum = 1;
        reqRelevanceOrder(true);
    }

    @Override
    public void register(IRelevanceOrderContract.IRelevanceOrderView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
