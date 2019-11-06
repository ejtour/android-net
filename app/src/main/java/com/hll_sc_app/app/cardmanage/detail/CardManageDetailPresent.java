package com.hll_sc_app.app.cardmanage.detail;

import com.hll_sc_app.api.CardManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.cardmanage.CardManageListResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class CardManageDetailPresent implements ICardManageDetailContract.IPresent {

    private ICardManageDetailContract.IView mView;

    public static CardManageDetailPresent newInstance() {
        return new CardManageDetailPresent();
    }

    @Override
    public void register(ICardManageDetailContract.IView view) {
        mView = view;
    }

}
