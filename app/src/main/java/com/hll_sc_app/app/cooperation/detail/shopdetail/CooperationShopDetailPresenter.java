package com.hll_sc_app.app.cooperation.detail.shopdetail;

import android.text.TextUtils;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.cooperation.CooperationShopReq;
import com.hll_sc_app.bean.cooperation.DeliveryPeriodBean;
import com.hll_sc_app.bean.cooperation.DeliveryPeriodResp;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 合作采购商详情-门店详情
 *
 * @author zhuyingsong
 * @date 2019/7/19
 */
public class CooperationShopDetailPresenter implements CooperationShopDetailContract.ICooperationShopDetailPresenter {
    private CooperationShopDetailContract.ICooperationShopDetailView mView;
    private List<DeliveryPeriodBean> mListPeriod;

    static CooperationShopDetailPresenter newInstance() {
        return new CooperationShopDetailPresenter();
    }

    @Override
    public void start() {
    }

    @Override
    public void register(CooperationShopDetailContract.ICooperationShopDetailView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void editCooperationShop(String actionType) {
        PurchaserShopBean bean = mView.getShopBean();
        if (bean == null) {
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("actionType", actionType)
            .put("groupID", UserConfig.getGroupID())
            .put("purchaserID", bean.getPurchaserID())
            .put("shopID", bean.getShopID())
            .create();
        CooperationPurchaserService
            .INSTANCE
            .editCooperationShop(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object object) {
                    if (TextUtils.equals("agree", actionType)) {
                        mView.showToast("同意合作成功");
                    } else if (TextUtils.equals("refuse", actionType)) {
                        mView.showToast("拒绝合作成功");
                    }
                    mView.editSuccess();
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public void delCooperationShop() {
        PurchaserShopBean bean = mView.getShopBean();
        if (bean == null) {
            return;
        }
        CooperationShopReq req = new CooperationShopReq();
        req.setActionType("delete");
        req.setGroupID(UserConfig.getGroupID());
        req.setOriginator("1");
        req.setPurchaserID(bean.getPurchaserID());
        req.setPurchaserName(bean.getPurchaserName());
        List<CooperationShopReq.ShopBean> list = new ArrayList<>();
        list.add(new CooperationShopReq.ShopBean(bean.getShopID(), bean.getShopName()));
        req.setShopList(list);
        BaseReq<CooperationShopReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        CooperationPurchaserService.INSTANCE.addCooperationShop(baseReq)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                    mView.showToast("解除合作成功");
                    mView.editSuccess();
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public void queryDeliveryPeriod() {
        if (!CommonUtils.isEmpty(mListPeriod)) {
            mView.showDeliveryPeriodWindow(mListPeriod);
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("flg", "2")
            .put("groupID", UserConfig.getGroupID())
            .create();
        CooperationPurchaserService
            .INSTANCE
            .queryDeliveryPeriodList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<DeliveryPeriodResp>() {
                @Override
                public void onSuccess(DeliveryPeriodResp resp) {
                    mListPeriod = resp.getRecords();
                    mView.showDeliveryPeriodWindow(mListPeriod);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public void editShopDeliveryPeriod(ShopSettlementReq req) {
        if (req == null) {
            return;
        }
        BaseReq<ShopSettlementReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        CooperationPurchaserService
            .INSTANCE
            .editShopSettlement(baseReq)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object object) {
                    mView.showToast("修改到货时间成功");
                    mView.editSuccess();
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
