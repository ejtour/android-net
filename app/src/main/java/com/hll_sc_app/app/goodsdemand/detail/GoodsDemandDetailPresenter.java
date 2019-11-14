package com.hll_sc_app.app.goodsdemand.detail;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Other;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/23
 */

public class GoodsDemandDetailPresenter implements IGoodsDemandDetailContract.IGoodsDemandDetailPresenter {
    private IGoodsDemandDetailContract.IGoodsDemandDetailView mView;
    private GoodsDemandBean mBean;

    private GoodsDemandDetailPresenter() {
    }

    public static GoodsDemandDetailPresenter newInstance(GoodsDemandBean bean) {
        GoodsDemandDetailPresenter presenter = new GoodsDemandDetailPresenter();
        presenter.mBean = bean;
        return presenter;
    }

    @Override
    public void register(IGoodsDemandDetailContract.IGoodsDemandDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void reply(String content, @IGoodsDemandDetailContract.TARGET int target) {
        Other.replyGoodsDemand(mBean.getId(),
                target == IGoodsDemandDetailContract.TARGET.CUSTOMER ? content : "",
                target == IGoodsDemandDetailContract.TARGET.SALE ? content : "",
                mBean.getPurchaserID(),
                mBean.getStatus(),
                new SimpleObserver<Object>(mView) {
                    @Override
                    public void onSuccess(Object o) {
                        mView.replySuccess();
                    }
                });
    }
}
