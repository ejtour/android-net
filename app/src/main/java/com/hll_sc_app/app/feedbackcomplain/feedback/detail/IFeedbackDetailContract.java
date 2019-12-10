package com.hll_sc_app.app.feedbackcomplain.feedback.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.complain.FeedbackDetailResp;

import java.util.List;

public interface IFeedbackDetailContract {
    interface IView extends ILoadView {
        void showDetail(FeedbackDetailResp resp);
    }

    interface IPresent extends IPresenter<IView> {
        void queryFeedback(String id);
    }
}
