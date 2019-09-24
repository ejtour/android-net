package com.hll_sc_app.app.complainmanage.purchaserlist;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.complain.ReportFormSearchResp;

public interface ISelectPurchaserListContract {
    interface IView extends ILoadView {
        void queySuccess(ReportFormSearchResp resp, boolean isMore);

        String getSearchWords();

        String getPurchaserId();

        int getSearchType();
    }

    interface IPresent extends IPresenter<IView> {

        void queryList(boolean isLoading);

        void getMore();

        void refresh();

        int getPageSize();
    }


}
