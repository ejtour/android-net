package com.hll_sc_app.app.deliverymanage.minimum.purchaser.shop;

import com.hll_sc_app.api.DeliveryManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.delivery.ShopMinimumBean;
import com.hll_sc_app.bean.delivery.ShopMinimumSelectBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 选择合作采购商门店-最小起购量设置
 *
 * @author zhuyingsong
 * @date 2019/7/31
 */
public class ShopMinimumPresenter implements ShopMinimumContract.IShopMinimumPresenter {
    private ShopMinimumContract.IShopMinimumView mView;
    private List<String> mListSelect;

    static ShopMinimumPresenter newInstance() {
        return new ShopMinimumPresenter();
    }

    @Override
    public void start() {
        querySelectShop();
    }

    @Override
    public void register(ShopMinimumContract.IShopMinimumView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void querySelectShop() {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("purchaserID", mView.getPurchaserId())
            .put("supplyID", UserConfig.getGroupID())
            .create();
        DeliveryManageService.INSTANCE
            .querySelectShop(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<ShopMinimumSelectBean>() {
                @Override
                public void onSuccess(ShopMinimumSelectBean bean) {
                    mListSelect = bean.getPurchaserShopList();
                    queryAreaShopList();
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public void queryAreaShopList() {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("purchaserID", mView.getPurchaserId())
            .create();
        DeliveryManageService.INSTANCE
            .queryAreaShopList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<List<ShopMinimumBean>>() {
                @Override
                public void onSuccess(List<ShopMinimumBean> list) {
                    processData(list);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    private void processData(List<ShopMinimumBean> list) {
        Map<String, String> map = new HashMap<>();
        if (!CommonUtils.isEmpty(mListSelect)) {
            for (String s : mListSelect) {
                map.put(s, s);
            }
        }
        if (!CommonUtils.isEmpty(list)) {
            for (ShopMinimumBean bean : list) {
                List<PurchaserShopBean> shopBeans = bean.getPurchaserShops();
                if (!CommonUtils.isEmpty(shopBeans)) {
                    for (PurchaserShopBean shopBean : shopBeans) {
                        if (map.containsKey(shopBean.getShopID())) {
                            shopBean.setIsActive(ShopMinimumActivity.DISABLE);
                        }
                    }
                }
                PurchaserShopBean shopBean = new PurchaserShopBean();
                shopBean.setShopName("全部");
                shopBeans.add(0, shopBean);
            }
            mView.showPurchaserShopList(list);
        }
    }
}
