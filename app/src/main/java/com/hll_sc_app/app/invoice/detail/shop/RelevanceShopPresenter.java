package com.hll_sc_app.app.invoice.detail.shop;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.invoice.InvoiceShopResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Invoice;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/23
 */

public class RelevanceShopPresenter implements IRelevanceShopContract.IRelevanceShopPresenter {
    private int mPageNum;
    private String mID;
    private IRelevanceShopContract.IRelevanceShopView mView;

    public static RelevanceShopPresenter newInstance(String invoiceID) {
        RelevanceShopPresenter presenter = new RelevanceShopPresenter();
        presenter.mID = invoiceID;
        return presenter;
    }

    private RelevanceShopPresenter() {
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    private void load(boolean showLoading) {
        Invoice.reqRelevanceShopList(mID, mPageNum, new SimpleObserver<InvoiceShopResp>(mView, showLoading) {
            @Override
            public void onSuccess(InvoiceShopResp invoiceShopResp) {
                mView.setListData(invoiceShopResp.getList(), mPageNum > 1);
                if (CommonUtils.isEmpty(invoiceShopResp.getList())) return;
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

    @Override
    public void register(IRelevanceShopContract.IRelevanceShopView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
