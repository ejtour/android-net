package com.hll_sc_app.app.user.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.MyApplication;
import com.hll_sc_app.R;
import com.hll_sc_app.app.user.login.bind.BindActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.GlobalPreference;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.LoginResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.StatusBarUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.WXEvent;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.utils.MessageUtil;
import com.hll_sc_app.widget.LoginView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * 登录页面
 *
 * @author zhuyingsong
 * @date 2019/6/4
 */
@Route(path = RouterConfig.USER_LOGIN, extras = Constant.AUTH_PROCESS)
public class LoginActivity extends BaseLoadActivity implements LoginContract.ILoginView {
    @Autowired(name = RouterUtil.DESTINATION)
    String mDestination;
    @BindView(R.id.ll_content)
    RelativeLayout mLlContent;
    @BindView(R.id.login_view)
    LoginView mLoginView;
    @BindView(R.id.ll_toLogin)
    LinearLayout mLlToLogin;
    @BindView(R.id.img_wx_login)
    ImageView mWxLogin;
    @BindView(R.id.al_env)
    TextView mEnv;
    private LoginPresenter mPresenter;
    private LoginTool mLoginTool;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginTool.pushActivity(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        ARouter.getInstance().inject(this);
        UserConfig.clearToken();
        MessageUtil.instance().setUnreadNum(0);
        initView();
        mLoginTool = new LoginTool(this, mDestination);
        mPresenter = LoginPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    @Override
    protected void initSystemBar() {
        StatusBarUtil.setTranslucent(this);
    }

    @Override
    protected void onDestroy() {
        LoginTool.popActivity(this);
        EventBus.getDefault().unregister(this);
        if (mLoginTool != null) {
            mLoginTool.release();
            mLoginTool = null;
        }
        super.onDestroy();
    }

    @Subscribe
    public void handleWXEvent(WXEvent event) {
        mPresenter.wxAuth(event.getCode());
    }

    private void initView() {
        if (!BuildConfig.DEBUG) {
            mEnv.setVisibility(View.GONE);
        } else {
            mEnv.setVisibility(View.VISIBLE);
            StatusBarUtil.fitSystemWindowsWithMarginTop(mEnv);
            mEnv.setText(GlobalPreference.getParam(HttpConfig.KEY, HttpConfig.Env.TEST));
        }
        int sh = UIUtils.getScreenHeight(this);
        mLlContent.post(() -> ObjectAnimator.ofFloat(mLlContent, "translationY", sh,
                sh - UIUtils.dip2px(295)).setDuration(800).start());
    }

    @Override
    public void loginSuccess(LoginResp resp) {
        mLoginTool.handleSuccess(resp, mLoginView.getLoginPhone());
    }

    @OnTouch(R.id.ll_content)
    public boolean onTouch(View view) {
        ViewUtils.clearEditFocus(view);
        view.postDelayed(() -> {
            if (mLoginView != null) {
                mLoginView.requestFocus();
            }
        }, 100);
        return false;
    }

    @OnClick({R.id.vl_login, R.id.txt_toLogin, R.id.txt_toRegister, R.id.img_wx_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.vl_login:
                mPresenter.toLogin(mLoginView.getLoginPhone(), mLoginView.getLoginPassword(), null);
                break;
            case R.id.txt_toRegister:
                toRegister();
                break;
            case R.id.txt_toLogin:
                toLogin();
                break;
            case R.id.img_wx_login:
                wxLogin();
                break;
            default:
                break;
        }
    }

    private void wxLogin() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "22city_sc";
        MyApplication.getInstance().getWxApi().sendReq(req);
    }

    @Override
    public void toBind(String unionId) {
        BindActivity.start(getIntent().getExtras(), mDestination, "unionId", unionId);
    }

    @Override
    public void showError(UseCaseException e) {
        mLoginTool.handleFailure(e, mLoginView.getLoginPhone(), code -> mPresenter.toLogin(mLoginView.getLoginPhone(), mLoginView.getLoginPassword(), code));
    }

    private void toRegister() {
        RouterUtil.goToActivity(RouterConfig.USER_REGISTER);
    }

    private void toLogin() {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mLlContent, "translationY", 0);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mLlToLogin, "alpha", 1, 0);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(mLoginView, "alpha", 0, 1);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(mWxLogin, "alpha", 0, 1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator1).with(animator2).with(animator3).with(animator4);
        animatorSet.setDuration(500);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mLlToLogin.setVisibility(View.INVISIBLE);
                int[] location = new int[2];
                mLlContent.getLocationOnScreen(location);
            }
        });
        animatorSet.start();
        mLoginView.setVisibility(View.VISIBLE);
        mWxLogin.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.al_env)
    public void showEnvWindow(View view) {
        PopupMenu popup = new PopupMenu(this, view, Gravity.END);
        popup.getMenuInflater().inflate(R.menu.menu_login_env, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            String env = HttpConfig.Env.TEST;
            if (item.getItemId() == R.id.menu_item_online) {
                env = HttpConfig.Env.ONLINE;
            } else if (item.getItemId() == R.id.menu_item_vip) {
                env = HttpConfig.Env.VIP;
            } else if (item.getItemId() == R.id.menu_item_dev) {
                env = HttpConfig.Env.DEV;
            }
            mEnv.setText(env);
            HttpConfig.updateHost(env);
            restartApp(this);
            return true;
        });
        popup.show();
    }

    public static void restartApp(Context context) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        Process.killProcess(Process.myPid());
    }
}
