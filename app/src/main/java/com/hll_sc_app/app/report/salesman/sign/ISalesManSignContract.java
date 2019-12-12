package com.hll_sc_app.app.report.salesman.sign;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.salesman.SalesManAchievementReq;
import com.hll_sc_app.bean.report.salesman.SalesManSignResp;
import com.hll_sc_app.impl.IExportView;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/12
 */

public interface ISalesManSignContract {
    interface ISalesManSignView extends IExportView {
        void setData(SalesManSignResp resp, boolean append);

        SalesManAchievementReq getReq();
    }

    interface ISalesManSignPresenter extends IPresenter<ISalesManSignView> {
        void refresh();

        void loadMore();

        void export(String email);
    }
}
