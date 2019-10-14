package com.hll_sc_app.app.complainmanage.add.productlist;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.order.OrderResp;

public interface ISelectProductListContract {
    interface IView extends ILoadView {

        String getSearchWords();

        String getSubBillNo();

        void querySuccess(OrderResp orderResp);

    }

    interface IPresent extends IPresenter<IView> {
        void queryList();
    }

}
