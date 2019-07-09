package com.hll_sc_app.app.agreementprice.quotation.add.purchaser.shop;

import com.hll_sc_app.api.AgreementPriceService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 选择合作采购商
 *
 * @author zhuyingsong
 * @date 2019/7/9
 */
public class PurchaserShopPresenter implements PurchaserShopListContract.IPurchaserListPresenter {
    private PurchaserShopListContract.IPurchaserListView mView;

    static PurchaserShopPresenter newInstance() {
        return new PurchaserShopPresenter();
    }

    @Override
    public void start() {
        // no-op
    }

    @Override
    public void register(PurchaserShopListContract.IPurchaserListView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryPurchaserShopList(String purchaserId, String searchParam) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("purchaserID", purchaserId)
            .put("searchParam", searchParam)
            .create();
        AgreementPriceService.INSTANCE.queryCooperationPurchaserShopList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<List<PurchaserShopBean>>() {
                @Override
                public void onSuccess(List<PurchaserShopBean> result) {
                    if (!CommonUtils.isEmpty(result)) {
                        PurchaserShopBean shopBean = new PurchaserShopBean();
                        shopBean.setShopName("全部");
                        result.add(0, shopBean);
                    }
                    mView.showPurchaserShopList(result);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showToast(e.getMessage());
                }
            });
    }

}
