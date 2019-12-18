package com.hll_sc_app.app.report.profit;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.lack.LackDetailsResp;
import com.hll_sc_app.bean.report.profit.ProfitResp;
import com.hll_sc_app.impl.IExportView;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/16
 */

public interface IProfitContract {
    interface IProfitView extends IExportView {
        void setData(ProfitResp resp, boolean append);
        BaseMapReq.Builder getReq();
    }

    interface IProfitPresenter extends IPresenter<IProfitView> {
        void refresh();
        void loadMore();
        void export(String email);
    }
}
