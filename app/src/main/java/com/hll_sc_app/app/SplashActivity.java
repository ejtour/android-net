package com.hll_sc_app.app;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.permission.RequestPermissionUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
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
        super.onCreate(savedInstanceState);
        new RequestPermissionUtils(this, PERMISSIONS, this::isFirstTime).requestPermission();
    }


    private void isFirstTime() {
        ARouter.getInstance().build(RouterConfig.USER_LOGIN)
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
