package com.hll_sc_app.app;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.R;
import com.hll_sc_app.api.VipService;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.utils.UserConfig;
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
        new RequestPermissionUtils(this, PERMISSIONS, this::getVipService).requestPermission();
    }

    private void getVipService() {
        if (BuildConfig.isDebug) {
            isFirstTime();
            return;
        }
        VipService.INSTANCE.getVipService(new BaseReq<>(new Object()))
                .compose(ApiScheduler.getObservableScheduler())
                .subscribe(new BaseCallback<BaseResp<String>>() {
                    @Override
                    public void onSuccess(BaseResp<String> resp) {
                        initVip(resp);
                        isFirstTime();
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        isFirstTime();
                    }
                });
    }


    public static void initVip(BaseResp<String> resp) {
        if (resp != null && TextUtils.equals(resp.getCode(), "000") && !TextUtils.isEmpty(resp.getUrl())) {
            HttpConfig.updateVipHost("http://" + resp.getUrl());
        } else {
            HttpConfig.updateHost(HttpConfig.Env.ONLINE);
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
