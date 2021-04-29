package com.hll_sc_app.app.print.list;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.print.PrinterBean;

import java.util.List;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2021/4/22
 */
interface IPrinterListContract {
    interface IPrinterListView extends ILoadView {
        void setData(List<PrinterBean> list);
    }

    interface IPrinterListPresenter extends IPresenter<IPrinterListView> {
        void delete(String deviceID);
    }
}
