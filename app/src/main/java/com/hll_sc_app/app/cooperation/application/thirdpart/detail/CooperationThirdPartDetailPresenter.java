package com.hll_sc_app.app.cooperation.application.thirdpart.detail;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.cooperation.DeliveryPeriodBean;
import com.hll_sc_app.bean.cooperation.ThirdPartyPurchaserBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;


/**
 * 合作采购商-我收到的申请-第三方申请-详情
 *
 * @author zhuyingsong
 * @date 2019/7/24
 */
public class CooperationThirdPartDetailPresenter implements CooperationThirdPartDetailContract.ICooperationThirdPartDetailPresenter {
    private CooperationThirdPartDetailContract.ICooperationThirdDetailView mView;
    private List<DeliveryPeriodBean> mListPeriod;

    static CooperationThirdPartDetailPresenter newInstance() {
        return new CooperationThirdPartDetailPresenter();
    }

    @Override
    public void start() {
        queryThirdPartDetail();
    }

    @Override
    public void register(CooperationThirdPartDetailContract.ICooperationThirdDetailView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryThirdPartDetail() {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("plateSupplierID", UserConfig.getGroupID())
            .put("id", mView.getId())
            .create();
        CooperationPurchaserService.INSTANCE.queryThirdPartPurchaserDetail(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<ThirdPartyPurchaserBean>() {
                @Override
                public void onSuccess(ThirdPartyPurchaserBean resp) {
                    mView.showView(resp);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }


    @Override
    public void editCooperationShop(String actionType) {

    }

    @Override
    public void delCooperationShop() {
//        PurchaserShopBean bean = mView.getShopBean();
//        if (bean == null) {
//            return;
//        }
//        CooperationShopReq req = new CooperationShopReq();
//        req.setActionType("delete");
//        req.setGroupID(UserConfig.getGroupID());
//        req.setOriginator("1");
//        req.setPurchaserID(bean.getPurchaserID());
//        req.setPurchaserName(bean.getPurchaserName());
//        List<CooperationShopReq.ShopBean> list = new ArrayList<>();
//        list.add(new CooperationShopReq.ShopBean(bean.getShopID(), bean.getShopName()));
//        req.setShopList(list);
//        BaseReq<CooperationShopReq> baseReq = new BaseReq<>();
//        baseReq.setData(req);
//        CooperationPurchaserService.INSTANCE.addCooperationShop(baseReq)
//            .compose(ApiScheduler.getObservableScheduler())
//            .map(new Precondition<>())
//            .doOnSubscribe(disposable -> mView.showLoading())
//            .doFinally(() -> mView.hideLoading())
//            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
//            .subscribe(new BaseCallback<Object>() {
//                @Override
//                public void onSuccess(Object resp) {
//                    mView.showToast("解除合作成功");
//                    mView.editSuccess();
//                }
//
//                @Override
//                public void onFailure(UseCaseException e) {
//                    mView.showError(e);
//                }
//            });
    }
}
