package com.hll_sc_app.app.goodsdemand.detail;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Other;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/23
 */

public class GoodsDemandDetailPresenter implements IGoodsDemandDetailContract.IGoodsDemandDetailPresenter {
    private IGoodsDemandDetailContract.IGoodsDemandDetailView mView;

    private GoodsDemandDetailPresenter() {
    }

    public static GoodsDemandDetailPresenter newInstance() {
        return new GoodsDemandDetailPresenter();
    }

    @Override
    public void register(IGoodsDemandDetailContract.IGoodsDemandDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void start() {
        Other.queryGoodsDemand(1, 0, mView.getID(), new SimpleObserver<SingleListResp<GoodsDemandBean>>(mView) {
            @Override
            public void onSuccess(SingleListResp<GoodsDemandBean> goodsDemandBeanSingleListResp) {
                if (CommonUtils.isEmpty(goodsDemandBeanSingleListResp.getRecords())) return;
                mView.setData(goodsDemandBeanSingleListResp.getRecords().get(0));
            }
        });
    }

    @Override
    public void reply(String content, @IGoodsDemandDetailContract.TARGET int target) {
        GoodsDemandBean bean = mView.getBean();
        if (bean == null) return;
        Other.replyGoodsDemand(bean.getId(),
                target == IGoodsDemandDetailContract.TARGET.CUSTOMER ? content : "",
                target == IGoodsDemandDetailContract.TARGET.SALE ? content : "",
                bean.getPurchaserID(),
                bean.getStatus(),
                new SimpleObserver<Object>(mView) {
                    @Override
                    public void onSuccess(Object o) {
                        mView.replySuccess();
                    }
                });
    }

    @Override
    public void cancel() {
        GoodsDemandBean bean = mView.getBean();
        if (bean == null) return;
        Other.replyGoodsDemand(bean.getId(), "", "",
                bean.getPurchaserID(), 4, new SimpleObserver<Object>(mView) {
                    @Override
                    public void onSuccess(Object o) {
                        mView.statusChanged();
                    }
                });
    }
}
