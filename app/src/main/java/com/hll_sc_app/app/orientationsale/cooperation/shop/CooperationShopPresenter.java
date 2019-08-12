package com.hll_sc_app.app.orientationsale.cooperation.shop;

import com.hll_sc_app.api.CommonService;
import com.hll_sc_app.app.agreementprice.quotation.add.purchaser.shop.PurchaserShopListActivity;

import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.cooperation.CooperationShopListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class CooperationShopPresenter implements ICooperationShopContract.ICooperationShopPresenter {

    private ICooperationShopContract.ICooperationShopView mView;

    static CooperationShopPresenter newInstance() {
        return new CooperationShopPresenter();
    }

    @Override
    public void start() {
        // no-op
    }

    @Override
    public void register(ICooperationShopContract.ICooperationShopView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryPurchaserShopList(String purchaserId) {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("purchaserID", purchaserId)
                .put("searchParams", mView.getSearchParam())
                .put("actionType","targetedSelling")
                .put("groupID", UserConfig.getGroupID())
                .put("pageNo", "1")
                .put("pageSize", "1000")
                .create();
        CommonService.INSTANCE.listCooperationShop(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<CooperationShopListResp>() {
                    @Override
                    public void onSuccess(CooperationShopListResp result) {
                        List<PurchaserShopBean> list = result.getShopList();
                        if (!CommonUtils.isEmpty(list)) {
                            PurchaserShopBean shopBean = new PurchaserShopBean();
                            shopBean.setShopName(PurchaserShopListActivity.STRING_ALL);
                            list.add(0, shopBean);
                        }
                        mView.showPurchaserShopList(list);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showToast(e.getMessage());
                    }
                });
    }
}
