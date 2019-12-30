package com.hll_sc_app.app.report.customerreceive;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.customerreive.ReceiveCustomerResp;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/30
 */

public interface ICustomerReceiveContract {
    interface ICustomerReceiveView extends ILoadView {
        void setData(ReceiveCustomerResp resp, boolean append);
    }

    interface ICustomerReceivePresenter extends IPresenter<ICustomerReceiveView> {
        void refresh();

        void loadMore();
    }
}
