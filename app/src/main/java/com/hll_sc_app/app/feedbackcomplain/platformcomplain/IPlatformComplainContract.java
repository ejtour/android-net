package com.hll_sc_app.app.feedbackcomplain.platformcomplain;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.complain.ComplainListResp;

import java.util.List;

public interface IPlatformComplainContract {
    interface IView extends ILoadView {
        void querySuccess(List<ComplainListResp.ComplainListBean> complainListBeans, boolean isMore);
    }

    interface IPresent extends IPresenter<IView> {
        int getPageSize();

        void queryList(boolean isLoading);

        void getMore();

        void refresh();
    }
}
