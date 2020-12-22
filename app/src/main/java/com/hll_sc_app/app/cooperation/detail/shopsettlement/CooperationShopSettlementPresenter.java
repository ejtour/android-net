package com.hll_sc_app.app.cooperation.detail.shopsettlement;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.api.DeliveryManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.cooperation.SettlementBean;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;
import com.hll_sc_app.bean.delivery.DeliveryBean;
import com.hll_sc_app.bean.delivery.DeliveryPeriodBean;
import com.hll_sc_app.bean.delivery.DeliveryPeriodResp;
import com.hll_sc_app.bean.user.GroupParamBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.User;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 合作采购商详情-选择结算方式
 *
 * @author zhuyingsong
 * @date 2019/7/18
 */
public class CooperationShopSettlementPresenter implements CooperationShopSettlementContract.ICooperationShopSettlementPresenter {
    private CooperationShopSettlementContract.ICooperationShopSettlementView mView;
    private List<DeliveryPeriodBean> mRecords;

    static CooperationShopSettlementPresenter newInstance() {
        return new CooperationShopSettlementPresenter();
    }

    @Override
    public void start() {
        querySettlementList();
    }

    @Override
    public void register(CooperationShopSettlementContract.ICooperationShopSettlementView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void querySettlementList() {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("supplyID", UserConfig.getGroupID())
            .create();
        CooperationPurchaserService.INSTANCE
            .querySettlementList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<SettlementBean>() {
                @Override
                public void onSuccess(SettlementBean resp) {
                    mView.showSettlementList(resp);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public void queryDeliveryList() {
        BaseMapReq req = BaseMapReq
                .newBuilder()
                .put("groupID", UserConfig.getGroupID())
                .create();
        DeliveryManageService.INSTANCE
                .queryDeliveryList(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<DeliveryBean>() {
                    @Override
                    public void onSuccess(DeliveryBean resp) {
                        mView.showDeliveryDialog(resp.getDeliveryWay());
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void queryDeliveryPeriod() {
        if (mRecords != null) {
            mView.showDeliveryPeriodWindow(mRecords);
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("flg", "2")
                .put("groupID", UserConfig.getGroupID())
                .create();
        DeliveryManageService
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
                        mRecords = resp.getRecords();
                        mView.showDeliveryPeriodWindow(mRecords);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void editShopSettlement(ShopSettlementReq req) {
        if (req == null) {
            return;
        }
        BaseReq<ShopSettlementReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        CooperationPurchaserService.INSTANCE
                .editShopSettlement(baseReq)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object object) {
                    mView.showToast("结算方式修改成功");
                    mView.editSuccess();
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public void editCooperationPurchaser(ShopSettlementReq req) {
        BaseMapReq.Builder builder = BaseMapReq.newBuilder()
                .put("actionType", "agree")
                .put("groupID", UserConfig.getGroupID())
                .put("originator", "1")
                .put("purchaserID", req.getPurchaserID())
                .put("defaultSettlementWay", req.getSettlementWay())
                .put("defaultAccountPeriod", req.getAccountPeriod())
                .put("defaultAccountPeriodType", req.getAccountPeriodType())
                .put("defaultDeliveryWay", req.getDeliveryWay())
                .put("defaultDeliveryPeriod", req.getDeliveryPeriod())
                .put("customerLevel", req.getCustomerLevel())
                .put("defaultSettleDate", req.getSettleDate())
                .put("inspector", req.getInspector());
        CooperationPurchaserService.INSTANCE
                .editCooperationPurchaser(builder.create())
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .delay(1000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<Object>() {
                    @Override
                    public void onSuccess(Object resp) {
                        mView.showToast("同意合作成功");
                        mView.editSuccess();
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void addCooperationPurchaser(ShopSettlementReq req) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq.Builder builder = BaseMapReq.newBuilder()
                .put("actionType", req.getActionType())
                .put("groupID", UserConfig.getGroupID())
                .put("originator", "1")
                .put("purchaserID", req.getPurchaserID())
                .put("defaultSettlementWay", req.getSettlementWay())
                .put("defaultAccountPeriod", req.getAccountPeriod())
                .put("defaultAccountPeriodType", req.getAccountPeriodType())
                .put("defaultSettleDate", req.getSettleDate())
                .put("defaultDeliveryWay", req.getDeliveryWay())
                .put("defaultDeliveryPeriod", req.getDeliveryPeriod())
                .put("customerLevel", req.getCustomerLevel())
                .put("groupName", userBean.getGroupName())
                .put("purchaserName", req.getPurchaserName())
                .put("verification", mView.getVerification())
                .put("inspector", req.getInspector());
        SimpleObserver<MsgWrapper<Object>> observer = new SimpleObserver<MsgWrapper<Object>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                mView.editSuccess();
            }
        };
        CooperationPurchaserService.INSTANCE
                .addCooperationPurchaser(builder.create())
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);

    }

    @Override
    public void queryGroupParameterInSetting(String purchaserId) {
        User.queryGroupParam("5", new SimpleObserver<List<GroupParamBean>>(mView) {
            @Override
            public void onSuccess(List<GroupParamBean> groupParamBeans) {
                if (!CommonUtils.isEmpty(groupParamBeans)) {
                    GroupParamBean parameter = groupParamBeans.get(0);
                    if (parameter.getParameValue() == 2) {
                        mView.showEditText();
                    }
                }
            }
        });
    }
}
