package com.hll_sc_app.app.cardmanage.recharge;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.cardmanage.CardManageBean;

public interface ICardManageRechargeContract {

    interface IView extends ILoadView {
        void toNextStep(CardManageBean bean);

        void recharge();

        String getCardId();

        void rechargeSuccess();
    }



    interface IPresent extends IPresenter<IView> {
        void recharge(String cashBalance, String giftBalance, String remark);
    }
}
