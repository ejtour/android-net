package com.hll_sc_app.app.invoice.entry;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.invoice.InvoiceBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/10
 */

public interface IInvoiceContract {
    interface IInvoiceView extends ILoadView {
        void setListData(List<InvoiceBean> list, boolean isMore);
    }

    interface IInvoicePresenter extends IPresenter<IInvoiceView> {
        void loadMore();

        void refresh();
    }
}
