package com.hll_sc_app.app.report.customerreceive;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.customerreceive.ReceiveCustomerBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/30
 */

public interface ICustomerReceiveContract {
    interface ICustomerReceiveView extends ILoadView {
        void setData(List<ReceiveCustomerBean> list, boolean append);

        BaseMapReq.Builder getReq();
    }

    interface ICustomerReceivePresenter extends IPresenter<ICustomerReceiveView> {
        void refresh();

        void loadMore();
    }
}
