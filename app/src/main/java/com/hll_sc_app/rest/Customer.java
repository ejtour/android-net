package com.hll_sc_app.rest;

import com.hll_sc_app.api.CustomerService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.customer.CrmCustomerResp;
import com.hll_sc_app.bean.customer.CrmShopResp;
import com.hll_sc_app.bean.customer.CustomerBean;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/15
 */

public class Customer {
    /**
     * 销售查询店铺数量
     */
    public static void queryShopInfo(SimpleObserver<CrmShopResp> observer) {
        UserBean user = GreenDaoUtils.getUser();
        CustomerService.INSTANCE
                .queryShopInfo(BaseMapReq.newBuilder()
                        .put("groupID", user.getGroupID())
                        .put("salesmanID", user.getEmployeeID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 销售查询客户信息
     */
    public static void queryCustomerInfo(SimpleObserver<CrmCustomerResp> observer) {
        UserBean user = GreenDaoUtils.getUser();
        CustomerService.INSTANCE
                .queryCustomerInfo(BaseMapReq.newBuilder()
                        .put("groupID", user.getGroupID())
                        .put("employeeID", user.getEmployeeID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 添加意向客户
     */
    public static void saveIntentCustomer(CustomerBean bean, SimpleObserver<Object> observer) {
        CustomerService.INSTANCE
                .saveIntentCustomer(new BaseReq<>(bean))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
