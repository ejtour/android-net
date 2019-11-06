package com.hll_sc_app.app.cardmanage.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.common.PurchaserBean;

import java.util.List;

public interface ISelectPurchaserContract {

    interface IView extends ILoadView {
        void querySuccess(List<PurchaserBean> purchaseBeanList);

        String getSearchText();
    }

    interface IPresent extends IPresenter<IView> {
        void queryList(boolean isLoading);

    }
}
