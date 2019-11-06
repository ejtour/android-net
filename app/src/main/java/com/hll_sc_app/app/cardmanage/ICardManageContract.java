package com.hll_sc_app.app.cardmanage;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.cardmanage.CardManageBean;

import java.util.List;

public interface ICardManageContract {

    interface IView extends ILoadView {
        String getSearchText();
    }

    interface IFragment extends ILoadView {

        void querySuccess(List<CardManageBean> cardManageBeans, boolean isMore);

        int getCardStatus();

        IView getCardManageActivity();
    }

    interface IPresent extends IPresenter<IFragment> {
        void queryList(boolean isLoading);

        void refresh();

        void queryMore();

        int getPageSize();
    }
}
