package com.hll_sc_app.app.report.produce;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.produce.ProduceSummaryResp;
import com.hll_sc_app.impl.IExportView;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/28
 */

public interface IProduceSummaryContract {
    interface IProduceSummaryView extends IExportView {
        void setData(ProduceSummaryResp resp, boolean append);
    }

    interface IProduceSummaryPresenter extends IPresenter<IProduceSummaryView> {
        void refresh();

        void loadMore();

        void export(String email);
    }
}
