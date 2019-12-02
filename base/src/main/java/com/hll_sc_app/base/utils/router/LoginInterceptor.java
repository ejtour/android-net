package com.hll_sc_app.base.utils.router;

import android.content.Context;
import android.os.Looper;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.hll_sc_app.base.R;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.citymall.App;
import com.hll_sc_app.citymall.util.ToastUtils;

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
                return;
            }
        }
        checkRight(postcard, callback);
    }

    /**
     * 正常跳转前判断权限
     *
     * @param postcard postcard
     * @param callback callback
     */
    private void checkRight(Postcard postcard, InterceptorCallback callback) {
        if (RightConfig.checkRight(RightConfig.getRightCode(App.INSTANCE, postcard.getPath()))) {
            callback.onContinue(postcard);
        } else {
            callback.onContinue(null);
            Looper.prepare();
            ToastUtils.showShort(App.INSTANCE.getString(R.string.right_tips));
            Looper.loop();
        }
    }

    @Override
    public void init(Context context) {
    }
}
