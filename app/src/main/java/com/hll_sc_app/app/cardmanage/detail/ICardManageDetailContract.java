package com.hll_sc_app.app.cardmanage.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.cardmanage.CardManageBean;

import java.util.List;

public interface ICardManageDetailContract {

    interface IView extends ILoadView {
    }



    interface IPresent extends IPresenter<IView> {

    }
}
