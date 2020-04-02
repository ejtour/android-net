package com.hll_sc_app.app.contractmanage.selectpurchaser;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.window.NameValue;

import java.util.List;

public interface ISelectPurchaserContract {

    interface IView extends ILoadView {
        void querySuccess(List<NameValue> purchaseBeanList, boolean isMore);

        String getSearchText();

        int getListType();
    }

    interface IPresent extends IPresenter<IView> {
        void queryList(boolean isLoading);

        void searchIntentionCustomer(boolean isLoading);

        void refresh();

        void quereMore();

    }
}
