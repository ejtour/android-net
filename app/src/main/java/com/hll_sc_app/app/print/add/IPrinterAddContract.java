package com.hll_sc_app.app.print.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.print.PrinterBean;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2021/4/22
 */
interface IPrinterAddContract {
    interface IPrinterAddView extends ILoadView{
        void success();
    }

    interface IPrinterAddPresenter extends IPresenter<IPrinterAddView>{
        void save(PrinterBean printerBean);
    }
}
