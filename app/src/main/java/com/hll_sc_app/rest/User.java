package com.hll_sc_app.rest;

import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.user.RemindReq;
import com.hll_sc_app.bean.user.RemindResp;
import com.hll_sc_app.bean.user.SpecialTaxBean;
import com.hll_sc_app.bean.user.SpecialTaxSaveReq;
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
}
