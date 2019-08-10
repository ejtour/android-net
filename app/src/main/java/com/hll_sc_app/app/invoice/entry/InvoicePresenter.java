package com.hll_sc_app.app.invoice.entry;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.invoice.InvoiceListResp;
import com.hll_sc_app.bean.invoice.InvoiceParam;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Invoice;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/10
 */

public class InvoicePresenter implements IInvoiceContract.IInvoicePresenter {
    private int mStatus;
    private int mPageNum;
    private InvoiceParam mParam;
    private IInvoiceContract.IInvoiceView mView;

    public static InvoicePresenter newInstance(int status, InvoiceParam param) {
        InvoicePresenter presenter = new InvoicePresenter();
        presenter.mStatus = status;
        presenter.mParam = param;
        return presenter;
    }

    @Override
    public void loadMore() {
        requestInvoiceList(false);
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        requestInvoiceList(false);
    }

    private void requestInvoiceList(boolean showLoading) {
        Invoice.getInvoiceList(mStatus, mPageNum,
                mParam.getFormatStartTime(), mParam.getFormatEndTime(),
                new SimpleObserver<InvoiceListResp>(mView, showLoading) {
                    @Override
                    public void onSuccess(InvoiceListResp invoiceListResp) {
                        mView.setListData(invoiceListResp.getRecords(), mPageNum > 1);
                        if (!CommonUtils.isEmpty(invoiceListResp.getRecords())) mPageNum++;
                    }
                });
    }

    @Override
    public void start() {
        mPageNum = 1;
        requestInvoiceList(true);
    }

    @Override
    public void register(IInvoiceContract.IInvoiceView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
