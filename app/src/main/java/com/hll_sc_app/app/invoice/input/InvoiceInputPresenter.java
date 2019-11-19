package com.hll_sc_app.app.invoice.input;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.invoice.InvoiceHistoryBean;
import com.hll_sc_app.bean.invoice.InvoiceHistoryResp;
import com.hll_sc_app.bean.invoice.InvoiceMakeReq;
import com.hll_sc_app.bean.invoice.InvoiceMakeResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Invoice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/14
 */

public class InvoiceInputPresenter implements IInvoiceInputContract.IInvoiceInputPresenter {
    private IInvoiceInputContract.IInvoiceInputView mView;
    private InvoiceHistoryResp mHistoryResp;

    public static InvoiceInputPresenter newInstance() {
        return new InvoiceInputPresenter();
    }

    private InvoiceInputPresenter() {
    }

    @Override
    public void reqInvoiceHistory(int titleType) {
        Invoice.getInvoiceHistory(titleType, new SimpleObserver<InvoiceHistoryResp>(mView) {
            @Override
            public void onSuccess(InvoiceHistoryResp invoiceHistoryResp) {
                mHistoryResp = invoiceHistoryResp;
            }
        });
    }

    @Override
    public void makeInvoice(InvoiceMakeReq req) {
        Invoice.makeInvoice(req, new SimpleObserver<InvoiceMakeResp>(mView) {
            @Override
            public void onSuccess(InvoiceMakeResp invoiceMakeResp) {
                mView.makeSuccess(invoiceMakeResp);
            }
        });
    }

    @Override
    public void search(String text) {
        if (mHistoryResp == null || CommonUtils.isEmpty(mHistoryResp.getRecords())) return;
        List<InvoiceHistoryBean> list = new ArrayList<>();
        if (!TextUtils.isEmpty(text))
            for (InvoiceHistoryBean record : mHistoryResp.getRecords()) {
                if (record.getInvoiceTitle().contains(text)) list.add(record);
            }
        mView.showInvoiceHistory(list);
    }

    @Override
    public void register(IInvoiceInputContract.IInvoiceInputView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
