package com.hll_sc_app.app.report.lack.customer.details;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.lack.CustomerLackDetailsBean;
import com.hll_sc_app.impl.IExportView;

import java.util.List;

/**
 * @author chukun
 * @since 2019/7/23
 */

public interface ICustomerLackDetailsContract {
    interface ICustomerLackDetailsView extends IExportView {
        void setList(List<CustomerLackDetailsBean> beans, boolean append);

        BaseMapReq.Builder getReq();
    }

    interface ICustomerLackDetailPresenter extends IPresenter<ICustomerLackDetailsView> {
        void refresh();

        void loadMore();

        void export(String email);
    }
}
