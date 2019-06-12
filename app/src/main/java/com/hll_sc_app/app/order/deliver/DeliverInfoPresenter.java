package com.hll_sc_app.app.order.deliver;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.order.deliver.DeliverInfoResp;
import com.hll_sc_app.bean.order.deliver.DeliverShopResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/11
 */

public class DeliverInfoPresenter implements IDeliverInfoContract.IDeliverInfoPresenter {
    private IDeliverInfoContract.IDeliverInfoView mView;

    private DeliverInfoPresenter() {
    }

    public static DeliverInfoPresenter newInstance() {
        return new DeliverInfoPresenter();
    }

    @Override
    public void requestShopList(String specID) {
        Order.getDeliverShop(specID, new SimpleObserver<List<DeliverShopResp>>(mView) {
            @Override
            public void onSuccess(List<DeliverShopResp> list) {
                mView.updateShopList(list);
            }
        });
    }

    @Override
    public void start() {
        Order.getDeliverInfo(new SimpleObserver<List<DeliverInfoResp>>(mView) {
            @Override
            public void onSuccess(List<DeliverInfoResp> list) {
                mView.updateInfoList(list);
            }
        });
    }

    @Override
    public void register(IDeliverInfoContract.IDeliverInfoView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
