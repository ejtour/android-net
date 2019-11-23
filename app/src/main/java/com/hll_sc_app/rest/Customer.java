package com.hll_sc_app.rest;

import android.text.TextUtils;

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
import com.hll_sc_app.bean.customer.VisitRecordBean;
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

    /**
     * 查询特定客户的拜访记录
     *
     * @param pageNum      页码
     * @param customerType 客户类型 1.意向客户 2.合作客户
     * @param id           客户id
     */
    public static void queryVisitRecordDetail(int pageNum, int customerType, String id, SimpleObserver<SingleListResp<VisitRecordBean>> observer) {
        queryVisitRecord(true, pageNum, "", customerType, id, observer);
    }

    /**
     * 查询拜访记录
     *
     * @param all         是否全部拜访记录
     * @param pageNum     页码
     * @param searchWords 搜索词
     */
    public static void queryVisitRecord(boolean all, int pageNum, String searchWords, SimpleObserver<SingleListResp<VisitRecordBean>> observer) {
        queryVisitRecord(all, pageNum, searchWords, 0, "", observer);
    }

    private static void queryVisitRecord(boolean all, int pageNum, String searchWords, int customerType, String id, SimpleObserver<SingleListResp<VisitRecordBean>> observer) {
        UserBean user = GreenDaoUtils.getUser();
        CustomerService.INSTANCE
                .queryVisitRecord(BaseMapReq.newBuilder()
                        .put("customerID", id)
                        .put("customerName", searchWords)
                        .put("customerType", String.valueOf(TextUtils.isEmpty(id) ? "" : customerType))
                        .put("employeeID", all ? "" : user.getEmployeeID())
                        .put("groupID", user.getGroupID())
                        .put("pageNum", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 删除拜访记录
     *
     * @param id 拜访记录id
     */
    public static void delVisitRecord(String id, SimpleObserver<Object> observer) {
        CustomerService.INSTANCE
                .delVisitRecord(BaseMapReq.newBuilder()
                        .put("id", id)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 添加/修改拜访记录
     */
    public static void saveVisitRecord(VisitRecordBean req,SimpleObserver<Object> observer){
        CustomerService.INSTANCE
                .saveVisitRecord(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
