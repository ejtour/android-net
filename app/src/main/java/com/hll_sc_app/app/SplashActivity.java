package com.hll_sc_app.app;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.GlobalPreference;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.permission.RequestPermissionUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.PrivacyDialog;
import com.yanzhenjie.permission.Permission;

/**
 * 闪屏页面
 *
 * @author zhuyingsong
 * @date 2018/12/13
 */
public class SplashActivity extends BaseLoadActivity {
    private static final String[] PERMISSIONS = {Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new RequestPermissionUtils(this, PERMISSIONS, this::showPrivacy).requestPermission();
        super.onCreate(savedInstanceState);
    }

    private void showPrivacy() {
        if (GlobalPreference.getParam(Constants.PRIVACY_KEY, false)) {
            isFirstTime();
        } else {
            new PrivacyDialog(this, this::isFirstTime).show();
        }
    }

    private void isFirstTime() {
        ARouter.getInstance().build(UserConfig.isLogin() ? RouterConfig.ROOT_HOME : RouterConfig.USER_LOGIN)
            .withTransition(R.anim.base_splash_enter_anim, R.anim.base_splash_exit_anim)
            .navigation(this, new NavCallback() {
                @Override
                public void onArrival(Postcard postcard) {
                    finish();
                }
            });
    }

    @Override
    public void onBackPressed() {
        // no-op
    }
}
