package com.hll_sc_app.app.complainmanage.detail;

import android.support.v4.util.ArrayMap;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.complain.ComplainDetailResp;
import com.hll_sc_app.bean.complain.ComplainStatusResp;

import java.util.List;
import java.util.Map;

public interface IComplainMangeDetailContract {
    interface IView extends ILoadView {
        String getComplaintID();

        void showComplainStatus(ComplainStatusResp complainStatusResp);

        void showComplainDetail(ComplainDetailResp complainDetailResp);
    }

    interface IPresent extends IPresenter<IView> {
        void queryComplainStatus();

        void queryComplainDetail();
    }


}
