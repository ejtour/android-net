package com.hll_sc_app.app.report.lack.details;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.lack.LackDetailsResp;
import com.hll_sc_app.impl.IExportView;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/16
 */

public interface ILackDetailsContract {
    interface ILackDetailsView extends IExportView {
        void setData(LackDetailsResp resp, boolean append);
        BaseMapReq.Builder getReq();
    }

    interface ILackDetailsPresenter extends IPresenter<ILackDetailsView> {
        void refresh();
        void loadMore();
        void export(String email);
    }
}
