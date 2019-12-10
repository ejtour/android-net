package com.hll_sc_app.rest;

import android.arch.lifecycle.LifecycleOwner;

import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.AuthBean;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.user.CertifyReq;
import com.hll_sc_app.bean.user.InviteCodeResp;
import com.hll_sc_app.bean.user.PurchaseTemplateBean;
import com.hll_sc_app.bean.user.RemindReq;
import com.hll_sc_app.bean.user.RemindResp;
import com.hll_sc_app.bean.user.SpecialTaxBean;
import com.hll_sc_app.bean.user.SpecialTaxSaveReq;
import com.hll_sc_app.citymall.util.LogUtil;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/28
 */

public class User {

    public static void logout(SimpleObserver<Object> observer) {
        UserService.INSTANCE
                .logout(BaseMapReq.newBuilder()
                        .put("accessToken", UserConfig.accessToken())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 保存商品特殊税率
     * @param req
     * @param observer
     */
    public static void saveSpecialTaxRate(SpecialTaxSaveReq req, SimpleObserver<Object> observer) {
        UserService.INSTANCE
                .saveSpecialTaxRate(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询商品特殊税率
     */
    public static void querySpecialTax(SimpleObserver<SingleListResp<SpecialTaxBean>> observer) {
        UserService.INSTANCE
                .querySpecialTax(BaseMapReq.newBuilder()
                        .put("groupID", UserConfig.getGroupID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询提醒设置
     */
    public static void queryRemind(SimpleObserver<RemindResp> observer) {
        UserService.INSTANCE
                .queryRemind(BaseMapReq.newBuilder()
                        .put("groupID", UserConfig.getGroupID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 更新提醒设置
     *
     * @param isOpen      开启状态
     * @param remindTimes 提醒倍数
     */
    public static void updateRemind(boolean isOpen, String remindTimes, SimpleObserver<Object> observer) {
        UserService.INSTANCE
                .updateRemind(new BaseReq<>(new RemindReq(isOpen, remindTimes)))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询采购模板
     *
     * @param pageNum         页码
     * @param purchaserID     采购商id
     * @param purchaserShopID 采购商门店id
     */
    public static void queryPurchaseTemplate(int pageNum, String purchaserID, String purchaserShopID, SimpleObserver<SingleListResp<PurchaseTemplateBean>> observer) {
        UserService.INSTANCE
                .queryPurchaseTemplate(BaseMapReq.newBuilder()
                        .put("actionType", "supplier")
                        .put("flag", "2")
                        .put("pageNum", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .put("supplyID", UserConfig.getGroupID())
                        .put("purchaserID", purchaserID)
                        .put("purchaserShopID", purchaserShopID)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 更新集团信息
     *
     * @param key   集团信息键
     * @param value 集团信息值
     */
    public static void updateGroupInfo(String key, String value, SimpleObserver<Object> observer) {
        UserService.INSTANCE
                .updateGroupInfo(BaseMapReq.newBuilder()
                        .put("groupID", UserConfig.getGroupID())
                        .put(key, value, true)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 请求认证
     *
     * @param req 认证请求
     */
    public static void reqCertify(CertifyReq req, SimpleObserver<Object> observer) {
        UserService.INSTANCE
                .reqCertify(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询邀请码
     */
    public static void queryInviteCode(SimpleObserver<InviteCodeResp> observer) {
        UserBean user = GreenDaoUtils.getUser();
        UserService.INSTANCE
                .queryInviteCode(BaseMapReq.newBuilder()
                        .put("employeeID", user.getEmployeeID())
                        .put("groupID", user.getGroupID())
                        .put("type", "1".equals(user.getCurRole()) ? "2" : "1")
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询权限列表
     */
    public static void queryAuthList(LifecycleOwner owner) {
        UserService.INSTANCE.queryAuth(new BaseReq<>(new Object()))
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
                .subscribe(new BaseCallback<SingleListResp<AuthBean>>() {
                    @Override
                    public void onSuccess(SingleListResp<AuthBean> authBeanSingleListResp) {
                        LogUtil.d("right", "权限获取成功");
                        GreenDaoUtils.deleteAuthList();
                        GreenDaoUtils.updateAuthList(authBeanSingleListResp.getRecords());
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        LogUtil.e("right", "权限获取失败" + e.getMessage());
                    }
                });
    }
}
