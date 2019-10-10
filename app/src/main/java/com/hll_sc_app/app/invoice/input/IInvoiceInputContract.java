package com.hll_sc_app.app.invoice.input;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.invoice.InvoiceHistoryBean;
import com.hll_sc_app.bean.invoice.InvoiceMakeReq;
import com.hll_sc_app.bean.invoice.InvoiceMakeResp;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/14
 */

public interface IInvoiceInputContract {
    interface IInvoiceInputView extends ILoadView {
        void showInvoiceHistory(List<InvoiceHistoryBean> list);

        void makeSuccess(InvoiceMakeResp resp);
    }

    interface IInvoiceInputPresenter extends IPresenter<IInvoiceInputContract.IInvoiceInputView> {
        void reqInvoiceHistory(int titleType);

        void makeInvoice(InvoiceMakeReq req);

        void search(String text);
    }
}
