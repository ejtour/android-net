package com.hll_sc_app.app.report.credit.details;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.credit.CreditDetailsResp;
import com.hll_sc_app.impl.IExportView;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/18
 */

public interface ICreditDetailsContract {
    interface ICreditDetailsView extends IExportView {
        void setData(CreditDetailsResp resp, boolean append);

        BaseMapReq.Builder getReq();

        boolean isDaily();
    }

    interface ICreditDetailsPresenter extends IPresenter<ICreditDetailsView> {
        void refresh();

        void loadMore();

        void export(String email);
    }
}
