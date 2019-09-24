package com.hll_sc_app.app.complainmanage.ordernumberlist;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.order.OrderResp;

import java.util.List;

public interface ISelectOrderListContract {
    interface IView extends ILoadView {
        void queySuccess(List<OrderResp> orderResps, boolean isMore);

        String getPurchaserId();

        String getShopId();

        String getCreateTimeStart();

        String getCreateTimeEnd();
    }

    interface IPresent extends IPresenter<IView> {
        void queryOrderList(boolean isLoading);

        void getMore();

        void refresh();

        int getPageSize();
    }


}
