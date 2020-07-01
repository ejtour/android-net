package com.hll_sc_app.app.user.register;

import android.text.TextUtils;

import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.user.InviteCodeResp;
import com.hll_sc_app.bean.user.RegisterReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Upload;
import com.hll_sc_app.rest.User;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.regex.Pattern;

import io.reactivex.Observable;

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
    private static Pattern patternGroupName = Pattern.compile("^\\s$");
    private static Pattern patternOperationGroupID = Pattern.compile("^[a-zA-Z0-9]{1,30}$");
    private RegisterContract.IFindView mView;

    static RegisterPresenter newInstance() {
        return new RegisterPresenter();
    }

    @Override
    public void start() {
        if (UserConfig.crm()) {
            User.queryInviteCode(new SimpleObserver<InviteCodeResp>(mView) {
                @Override
                public void onSuccess(InviteCodeResp inviteCodeResp) {
                    mView.setCode(inviteCodeResp.getRecommendCode());
                }
            });
        }
    }

    @Override
    public void register(RegisterContract.IFindView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void toRegister(RegisterReq req) {
        if (!checkPhoneNumber(req.getLoginPhone())
            || !checkPassword(req.getLoginPWD())
            || !checkAgainPassword(req.getCheckLoginPWD())
            || !samePWD(req.getLoginPWD(), req.getCheckLoginPWD())
            || !checkGroupName(req.getGroupName())
            || !checkGroupAddress(req.getGroupAddress())
            || !checkOperationGroupID(req.getOperationGroupID())) {
            return;
        }
        BaseReq<RegisterReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        Observable<BaseResp<Object>> observable;
        if (UserConfig.crm()) observable = UserService.INSTANCE.registerByCrm(baseReq);
        else observable = UserService.INSTANCE.register(baseReq);
        observable.compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                    mView.registerSuccess();
                }

                @Override
                public void onFailure(UseCaseException e) {
                    e.setTag(req);
                    mView.showError(e);
                }
            });
    }

    @Override
    public void uploadImg(String path) {
        Upload.upload(mView, path, mView::uploadSuccess);
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

    /**
     * 公司名称格式验证
     *
     * @return false-不符合
     */
    private boolean checkGroupName(String groupName) {
        if (!TextUtils.isEmpty(groupName)) {
            if (groupName.length() < 3 || patternGroupName.matcher(groupName).matches()) {
                mView.showToast("公司名称仅支持3-100个不含特殊符号的字符");
                return false;
            }
        }
        return true;
    }

    /**
     * 详细地址验证
     *
     * @return false-不符合
     */
    private boolean checkGroupAddress(String address) {
        if (!TextUtils.isEmpty(address)) {
            if (address.length() < 5) {
                mView.showToast("地址最少输入5个字符");
                return false;
            }
        }
        return true;
    }

    /**
     * 推荐码格式验证
     *
     * @return false-不符合
     */
    private boolean checkOperationGroupID(String operationGroupID) {
        if (!TextUtils.isEmpty(operationGroupID)) {
            if (!patternOperationGroupID.matcher(operationGroupID).matches()) {
                mView.showToast("请输入正确的推荐码");
                return false;
            }
        }
        return true;
    }
}
