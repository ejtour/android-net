package com.hll_sc_app.app.cooperation.detail.details.shop;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 12/15/20.
 */
public class CooperationShopListPresenter implements ICooperationShopListContract.ICooperationShopListPresenter {
    private ICooperationShopListContract.ICooperationShopListView mView;

    @Override
    public void agree(PurchaserShopBean bean) {
        SimpleObserver<MsgWrapper<Object>> observer = new SimpleObserver<MsgWrapper<Object>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                mView.success();
            }
        };
        CooperationPurchaserService
                .INSTANCE
                .editCooperationShop(BaseMapReq.newBuilder()
                        .put("actionType", "agree")
                        .put("groupID", UserConfig.getGroupID())
                        .put("purchaserID", bean.getPurchaserID())
                        .put("shopID", bean.getShopID())
                        .create())
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(observer);
    }

    @Override
    public void register(ICooperationShopListContract.ICooperationShopListView view) {
        mView = view;
    }
}
