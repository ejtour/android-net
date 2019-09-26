package com.hll_sc_app.app.complainmanage.productlist;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.complain.ReportFormSearchResp;

public interface ISelectProductListContract {
    interface IView extends ILoadView {

        String getSearchWords();


    }

    interface IPresent extends IPresenter<IView> {

        void queryList(boolean isLoading);

        void getMore();

        void refresh();

        int getPageSize();
    }


}
