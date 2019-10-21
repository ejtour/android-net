package com.hll_sc_app.app.feedbackcomplain.feedback;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.complain.ComplainListResp;
import com.hll_sc_app.bean.complain.FeedbackListResp;

import java.util.List;

public interface IFeedbackListContract {
    interface IView extends ILoadView {
        void querySuccess(List<FeedbackListResp.FeedbackBean> feedbackBeans, boolean isMore);
    }

    interface IPresent extends IPresenter<IView> {
        int getPageSize();

        void queryList(boolean isLoading);

        void getMore();

        void refresh();
    }
}
