package com.hll_sc_app.app.print.preview;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.print.PrintPreviewResp;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2021/4/23
 */
interface IPrintPreviewContract {
    interface IPrintPreviewView extends ILoadView {
        void setData(PrintPreviewResp resp);

        void printSendSuccess(String billNo);
    }

    interface IPrintPreviewPresenter extends IPresenter<IPrintPreviewView> {
        void loadTemplate(String templateID);

        void loadBill(String subBillNo);

        void print(String pdfUrl, String html, int copies, String deviceID);
    }
}
