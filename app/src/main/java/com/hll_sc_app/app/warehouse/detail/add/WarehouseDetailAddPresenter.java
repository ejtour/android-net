package com.hll_sc_app.app.warehouse.detail.add;

import com.hll_sc_app.api.AgreementPriceService;
import com.hll_sc_app.api.WarehouseService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.warehouse.WarehouseShopBean;
import com.hll_sc_app.bean.warehouse.WarehouseShopEditReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/24
 */

class WarehouseDetailAddPresenter implements IWarehouseDetailAddContract.IWarehouseDetailAddPresenter {
    private IWarehouseDetailAddContract.IWarehouseDetailAddView mView;

    private WarehouseDetailAddPresenter() {
    }

    public static WarehouseDetailAddPresenter newInstance() {
        return new WarehouseDetailAddPresenter();
    }

    @Override
    public void confirm(WarehouseShopEditReq req) {
        WarehouseService.INSTANCE
                .editWarehouseShop(new BaseReq<>(req))
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<Object>() {
                    @Override
                    public void onSuccess(Object resp) {
                        mView.success();
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void start() {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("groupID", UserConfig.getGroupID())
                .put("actionType", "addWarehouse")
                .put("pageNum", "1")
                .put("pageSize", "999")
                .put("purchaserID", mView.getPurchaserID())
                .create();
        AgreementPriceService.INSTANCE.queryCooperationPurchaserShopList(req)
                .map(new Precondition<>())
                .map(purchaserShopBeans -> {
                    List<WarehouseShopBean> list = new ArrayList<>();
                    for (PurchaserShopBean bean : purchaserShopBeans) {
                        WarehouseShopBean shopBean = new WarehouseShopBean();
                        shopBean.setIsActive(bean.getIsActive());
                        shopBean.setLinkman(bean.getLinkman());
                        shopBean.setMobile(bean.getMobile());
                        shopBean.setLogoUrl(bean.getImagePath());
                        shopBean.setShopName(bean.getShopName());
                        shopBean.setShopArea(bean.getShopArea());
                        shopBean.setShopAddress(bean.getShopAddress());
                        shopBean.setId(bean.getShopID());
                        list.add(shopBean);
                    }
                    return list;
                })
                .compose(ApiScheduler.getObservableScheduler())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<WarehouseShopBean>>() {
                    @Override
                    public void onSuccess(List<WarehouseShopBean> result) {
                        mView.setData(result);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showToast(e.getMessage());
                    }
                });
    }

    @Override
    public void register(IWarehouseDetailAddContract.IWarehouseDetailAddView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
