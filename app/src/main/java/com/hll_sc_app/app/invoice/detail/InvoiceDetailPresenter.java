package com.hll_sc_app.app.invoice.detail;

import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.invoice.InvoiceBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Invoice;
import com.hll_sc_app.rest.Upload;

import java.io.File;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/15
 */

public class InvoiceDetailPresenter implements IInvoiceDetailContract.IInvoiceDetailPresenter {
    private String mID;
    private IInvoiceDetailContract.IInvoiceDetailView mView;

    private InvoiceDetailPresenter() {
    }

    public static InvoiceDetailPresenter newInstance(String id) {
        InvoiceDetailPresenter presenter = new InvoiceDetailPresenter();
        presenter.mID = id;
        return presenter;
    }

    @Override
    public void doAction(int action, String invoiceNO, double invoicePrice, String invoiceVoucher, String rejectReason) {
        Invoice.doAction(action, mID,
                invoiceNO, invoicePrice, invoiceVoucher,
                rejectReason, new SimpleObserver<MsgWrapper<Object>>(true, mView) {
                    @Override
                    public void onSuccess(MsgWrapper<Object> resp) {
                        mView.actionSuccess();
                    }
                });
    }

    @Override
    public void settle(String id) {
        Invoice.settle(id, new SimpleObserver<MsgWrapper<Object>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                start();
            }
        });
    }

    @Override
    public void imageUpload(File file) {
        Upload.imageUpload(file, new SimpleObserver<String>(mView) {
            @Override
            public void onSuccess(String s) {
                mView.showImage(s);
            }
        });
    }

    @Override
    public void modifyInvoiceInfo(String invoiceNO, String invoiceVoucher) {
        Invoice.modifyInvoiceInfo(mID, invoiceNO, invoiceVoucher, new SimpleObserver<MsgWrapper<Object>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                start();
            }
        });
    }

    @Override
    public void start() {
        Invoice.getInvoiceDetail(mID, new SimpleObserver<InvoiceBean>(mView) {
            @Override
            public void onSuccess(InvoiceBean bean) {
                mView.updateData(bean);
            }
        });
    }

    @Override
    public void register(IInvoiceDetailContract.IInvoiceDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
