package com.hll_sc_app.rest;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.hll_sc_app.R;
import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.GlobalPreference;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.AuthBean;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.LoginResp;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.mall.PrivateMallResp;
import com.hll_sc_app.bean.user.CertifyReq;
import com.hll_sc_app.bean.user.FollowQRResp;
import com.hll_sc_app.bean.user.FollowStatusResp;
import com.hll_sc_app.bean.user.GroupParamBean;
import com.hll_sc_app.bean.user.InviteCodeResp;
import com.hll_sc_app.bean.user.PurchaseTemplateBean;
import com.hll_sc_app.bean.user.RemindReq;
import com.hll_sc_app.bean.user.RemindResp;
import com.hll_sc_app.bean.user.SpecialTaxBean;
import com.hll_sc_app.bean.user.SpecialTaxSaveReq;
import com.hll_sc_app.citymall.App;
import com.hll_sc_app.citymall.util.LogUtil;
import com.hll_sc_app.impl.IChangeListener;
import com.hll_sc_app.utils.Constants;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

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
                        .put("taxRateStatus", "1")
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

    public static void queryOnlyReceive(@NonNull ILoadView context, @Nullable IChangeListener listener) {
        User.queryGroupParam("7", new SimpleObserver<List<GroupParamBean>>(context) {
            @Override
            public void onSuccess(List<GroupParamBean> groupParamBeans) {
                boolean onlyReceive = groupParamBeans.get(0).getParameValue() == 2;
                GlobalPreference.putParam(Constants.ONLY_RECEIVE, onlyReceive);
                if (listener != null) {
                    listener.onChanged();
                    listener.onChanged(onlyReceive);
                }
            }
        });
    }

    /**
     * 绑定邮箱
     *
     * @param email 邮箱
     */
    public static void bindEmail(String email, SimpleObserver<Object> observer) {
        UserService.INSTANCE.bindEmail(BaseMapReq.newBuilder()
                .put("email", email)
                .put("employeeID", GreenDaoUtils.getUser().getEmployeeID())
                .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询集团oem二维码
     */
    public static void queryGroupQRCode(SimpleObserver<PrivateMallResp> observer) {
        UserService.INSTANCE
                .queryGroupQRCode(BaseMapReq.newBuilder()
                        .put("groupID", GreenDaoUtils.getUser().getGroupID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }


    /**
     * 查询集团参数
     *
     * @param types 以逗号分隔的参数类型
     */
    public static void queryGroupParam(String types, SimpleObserver<List<GroupParamBean>> observer) {
        UserService.INSTANCE
                .queryGroupParam(BaseMapReq.newBuilder()
                        .put("flag", "1")
                        .put("groupID", UserConfig.getGroupID())
                        .put("parameTypes", types)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 更改集团参数
     *
     * @param type  集团类型
     * @param value 类型值 如果 type 为 13，则设置天数，否则 1 为关闭，2 为开启
     */
    public static void changeGroupParam(int type, int value, SimpleObserver<Object> observer) {
        UserService.INSTANCE
                .changeGroupParam(BaseMapReq.newBuilder()
                        .put("groupID", UserConfig.getGroupID())
                        .put("parameType", String.valueOf(type))
                        .put("parameValue", String.valueOf(value))
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 微信登录
     *
     * @param code 用户换取 access_token 的 code
     */
    public static void wxAuth(String code, SimpleObserver<LoginResp> observer) {
        UserService.INSTANCE
                .wxAuth(BaseMapReq.newBuilder()
                        .put("code", code)
                        .put("authorizerAppId", App.INSTANCE.getString(R.string.wx_appid))
                        .put("loginType", "1")
                        .put("userType", "1")
                        .put("deviceId", PushServiceFactory.getCloudPushService().getDeviceId())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 解绑三方账号
     * @param type 三方账号类型 1微信 2apple
     */
    public static void unbindAccount(int type, SimpleObserver<Object> observer) {
        UserService.INSTANCE
                .unbindAccount(BaseMapReq.newBuilder()
                        .put("thirdType", String.valueOf(type))
                        .put("userID", GreenDaoUtils.getUser().getEmployeeID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询公众号绑定状态
     */
    public static void queryFollowStatus(SimpleObserver<FollowStatusResp> observer) {
        UserBean user = GreenDaoUtils.getUser();
        if (user == null) return;
        UserService.INSTANCE
                .queryFollowStatus(BaseMapReq.newBuilder()
                        .put("groupID", user.getGroupID())
                        .put("userID", user.getEmployeeID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询绑定公众号二维码
     */
    public static void queryFollowQR(SimpleObserver<FollowQRResp> observer) {
        queryFollowQR(null, "", observer);
    }

    /**
     * 查询关注公众号二维码
     *
     * @param groupID     集团id或货主id
     * @param isWarehouse 代仓类型 0-自营 1-采代 2-供代
     */
    public static void queryFollowQR(String groupID, String isWarehouse, SimpleObserver<FollowQRResp> observer) {
        UserBean user = GreenDaoUtils.getUser();
        if (user == null) return;
        UserService.INSTANCE
                .queryFollowQR(BaseMapReq.newBuilder()
                        .put("groupID", TextUtils.isEmpty(groupID) ? user.getGroupID() : groupID)
                        .put("agencyID", !TextUtils.isEmpty(groupID) ? user.getGroupID() : "")
                        .put("isWareHourse", isWarehouse)
                        .put("userID", user.getEmployeeID())
                        .put("userType", "1".equals(isWarehouse) ? "0" : "1")
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
