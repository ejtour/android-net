package com.hll_sc_app.app.user.login;

import android.text.TextUtils;

import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.GlobalPreference;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.LoginResp;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 登录页面
 *
 * @author zhuyingsong
 * @date 2019/6/4
 */
public class LoginPresenter implements LoginContract.ILoginPresenter {
    static final String LOGIN_PHONE = "loginPhone";
    private static final int PWD_MIN = 6;
    private static final int PWD_MAX = 20;
    private LoginContract.ILoginView mView;

    static LoginPresenter newInstance() {
        return new LoginPresenter();
    }

    @Override
    public void register(LoginContract.ILoginView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void toLogin(String loginPhone, String loginPWD, String checkCode) {
        if (!checkPhoneNumber(loginPhone) || !checkPassword(loginPWD)) {
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("loginPhone", loginPhone)
            .put("loginPWD", loginPWD)
            .put("checkCode", checkCode)
            .put("deviceId", PushServiceFactory.getCloudPushService().getDeviceId())
            .create();
        UserService.INSTANCE.login(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<LoginResp>() {
                @Override
                public void onSuccess(LoginResp resp) {
                    GlobalPreference.putParam(UserConfig.ACCESS_TOKEN, resp.getAccessToken());
//                    GlobalPreference.putParam(LOGIN_PHONE, loginPhone);
                    UserBean userBean = resp.getUser();
                    userBean.setAccessToken(resp.getAccessToken());
                    String authType = userBean.getAuthType();
                    if (!TextUtils.isEmpty(authType) && !authType.contains(",")) {
                        userBean.setCurRole(authType);
                    }
                    GreenDaoUtils.updateUser(userBean);
                    GreenDaoUtils.updateShopList(resp.getShops());
                    mView.loginSuccess(authType);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    /**
     * 手机号码格式
     *
     * @return false-不符合
     */
    private boolean checkPhoneNumber(String loginPhone) {
        if (!CommonUtils.isPhone(loginPhone)) {
            mView.showToast("输入手机号格式不正确");
            return false;
        }
        return true;
    }

    /**
     * 密码格式验证
     *
     * @return false-不符合
     */
    private boolean checkPassword(String loginPWD) {
        if (!TextUtils.isEmpty(loginPWD)) {
            if (loginPWD.length() < PWD_MIN || loginPWD.length() > PWD_MAX) {
                mView.showToast("请输入6-20位密码");
                return false;
            }
        }
        return true;
    }
}
