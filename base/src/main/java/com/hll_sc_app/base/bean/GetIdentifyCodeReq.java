package com.hll_sc_app.base.bean;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 得到验证码的请求参数
 *
 * @author zc
 */
public class GetIdentifyCodeReq {
    /**
     * 1-登录（找回密码）,2-注册,3-解绑验证原手机号,4-解绑验证新手机号，5-解绑子账号
     */
    private int flag;
    /**
     * 请求发送验证码的手机号
     */
    private String loginPhone;

    public int getFlag() {
        return flag;
    }

    public void setFlag(@FLAG int flag) {
        this.flag = flag;
    }

    public String getLoginPhone() {

        return loginPhone;
    }

    public void setLoginPhone(String loginPhone) {
        this.loginPhone = loginPhone;
    }

    @IntDef({FLAG.INT_LOGIN, FLAG.INT_REGISTER, FLAG.INT_BIND_OLD, FLAG.INT_BIND_NEW, FLAG.INT_CHILD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FLAG {
        int INT_LOGIN = 1;
        int INT_REGISTER = 2;
        int INT_BIND_OLD = 3;
        int INT_BIND_NEW = 4;
        int INT_CHILD = 5;
    }
}
