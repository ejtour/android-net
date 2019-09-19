package com.hll_sc_app.app.complainmanage.history;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.complain.ComplainHistoryResp;

public interface IComplainHistoryContract {
    interface IView extends ILoadView {
        String getComplaintID();

        void queryHistorySucess(ComplainHistoryResp complainHistoryResp);
    }

    interface IPresent extends IPresenter<IView> {
        void queryHistory();
    }


}
