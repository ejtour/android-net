package com.hll_sc_app.app.paymanage;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.cooperation.SettlementBean;
import com.hll_sc_app.bean.paymanage.PayResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 支付管理
 *
 * @author zhuyingsong
 * @date 2019/8/8
 */
public class PayManagePresenter implements PayManageContract.IPayManagePresenter {
    private PayManageContract.IPayManageView mView;

    static PayManagePresenter newInstance() {
        return new PayManagePresenter();
    }

    @Override
    public void start() {
        querySettlementMethodList();
    }

    @Override
    public void register(PayManageContract.IPayManageView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void querySettlementMethodList() {
        CooperationPurchaserService.INSTANCE
            .querySettlementMethodList(BaseMapReq.newBuilder().create())
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .flatMap((Function<PayResp, ObservableSource<SettlementBean>>) o -> {
                mView.setDefaultPayMethod(o.getRecords());
                return getSettlementListObservable();
            })
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new SettlementBeanBaseCallback());
    }

    @Override
    public void querySettlementList() {
        getSettlementListObservable().doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new SettlementBeanBaseCallback());
    }


    private Observable<SettlementBean> getSettlementListObservable() {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("supplyID", UserConfig.getGroupID())
            .create();
        return CooperationPurchaserService.INSTANCE
            .querySettlementList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>());
    }

    private class SettlementBeanBaseCallback extends BaseCallback<SettlementBean> {
        @Override
        public void onSuccess(SettlementBean settlementBean) {
            mView.showPayList(settlementBean);
        }

        @Override
        public void onFailure(UseCaseException e) {
            mView.showError(e);
            // 失败处理
            mView.showPayList();
        }
    }

    /**
     * 修改支付方式
     *
     * @param payType 支付方式	0-在线支付,1-货到付款,2-账期支付
     * @param status  开启状态 0-停用,1-启用
     */
    public static void editSettlement(String payType, String status, SimpleObserver<MsgWrapper<Object>> observer) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("actionType", "1")
                .put("payType", payType)
                .put("status", status)
                .put("supplierID", UserConfig.getGroupID())
                .create();
        CooperationPurchaserService.INSTANCE
                .editSettlement(req)
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .subscribe(observer);
    }
}
