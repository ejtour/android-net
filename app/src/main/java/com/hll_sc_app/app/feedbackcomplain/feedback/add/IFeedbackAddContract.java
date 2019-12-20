package com.hll_sc_app.app.feedbackcomplain.feedback.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

import java.util.List;

public interface IFeedbackAddContract {
    interface IView extends ILoadView {
        void addSuccess();

        String getParentID();

        String getFeedbackID();
    }

    interface IPresent extends IPresenter<IView> {
        void addFeedback(String content,List<String> imgs);
    }
}
