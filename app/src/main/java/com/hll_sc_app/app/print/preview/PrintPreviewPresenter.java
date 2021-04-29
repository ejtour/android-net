package com.hll_sc_app.app.print.preview;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.print.PrintPreviewResp;
import com.hll_sc_app.bean.print.PrintResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Print;

import org.jetbrains.annotations.NotNull;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2021/4/23
 */
class PrintPreviewPresenter implements IPrintPreviewContract.IPrintPreviewPresenter {
    private IPrintPreviewContract.IPrintPreviewView mView;

    @Override
    public void loadTemplate(String templateID) {
        Print.getPreviewSourceCode(templateID, null, getObserver());
    }

    @Override
    public void loadBill(String subBillNo) {
        Print.getPreviewSourceCode(null, subBillNo, getObserver());
    }

    @Override
    public void print(String pdfUrl, String html, int copies, String deviceID) {
        Print.print(pdfUrl, html, copies, deviceID, new SimpleObserver<PrintResp>(mView) {
            @Override
            public void onSuccess(PrintResp printResp) {
                mView.printSendSuccess(printResp.getBillNo());
            }
        });
    }

    @NotNull
    private SimpleObserver<PrintPreviewResp> getObserver() {
        return new SimpleObserver<PrintPreviewResp>(mView) {
            @Override
            public void onSuccess(PrintPreviewResp resp) {
                mView.setData(resp);
            }
        };
    }

    @Override
    public void register(IPrintPreviewContract.IPrintPreviewView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
