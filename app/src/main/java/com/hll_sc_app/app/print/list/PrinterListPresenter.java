package com.hll_sc_app.app.print.list;

import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.print.PrinterBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Print;

import java.util.List;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2021/4/22
 */
class PrinterListPresenter implements IPrinterListContract.IPrinterListPresenter {
    private IPrinterListContract.IPrinterListView mView;

    @Override
    public void start() {
        Print.queryPrinterList(new SimpleObserver<List<PrinterBean>>(mView) {
            @Override
            public void onSuccess(List<PrinterBean> printerBeans) {
                mView.setData(printerBeans);
            }
        });
    }

    @Override
    public void register(IPrinterListContract.IPrinterListView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void delete(String deviceID) {
        Print.deletePrinter(deviceID, new SimpleObserver<MsgWrapper<Object>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                start();
            }
        });
    }
}
