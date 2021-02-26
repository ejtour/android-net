package com.hll_sc_app.app.user.login.bind;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.user.login.LoginContract;
import com.hll_sc_app.app.user.login.LoginPresenter;
import com.hll_sc_app.app.user.login.LoginTool;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.LoginResp;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.widget.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/10
 */

@Route(path = RouterConfig.USER_BIND, extras = Constant.AUTH_PROCESS)
public class BindActivity extends BaseLoadActivity implements LoginContract.ILoginView {
    @Autowired(name = RouterUtil.DESTINATION)
    String mDestination;
    @Autowired(name = "bindKey")
    String mBindKey;
    @Autowired(name = "bindValue")
    String mBindValue;
    @BindView(R.id.ab_login_view)
    LoginView mLoginView;
    private LoginTool mLoginTool;
    private LoginContract.ILoginPresenter mPresenter;

    /**
     * @param bundle 原传参
     * @param destination 原路由
     * @param bindKey     绑定键
     * @param bindValue   绑定值
     */
    public static void start(Bundle bundle, String destination, String bindKey, String bindValue) {
        ARouter.getInstance().build(RouterConfig.USER_BIND)
                .with(bundle)
                .withString(RouterUtil.DESTINATION, destination)
                .withString("bindKey", bindKey)
                .withString("bindValue", bindValue)
                .navigation();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginTool.pushActivity(this);
        setContentView(R.layout.activity_bind);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        mLoginTool = new LoginTool(this, mDestination);
        mPresenter = new LoginPresenter();
        mPresenter.register(this);
    }

    @Override
    protected void onDestroy() {
        LoginTool.popActivity(this);
        if (mLoginTool != null) {
            mLoginTool.release();
            mLoginTool = null;
        }
        super.onDestroy();
    }

    @OnClick(R.id.ab_close)
    @Override
    public void finish() {
        super.finish();
    }

    @OnClick(R.id.vl_login)
    public void bind() {
        mPresenter.toLogin(mLoginView.getLoginPhone(), mLoginView.getLoginPassword(), null);
    }

    @Override
    public void loginSuccess(LoginResp resp) {
        mLoginTool.handleSuccess(resp, mLoginView.getLoginPhone());
    }

    @Override
    public void showError(UseCaseException e) {
        mLoginTool.handleFailure(e, mLoginView.getLoginPhone(), code -> mPresenter.toLogin(mLoginView.getLoginPhone(), mLoginView.getLoginPassword(), code));
    }

    @Override
    public String getBindKey() {
        return mBindKey;
    }

    @Override
    public String getBindValue() {
        return mBindValue;
    }
}
