package com.hll_sc_app.app.cardmanage.cardlog;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.cardmanage.CardLogResp;
import com.hll_sc_app.bean.common.PurchaserBean;

import java.util.List;

public interface ICardLogContract {

    interface IView extends ILoadView {
        void querySuccess(List<CardLogResp.CardLogBean> cardLogBeans,boolean isMore);

        String getStartDate();

        String getEndDate();

        String getCardNo();
    }

    interface IPresent extends IPresenter<IView> {
        void queryLogList(boolean isLoading);

        void refresh();

        void getMore();

        int getPageSize();
    }
}
