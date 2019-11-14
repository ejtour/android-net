package com.hll_sc_app.app.cooperation.detail.details.basic;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.api.DeliveryManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.cooperation.ChangeGroupParamReq;
import com.hll_sc_app.bean.cooperation.QueryGroupListResp;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;
import com.hll_sc_app.bean.delivery.DeliveryPeriodBean;
import com.hll_sc_app.bean.delivery.DeliveryPeriodResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.Arrays;
import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;


/**
 * 合作采购商详情-详细资料-基本信息
 *
 * @author zhuyingsong
 * @date 2019/7/19
 */
public class CooperationDetailsBasicPresenter implements CooperationDetailsBasicContract.IGoodsRelevanceListPresenter {
    private CooperationDetailsBasicContract.IGoodsRelevanceListView mView;
    private List<DeliveryPeriodBean> mListPeriod;

    static CooperationDetailsBasicPresenter newInstance() {
        return new CooperationDetailsBasicPresenter();
    }

    @Override
    public void register(CooperationDetailsBasicContract.IGoodsRelevanceListView view) {
        this.mView = CommonUtils.checkNotNull(view);
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
                    mView.saveSuccess();
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public void editCooperationPurchaserLevel(BaseMapReq req) {
        CooperationPurchaserService
            .INSTANCE
            .editCooperationPurchaserLevel(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object object) {
                    mView.showToast("修改成功");
                    mView.saveSuccess();
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }


    @Override
    public void changeGroupParams(String type, String value, String purchaserID) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        ChangeGroupParamReq req = new ChangeGroupParamReq();
        ChangeGroupParamReq.BizList bizList = new ChangeGroupParamReq.BizList();
        bizList.setBizType(type);
        bizList.setBizValue(value);
        req.setBizList(Arrays.asList(bizList));
        req.setGroupID(userBean.getGroupID());
        req.setPurchaserID(purchaserID);

        BaseReq<ChangeGroupParamReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        CooperationPurchaserService
                .INSTANCE
                .changeGroupParams(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<QueryGroupListResp>() {
                    @Override
                    public void onSuccess(QueryGroupListResp queryGroupListResp) {
                        mView.showToast("修改成功");
                        mView.saveSuccess();
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });

    }
}
