package com.hll_sc_app.app.invoice.select.order;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.invoice.InvoiceOrderResp;
import com.hll_sc_app.bean.invoice.InvoiceParam;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Invoice;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/12
 */

public class SelectOrderPresenter implements ISelectOrderContract.ISelectOrderPresenter {
    private String mShopID;
    private ISelectOrderContract.ISelectOrderView mView;
    private int mPageNum;
    private InvoiceParam mParam;

    public static SelectOrderPresenter newInstance(InvoiceParam param, String shopID) {
        SelectOrderPresenter presenter = new SelectOrderPresenter();
        presenter.mParam = param;
        presenter.mShopID = shopID;
        return presenter;
    }

    private SelectOrderPresenter() {
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        reqList(false);
    }

    @Override
    public void loadMore() {
        reqList(false);
    }

    private void reqList(boolean showLoading) {
        Invoice.getRelevanceOrderList(mPageNum,
                mParam.getFormatStartTime(),
                mParam.getFormatEndTime(),
                mShopID,
                new SimpleObserver<InvoiceOrderResp>(mView, showLoading) {
                    @Override
                    public void onSuccess(InvoiceOrderResp invoiceOrderResp) {
                        mView.updateOrderData(invoiceOrderResp, mPageNum > 1);
                        if (CommonUtils.isEmpty(invoiceOrderResp.getList())) return;
                        mPageNum++;
                    }
                });
    }

    @Override
    public void start() {
        mPageNum = 1;
        reqList(true);
    }

    @Override
    public void register(ISelectOrderContract.ISelectOrderView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
