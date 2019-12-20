package com.hll_sc_app.app.staffmanage.linkshop;

import com.hll_sc_app.api.CommonService;
import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.api.StaffManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.cooperation.CooperationShopListResp;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;
import com.hll_sc_app.bean.staff.DropStaffEmployeeReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 员工列表——关联门店
 */
public class StaffLinkShopPresenter implements StaffLinkShopListContract.IPresent {
    private final int pageSize = 20;
    private StaffLinkShopListContract.IView mView;
    private int mPageNum = 1;
    private int mTempPageNum = 1;

    static StaffLinkShopPresenter newInstance() {
        return new StaffLinkShopPresenter();
    }

    @Override
    public void start() {

    }

    @Override
    public void register(StaffLinkShopListContract.IView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryCooperationShop(boolean isLoading) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("actionType", "employeeUpdate")
                .put("groupID", userBean.getGroupID())
                .put("pageNo", String.valueOf(mTempPageNum))
                .put("pageSize", String.valueOf(pageSize))
                .put("salesmanID", mView.getSalesmanID())
                .create();
        CommonService.INSTANCE
                .queryCooperationShop(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if(isLoading){
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<CooperationShopListResp>() {
                    @Override
                    public void onSuccess(CooperationShopListResp resp) {
                        mView.showShops(resp, mTempPageNum > 1);
                        mPageNum = mTempPageNum;
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                        mTempPageNum = mPageNum;
                    }
                });
    }


    @Override
    public void refresh() {
        mTempPageNum = 1;
        queryCooperationShop(false);
    }

    @Override
    public void getMore() {
        mTempPageNum++;
        queryCooperationShop(false);
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public void dropStaffEmployee() {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseReq<DropStaffEmployeeReq> baseReq = new BaseReq<>();
        DropStaffEmployeeReq req = new DropStaffEmployeeReq();
        req.setGroupID(userBean.getGroupID());
        req.setSalesmanID(mView.getSalesmanID());
        req.setShopIDs(mView.getShopIds());
        baseReq.setData(req);
        StaffManageService.INSTANCE
                .dropStaffEmployee(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                        mView.showLoading();
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        mView.dropSuccess();
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void editShopEmployee(ShopSettlementReq shopSettlementReq) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        shopSettlementReq.setGroupID(userBean.getGroupID());
        shopSettlementReq.setActionType("salesRepresentative");
        BaseReq<ShopSettlementReq> baseReq = new BaseReq<>();
        baseReq.setData(shopSettlementReq);
        CooperationPurchaserService
                .INSTANCE
                .editShopEmployee(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<Object>() {
                    @Override
                    public void onSuccess(Object object) {
                        mView.editSuccess();
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }
}
