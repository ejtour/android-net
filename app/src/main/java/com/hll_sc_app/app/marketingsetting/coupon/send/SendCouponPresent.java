package com.hll_sc_app.app.marketingsetting.coupon.send;

import com.hll_sc_app.api.MarketingSettingService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.bean.marketingsetting.CouponSendReq;
import com.hll_sc_app.bean.marketingsetting.CouponSendResultBean;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class SendCouponPresent implements ISendCouponContract.IPresent {

    private ISendCouponContract.IView mView;

    public static SendCouponPresent newInstance() {
        return new SendCouponPresent();
    }

    @Override
    public void register(ISendCouponContract.IView view) {
        mView = view;
    }

    @Override
    public void sendCoupon() {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }

        CouponSendReq couponSendReq = new CouponSendReq();
        couponSendReq.setGroupID(userBean.getGroupID());
        couponSendReq.setCustomerList(mView.getCustomers());
        couponSendReq.setNote(mView.getNode());
        couponSendReq.setSendType(2);
        BaseReq<CouponSendReq> baseReq = new BaseReq<>();
        baseReq.setData(couponSendReq);
        MarketingSettingService.INSTANCE
                .sendCoupon(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<BaseResp<List<CouponSendResultBean>>>() {
                    @Override
                    public void onSuccess(BaseResp<List<CouponSendResultBean>> listBaseResp) {
                        mView.showToast(listBaseResp.getMessage());
                        if (listBaseResp.isSuccess()) {
                            mView.sendSuccess(listBaseResp.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }
}
