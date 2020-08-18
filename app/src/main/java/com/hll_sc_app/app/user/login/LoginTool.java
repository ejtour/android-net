package com.hll_sc_app.app.user.login;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.GlobalPreference;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.AccountBean;
import com.hll_sc_app.base.bean.LoginResp;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.KeyboardWatcher;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/10
 */

public class LoginTool implements KeyboardWatcher.SoftKeyboardStateListener {
    public static final String CODE_NEED_CHECK = "00120113053";
    public static final String CODE_UN_REGISTER = "该手机号未注册，是否现在注册";
    private static final List<Activity> LOGIN_ACTIVITY_LIST = new ArrayList<>();

    public static void pushActivity(Activity activity) {
        LOGIN_ACTIVITY_LIST.add(activity);
    }

    public static void popActivity(Activity activity) {
        LOGIN_ACTIVITY_LIST.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : LOGIN_ACTIVITY_LIST) {
            activity.finish();
        }
    }

    private final Activity mActivity;
    private final String mDestination;
    private KeyboardWatcher mKeyboardWatcher;

    public LoginTool(Activity activity, String destination) {
        mActivity = activity;
        mDestination = destination;
        mKeyboardWatcher = new KeyboardWatcher(mActivity);
        mKeyboardWatcher.addSoftKeyboardStateListener(this);
    }

    public void release() {
        mKeyboardWatcher.removeSoftKeyboardStateListener(this);
    }

    @Override
    public void onSoftKeyboardOpened(int keyboardHeightInPx) {
        View view = mActivity.getCurrentFocus();
        if (view instanceof EditText && ((EditText) view).getInputType() == (EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD)) {
            return;
        }
        GlobalPreference.putParam(Constants.KEYBOARD_KEY, keyboardHeightInPx);
    }

    @Override
    public void onSoftKeyboardClosed() {
        // no-op
    }

    public void handleSuccess(LoginResp resp, String phone) {
        GlobalPreference.putParam(UserConfig.ACCESS_TOKEN, resp.getAccessToken());
        GlobalPreference.putParam(UserConfig.WX_UNION_ID, resp.getUnionId());
        UserBean userBean = resp.getUser();
        userBean.setAccessToken(resp.getAccessToken());
        String authType = userBean.getAuthType();
        if (!TextUtils.isEmpty(authType) && !authType.contains(",")) {
            userBean.setCurRole(authType);
        }
        GreenDaoUtils.updateUser(userBean);
        if (!CommonUtils.isEmpty(resp.getShops())) {
            GreenDaoUtils.updateShopList(resp.getShops());
        }
        if (!TextUtils.isEmpty(phone)) {
            //存储登录的账号
            AccountBean accountBean = new AccountBean();
            accountBean.setAccount(phone);
            accountBean.setTime(Calendar.getInstance().getTimeInMillis());
            GreenDaoUtils.updateAccount(accountBean);
        }

        if (!TextUtils.isEmpty(mDestination)) {
            jump(mDestination, mActivity.getIntent().getExtras());
            return;
        }
        if (TextUtils.isEmpty(authType) || !authType.contains(",") || !authType.contains("1")) {
            toHomePage();
        } else {
            showChoiceDialog();
        }
    }

    private void toHomePage() {
        jump(RouterConfig.ROOT_HOME, null);
    }

    private void jump(String path, Bundle bundle) {
        ARouter.getInstance().build(path).with(bundle).setProvider(new LoginInterceptor())
                .navigation(mActivity, new NavCallback() {
                    @Override
                    public void onArrival(Postcard postcard) {
                        finishAll();
                    }
                });
    }

    public void handleFailure(UseCaseException e, String phone, LoginCheckDialog.ItemClickListener listener) {
        if (TextUtils.equals(e.getCode(), CODE_NEED_CHECK)) {
            // 子账号需要验证码登录
            showCheckDialog(phone, listener);
        } else if (TextUtils.equals(e.getMsg(), CODE_UN_REGISTER)) {
            // 帐号未注册
            showTipsDialog(e.getMsg());
        } else {
            ToastUtils.showShort(e.getMessage());
        }
    }

    public void showChoiceDialog() {
        LoginChoiceDialog dialog = new LoginChoiceDialog(mActivity);
        dialog.setCancelable(false);
        dialog.setAddClickListener(new LoginChoiceDialog.ItemClickListener() {

            @Override
            public void toManage() {
                UserBean user = GreenDaoUtils.getUser();
                user.setCurRole(user.getAuthType().contains("2") ? "2" : "3");
                GreenDaoUtils.updateUser(user);
                toHomePage();
            }

            @Override
            public void toBusiness() {
                UserBean user = GreenDaoUtils.getUser();
                user.setCurRole("1");
                GreenDaoUtils.updateUser(user);
                toHomePage();
            }

            @Override
            public void noLogin() {
                UserConfig.clearToken();
            }
        });
        dialog.show();
    }

    public void showCheckDialog(String phone, LoginCheckDialog.ItemClickListener listener) {
        LoginCheckDialog dialog = new LoginCheckDialog(mActivity, phone);
        dialog.setCancelable(false);
        dialog.setAddClickListener(listener);
        dialog.show();
    }

    public void showTipsDialog(String msg) {
        SuccessDialog.newBuilder(mActivity)
                .setImageTitle(R.drawable.ic_dialog_failure)
                .setImageState(R.drawable.ic_dialog_state_failure)
                .setMessageTitle("登录失败")
                .setMessage(msg)
                .setButton((dialog, item) -> {
                    if (item == 1) {
                        RouterUtil.goToActivity(RouterConfig.USER_REGISTER);
                    }
                    dialog.dismiss();
                }, "取消", "去注册")
                .create()
                .show();
    }
}
