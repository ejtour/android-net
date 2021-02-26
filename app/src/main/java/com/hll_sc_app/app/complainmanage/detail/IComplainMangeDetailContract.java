package com.hll_sc_app.app.complainmanage.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.complain.ComplainDetailResp;
import com.hll_sc_app.bean.complain.ComplainStatusResp;

public interface IComplainMangeDetailContract {
    interface IView extends ILoadView {
        String getComplaintID();

        void showComplainStatus(ComplainStatusResp complainStatusResp);

        void showComplainDetail(ComplainDetailResp complainDetailResp);

        void applyPlatformInjectSuccess();

        void changeStatusSuccess(int status);

        void replySuccess();
    }

    interface IPresent extends IPresenter<IView> {
        void queryComplainStatus();

        void queryComplainDetail();

        void applyPlatformInject();

        void changeComplainStatus(int status);

        void sendComplainReply(String reply);
    }


}
