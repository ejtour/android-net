package com.hll_sc_app.app.cooperation.detail.shopsettlement;

import android.text.TextUtils;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.cooperation.SettlementBean;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;
import com.hll_sc_app.bean.user.GroupParame;
import com.hll_sc_app.bean.user.GroupParameReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 合作采购商详情-选择结算方式
 *
 * @author zhuyingsong
 * @date 2019/7/18
 */
public class CooperationShopSettlementPresenter implements CooperationShopSettlementContract.ICooperationShopSettlementPresenter {
    private CooperationShopSettlementContract.ICooperationShopSettlementView mView;

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
            .put("defaultSettleDate", req.getSettleDate());
        CooperationPurchaserService.INSTANCE
            .editCooperationPurchaser(builder.create())
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
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
            .put("groupName", userBean.getGroupName())
            .put("purchaserName", req.getPurchaserName())
            .put("verification", mView.getVerification());
        CooperationPurchaserService.INSTANCE
            .addCooperationPurchaser(builder.create())
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                    if (TextUtils.equals(req.getActionType(), "normal")) {
                        mView.showToast("添加合作成功");
                    } else if (TextUtils.equals(req.getActionType(), "revalidation")) {
                        mView.showToast("重新验证成功");
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
    public void queryGroupParameterInSetting(String purchaserId) {
        GroupParameReq req = new GroupParameReq();
        req.setGroupID(purchaserId);
        req.setParameTypes("5");
        req.setFlag("1");
        BaseReq<GroupParameReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        UserService.INSTANCE
            .queryGroupParameterInSetting(baseReq)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .subscribe(new BaseCallback<List<GroupParame>>() {
                @Override
                public void onSuccess(List<GroupParame> list) {
                    if (!CommonUtils.isEmpty(list)) {
                        GroupParame parameter = list.get(0);
                        if (parameter.getParameValue() == 2) {
                            mView.showEditText();
                        }
                    }
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showToast(e.getMessage());
                }
            });

    }
}
