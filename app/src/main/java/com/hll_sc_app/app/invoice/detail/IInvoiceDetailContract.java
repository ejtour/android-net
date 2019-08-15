package com.hll_sc_app.app.invoice.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.invoice.InvoiceBean;

import java.io.File;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/15
 */

public interface IInvoiceDetailContract {
    interface IInvoiceDetailView extends ILoadView {
        void updateData(InvoiceBean bean);

        void actionSuccess();

        void settleSuccess();

        void showImage(String url);
    }

    interface IInvoiceDetailPresenter extends IPresenter<IInvoiceDetailView> {
        void doAction(int action, String invoiceNO, double invoicePrice, String invoiceVoucher, String rejectReason);

        void settle(String id);

        void imageUpload(File file);
    }
}
