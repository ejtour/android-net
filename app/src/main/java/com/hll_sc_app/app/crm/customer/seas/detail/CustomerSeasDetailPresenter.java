package com.hll_sc_app.app.crm.customer.seas.detail;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/29
 */

public class CustomerSeasDetailPresenter implements ICustomerSeasDetailContract.ICustomerSeasDetailPresenter {
    private ICustomerSeasDetailContract.ICustomerSeasDetailView mView;

    private CustomerSeasDetailPresenter() {
    }

    public static CustomerSeasDetailPresenter newInstance() {
        return new CustomerSeasDetailPresenter();
    }

    @Override
    public void receive(String id, String purchaserID) {
        SimpleObserver<Object> observer = new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.showToast("领取客户成功");
                mView.success();
            }
        };
        UserBean user = GreenDaoUtils.getUser();
        ShopSettlementReq req = new ShopSettlementReq();
        req.setShopIDs(id);
        req.setActionType("salesRepresentative");
        req.setEmployeeID(user.getEmployeeID());
        req.setEmployeeName(user.getEmployeeName());
        req.setEmployeePhone(user.getLoginPhone());
        req.setGroupID(UserConfig.getGroupID());
        req.setPurchaserID(purchaserID);
        CooperationPurchaserService.INSTANCE
                .editShopEmployee(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    @Override
    public void register(ICustomerSeasDetailContract.ICustomerSeasDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
