package com.hll_sc_app.app.user.register;

import android.text.TextUtils;

import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 注册页面
 *
 * @author zhuyingsong
 * @date 2019/6/5
 */
public class RegisterPresenter implements RegisterContract.IFindPresenter {
    private static final int PWD_MIN = 6;
    private static final int PWD_MAX = 20;
    private RegisterContract.IFindView mView;

    static RegisterPresenter newInstance() {
        return new RegisterPresenter();
    }

    @Override
    public void start() {
        // no-op
    }

    @Override
    public void register(RegisterContract.IFindView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void toFind(String loginPhone, String checkCode, String loginPWD, String checkLoginPWD) {
        if (!checkPhoneNumber(loginPhone)
            || !checkPassword(loginPWD)
            || !checkAgainPassword(checkLoginPWD)
            || !samePWD(loginPWD, checkLoginPWD)) {
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("loginPhone", loginPhone)
            .put("loginPWD", loginPWD)
            .put("checkCode", checkCode)
            .put("checkLoginPWD", checkLoginPWD)
            .create();
        UserService.INSTANCE.find(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                    mView.findSuccess();
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

    /**
     * 密码格式验证
     *
     * @return false-不符合
     */
    private boolean checkAgainPassword(String checkLoginPWD) {
        if (!TextUtils.isEmpty(checkLoginPWD)) {
            if (checkLoginPWD.length() < PWD_MIN || checkLoginPWD.length() > PWD_MAX) {
                mView.showToast("请输入6-20位确认密码");
                return false;
            }
        }
        return true;
    }

    /**
     * 密码格式验证
     *
     * @return false-不符合
     */
    private boolean samePWD(String loginPWD, String checkLoginPWD) {
        if (!TextUtils.equals(loginPWD, checkLoginPWD)) {
            mView.showToast("两次输入的密码不一致");
            return false;
        }
        return true;
    }
}
