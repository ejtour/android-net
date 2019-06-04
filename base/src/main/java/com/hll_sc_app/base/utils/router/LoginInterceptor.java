package com.hll_sc_app.base.utils.router;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UserConfig;

/**
 * 登录的拦截器
 *
 * @author zhuyingsong
 * @date 2018-12-14
 */
@Interceptor(priority = 8, name = Constant.LOGIN_INTERCEPTOR)
public class LoginInterceptor implements IInterceptor {

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        if (postcard.getExtra() == Constant.LOGIN_EXTRA) {
            if (!UserConfig.isLogin()) {
                callback.onInterrupt(null);
                RouterUtil.goToLogin(postcard.getPath(), postcard.getExtras());
            }
        } else {
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {
    }
}
