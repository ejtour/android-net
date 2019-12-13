package com.hll_sc_app.app.report.receive.details;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.receive.ReceiveDiffDetailsResp;
import com.hll_sc_app.impl.IExportView;

/**
 * @author chukun
 * @since 2019/8/15
 */

public interface IReceiveDiffDetailsContract {
    interface IReceiveDiffDetailsView extends IExportView {
        void setData(ReceiveDiffDetailsResp resp, boolean append);
        BaseMapReq.Builder getReq();
    }

    interface IReceiveDiffDetailsPresenter extends IPresenter<IReceiveDiffDetailsView> {
        void refresh();
        void loadMore();
        void export(String email);
    }
}
