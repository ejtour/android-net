package com.hll_sc_app.app.cardmanage.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.cardmanage.CardManageBean;

public interface ICardManageDetailContract {
    interface IView extends ILoadView {
        CardManageBean getCardBean();

        void changeSuccess(int status);
    }

    interface IPresent extends IPresenter<IView> {
        void changeCardStatus(int status, String remark);
    }
}
