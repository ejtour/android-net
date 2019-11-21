package com.hll_sc_app.rest;

import com.hll_sc_app.api.CustomerService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
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

    /**
     * 查询意向客户
     *
     * @param all         是否为全部
     * @param pageNum     页码
     * @param searchWords 客户名称
     */
    public static void queryIntentCustomer(boolean all, int pageNum, String searchWords, SimpleObserver<SingleListResp<CustomerBean>> observer) {
        UserBean user = GreenDaoUtils.getUser();
        CustomerService.INSTANCE
                .queryIntentCustomer(BaseMapReq.newBuilder()
                        .put("groupID", user.getGroupID())
                        .put("pageNum", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .put("customerName", searchWords)
                        .put("employeeID", all ? "" : user.getEmployeeID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
