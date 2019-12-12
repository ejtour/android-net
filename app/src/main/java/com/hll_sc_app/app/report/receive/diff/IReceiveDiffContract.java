package com.hll_sc_app.app.report.receive.diff;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.receive.ReceiveDiffResp;
import com.hll_sc_app.impl.IExportView;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/12
 */

public interface IReceiveDiffContract {
    interface IReceiveDiffView extends IExportView {
        void setData(ReceiveDiffResp resp, boolean append);

        BaseMapReq.Builder getReq();
    }

    interface IReceiveDiffPresenter extends IPresenter<IReceiveDiffView> {
        void export(String email);

        void refresh();

        void loadMore();
    }
}
