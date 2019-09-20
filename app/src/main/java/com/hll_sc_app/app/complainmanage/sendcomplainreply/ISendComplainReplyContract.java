package com.hll_sc_app.app.complainmanage.sendcomplainreply;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

public interface ISendComplainReplyContract {
    interface IView extends ILoadView {
        String getComplaintID();

        String getReply();

        void sendSuccess();
    }

    interface IPresent extends IPresenter<IView> {
        void sendComplainReply();
    }


}
