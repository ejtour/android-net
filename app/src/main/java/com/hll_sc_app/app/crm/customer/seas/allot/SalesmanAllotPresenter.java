package com.hll_sc_app.app.crm.customer.seas.allot;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;
import com.hll_sc_app.bean.staff.EmployeeBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Customer;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/29
 */

public class SalesmanAllotPresenter implements ISalesmanAllotContract.ISalesmanAllotPresenter {
    private ISalesmanAllotContract.ISalesmanAllotView mView;
    private int mPageNum;

    private SalesmanAllotPresenter() {
    }

    public static SalesmanAllotPresenter newInstance() {
        return new SalesmanAllotPresenter();
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        load(false);
    }

    @Override
    public void loadMore() {
        load(false);
    }

    @Override
    public void allot(String id, String name, String phone) {
        SimpleObserver<Object> observer = new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.showToast("分配成功");
                RouterUtil.goToActivity(RouterConfig.CRM_CUSTOMER_SEAS);
            }
        };
        if (mView.isIntent()) {
            Customer.distributeCustomer(mView.getID(), id, name, observer);
        } else {
            ShopSettlementReq req = new ShopSettlementReq();
            req.setShopIDs(mView.getID());
            req.setActionType("salesRepresentative");
            req.setEmployeeID(id);
            req.setEmployeeName(name);
            req.setEmployeePhone(phone);
            req.setGroupID(UserConfig.getGroupID());
            req.setPurchaserID(mView.getPurchaserID());
            CooperationPurchaserService.INSTANCE
                    .editShopEmployee(new BaseReq<>(req))
                    .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                    .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                    .subscribe(observer);
        }
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    private void load(boolean showLoading) {
        SimpleObserver<List<EmployeeBean>> observer = new SimpleObserver<List<EmployeeBean>>(mView, showLoading) {
            @Override
            public void onSuccess(List<EmployeeBean> list) {
                mView.setData(list, mPageNum > 1);
                if (CommonUtils.isEmpty(list)) return;
                mPageNum++;
            }
        };
        CooperationPurchaserService.INSTANCE.queryEmployeeList(BaseMapReq.newBuilder()
                .put("keyword", mView.getSearchWords())
                .put("pageNum", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .put("roleType", "1")
                .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    @Override
    public void register(ISalesmanAllotContract.ISalesmanAllotView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
