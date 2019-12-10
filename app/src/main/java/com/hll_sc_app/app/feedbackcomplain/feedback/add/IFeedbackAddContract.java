package com.hll_sc_app.app.feedbackcomplain.feedback.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.complain.FeedbackListResp;

import java.util.List;

public interface IFeedbackAddContract {
    interface IView extends ILoadView {
        void addSuccess();
    }

    interface IPresent extends IPresenter<IView> {
        void addFeedback(String content,List<String> imgs);
    }
}
