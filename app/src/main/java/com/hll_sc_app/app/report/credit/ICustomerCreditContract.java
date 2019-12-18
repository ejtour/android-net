package com.hll_sc_app.app.report.credit;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.credit.CreditBean;
import com.hll_sc_app.impl.IExportView;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/18
 */

public interface ICustomerCreditContract {
    interface ICustomerCreditView extends IExportView {
        void setData(List<CreditBean> list, boolean append);

        BaseMapReq.Builder getReq();
    }

    interface ICustomerCreditPresenter extends IPresenter<ICustomerCreditView> {
        void refresh();

        void loadMore();

        void export(String email);
    }
}
