package com.hll_sc_app.app.user.change;

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
 * 找回密码页面
 *
 * @author zhuyingsong
 * @date 2019/6/5
 */
public class ChangePasswordPresenter implements ChangePasswordContract.IChangePresenter {
    private static final int PWD_MIN = 6;
    private static final int PWD_MAX = 20;
    private ChangePasswordContract.IChangeView mView;

    static ChangePasswordPresenter newInstance() {
        return new ChangePasswordPresenter();
    }

    @Override
    public void start() {
        // no-op
    }

    @Override
    public void register(ChangePasswordContract.IChangeView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void toChange(String loginPassword, String newLoginPassword, String checkLoginPassword) {
        if (!checkPassword(newLoginPassword, false)
            || !checkPassword(checkLoginPassword, true)
            || !samePassword(newLoginPassword, checkLoginPassword)) {
            return;
        }
        if (TextUtils.equals(newLoginPassword, loginPassword)) {
            mView.showToast("新密码与原密码一致");
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("flag", "1")
            .put("loginPWD", loginPassword)
            .put("newLoginPWD", newLoginPassword)
            .put("checkLoginPWD", checkLoginPassword)
            .create();
        UserService.INSTANCE.changePassword(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                    mView.changeSuccess();
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    /**
     * 密码格式验证
     *
     * @return false-不符合
     */
    private boolean checkPassword(String loginPassword, boolean check) {
        boolean isCheck = true;
        if (!TextUtils.isEmpty(loginPassword)) {
            if (loginPassword.length() < PWD_MIN || loginPassword.length() > PWD_MAX) {
                if (check) {
                    mView.showToast("请输入6-20位确认密码");
                } else {
                    mView.showToast("请输入6-20位密码");
                }
                isCheck = false;
            }
        }
        return isCheck;
    }

    /**
     * 密码格式验证
     *
     * @return false-不符合
     */
    private boolean samePassword(String password, String checkPassword) {
        boolean isSame = true;
        if (!TextUtils.equals(password, checkPassword)) {
            mView.showToast("两次输入的密码不一致");
            isSame = false;
        }
        return isSame;
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
}
