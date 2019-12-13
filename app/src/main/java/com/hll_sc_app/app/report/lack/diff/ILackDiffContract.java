package com.hll_sc_app.app.report.lack.diff;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.lack.LackDiffResp;
import com.hll_sc_app.impl.IExportView;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/12
 */

public interface ILackDiffContract {
    interface ILackDiffView extends IExportView {
        void setData(LackDiffResp resp, boolean append);

        BaseMapReq.Builder getReq();
    }

    interface ILackDiffPresenter extends IPresenter<ILackDiffView> {
        void export(String email);

        void refresh();

        void loadMore();
    }
}
