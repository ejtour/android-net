package com.hll_sc_app.app.user.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.GlobalPreference;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.ClearEditText;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录页面
 *
 * @author zhuyingsong
 * @date 2019/6/4
 */
@Route(path = RouterConfig.USER_LOGIN)
public class LoginActivity extends BaseLoadActivity implements LoginContract.ILoginView {
    public static final String CODE_NEED_CHECK = "00120113053";
    public static final String CODE_UN_REGISTER = "该手机号未注册，是否现在注册";
    @BindView(R.id.edt_phone)
    ClearEditText mEdtPhone;
    @BindView(R.id.edt_password)
    ClearEditText mEdtPWD;
    @BindView(R.id.txt_login)
    TextView mTxtLogin;
    @Autowired(name = RouterUtil.DESTINATION)
    String mDestination;
    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        mPresenter = LoginPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        if (UserConfig.isLogin()) {
            toManageActivity();
        }
    }

    private void initView() {
        InputTextWatcher textWatcher = new InputTextWatcher();
        mEdtPhone.setText(GlobalPreference.getParam(LoginPresenter.LOGIN_PHONE, ""));
        mEdtPhone.addTextChangedListener(textWatcher);
        mEdtPWD.addTextChangedListener(textWatcher);
    }

    private void toManageActivity() {
        RouterUtil.goToActivity(RouterConfig.ROOT_HOME, this);
    }

    @Override
    public void loginSuccess(String authType) {
        if (!TextUtils.isEmpty(mDestination)) {
            ARouter.getInstance().build(mDestination).with(getIntent().getExtras()).setProvider(new LoginInterceptor())
                .navigation(LoginActivity.this, new NavCallback() {
                    @Override
                    public void onArrival(Postcard postcard) {
                        LoginActivity.this.finish();
                    }
                });
            return;
        }
        if (TextUtils.isEmpty(authType)) {
            toManageActivity();
            return;
        }
        List<String> list = Arrays.asList(authType.split(","));
        if (list.size() == 1 && list.contains("2")) {
            toManageActivity();
            return;
        }
        if (list.size() > 1 && list.contains("1")) {
            showChoiceDialog();
        }
    }

    @Override
    public void showChoiceDialog() {
        LoginChoiceDialog dialog = new LoginChoiceDialog(this);
        dialog.setCancelable(false);
        dialog.setAddClickListener(new LoginChoiceDialog.ItemClickListener() {

            @Override
            public void toManage() {
                toManageActivity();
            }

            @Override
            public void toBusiness() {
                // TODO:销售CRM
            }

            @Override
            public void noLogin() {
                UserConfig.clearToken();
            }
        });
        dialog.show();
    }

    @Override
    public void showError(UseCaseException e) {
        if (TextUtils.equals(e.getCode(), CODE_NEED_CHECK)) {
            // 子账号需要验证码登录
            showCheckDialog();
        } else if (TextUtils.equals(e.getMsg(), CODE_UN_REGISTER)) {
            // 帐号未注册
            showTipsDialog(e);
        } else {
            super.showError(e);
        }
    }

    public void showCheckDialog() {
        LoginCheckDialog dialog = new LoginCheckDialog(this, getLoginPhone(), this);
        dialog.setCancelable(false);
        dialog.setAddClickListener(code -> mPresenter.toLogin(getLoginPhone(), getLoginPWD(), code));
        dialog.show();
    }

    public void showTipsDialog(UseCaseException e) {
        SuccessDialog.newBuilder(this)
            .setImageTitle(R.drawable.ic_dialog_failure)
            .setImageState(R.drawable.ic_dialog_state_failure)
            .setMessageTitle("登录失败")
            .setMessage(e.getMessage())
            .setButton((dialog, item) -> {
                if (item == 1) {
                    toRegister();
                }
                dialog.dismiss();
            }, "取消", "去注册")
            .create()
            .show();
    }

    private String getLoginPhone() {
        return mEdtPhone.getText().toString().trim();
    }

    private String getLoginPWD() {
        return mEdtPWD.getText().toString().trim();
    }

    private void toRegister() {
//        RouterUtil.goToActivity(RouterConfig.ACTIVITY_USER_REGISTER);
        showChoiceDialog();
    }

    @OnClick({R.id.txt_login, R.id.txt_findPWD, R.id.txt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_login:
                mPresenter.toLogin(getLoginPhone(), getLoginPWD(), null);
                break;
            case R.id.txt_findPWD:
//                RouterUtil.goToActivity(RouterConfig.USER_FIND_PWD, this);
                break;
            case R.id.txt_register:
                toRegister();
                break;
            default:
                break;
        }
    }

    private class InputTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // no-op
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // no-op
        }

        @Override
        public void afterTextChanged(Editable s) {
            mTxtLogin.setEnabled(!TextUtils.isEmpty(getLoginPhone()) && !TextUtils.isEmpty(getLoginPWD()));
        }
    }
}
