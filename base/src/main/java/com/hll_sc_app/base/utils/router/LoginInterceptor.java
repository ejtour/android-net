package com.hll_sc_app.base.utils.router;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.hll_sc_app.base.utils.Constant;

/**
 * 登录的拦截器
 *
 * @author zhuyingsong
 * @date 2018-12-14
 */
@Interceptor(priority = 8, name = Constant.LOGIN_INTERCEPTOR)
public class LoginInterceptor implements IInterceptor {
    private Context mContext;

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
//        if (postcard.getExtra() == Constant.LOGIN_EXTRA) {
//            if (UserConfig.isLogin()) {
//                checkRight(postcard, callback);
//            } else {
//                noLogin(postcard, callback);
//            }
//        } else {
//            checkRight(postcard, callback);
//        }
    }

    /**
     * 正常跳转前判断权限
     *
     * @param postcard postcard
     * @param callback callback
     */
    private void checkRight(Postcard postcard, InterceptorCallback callback) {
//        if (RightConfig.checkRight(RightConfig.getRightCode(mContext, postcard.getPath()))) {
//            callback.onContinue(postcard);
//        } else {
//            callback.onContinue(null);
//            Looper.prepare();
//            ToastUtils.showShort(mContext, mContext.getString(R.string.right_tips));
//            Looper.loop();
//        }
    }

    /**
     * 未登录跳转到登录页面,传递目标的跳转地址
     *
     * @param postcard postcard
     * @param callback callback
     */
    private void noLogin(Postcard postcard, InterceptorCallback callback) {
        callback.onInterrupt(null);
        RouterUtil.goToLogin(postcard.getPath(), postcard.getExtras());
    }

    @Override
    public void init(Context context) {
        this.mContext = context;
    }
}
