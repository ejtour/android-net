package com.hll_sc_app.app.print.add;

import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.print.PrinterBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Print;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2021/4/22
 */
class PrinterAddPresenter implements IPrinterAddContract.IPrinterAddPresenter {
    private IPrinterAddContract.IPrinterAddView mView;

    @Override
    public void save(PrinterBean printerBean) {
        Print.addPrinter(printerBean, new SimpleObserver<MsgWrapper<Object>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                mView.success();
            }
        });
    }

    @Override
    public void register(IPrinterAddContract.IPrinterAddView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
