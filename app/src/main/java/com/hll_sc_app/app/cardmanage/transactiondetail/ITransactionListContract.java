package com.hll_sc_app.app.cardmanage.transactiondetail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.cardmanage.CardLogResp;
import com.hll_sc_app.bean.cardmanage.CardTransactionListResp;

import java.util.List;

public interface ITransactionListContract {
    interface IView extends ILoadView {

        String getShopID();

        String getTradeType();

        String getStartDate();

        String getEndDate();

        String getCardNo();

        void queryListSuccess(CardTransactionListResp resp, boolean isMore);
    }

    interface IPresent extends IPresenter<IView> {
        void queryDetailList(boolean isLoading);

        int getPageSize();

        void getMore();

        void refresh();

        void filter();
    }
}
