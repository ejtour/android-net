package com.hll_sc_app.base.utils;

import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.base.GlobalPreference;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;

/**
 * UserConfig
 *
 * @author zhuyingsong
 * @date 2018 /12/19
 */
public class UserConfig {
    public static final String ACCESS_TOKEN = "access_token";
    private static String mToken;

    public static boolean isLogin() {
        return !TextUtils.isEmpty(UserConfig.accessToken()) && GreenDaoUtils.getUser() != null;
    }

    /**
     * 获取token
     *
     * @return token
     */
    public static String accessToken() {
        if (TextUtils.isEmpty(mToken)) {
            mToken = GlobalPreference.getParam("access_token", "");
        }
        return mToken;
    }

    public static void reLogin() {
        clearToken();
        ARouter.getInstance().build(RouterConfig.USER_LOGIN)
                .setProvider(new LoginInterceptor())
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                .navigation();
    }

    /**
     * 退出登录时候，置空token
     * 删除存储的用户信息
     */
    public static void clearToken() {
        mToken = "";
        GlobalPreference.putParam(ACCESS_TOKEN, "");
        GreenDaoUtils.clear();
    }

    public static String getGroupID() {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean != null) {
            return userBean.getGroupID();
        }
        return "";
    }

    /**
     * 是否自营
     *
     * @return true-自营
     */
    public static boolean isSelfOperated() {
        boolean selfOperated = false;
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean != null && TextUtils.equals("true", userBean.getSelfOperated())) {
            selfOperated = true;
        }
        return selfOperated;
    }

    /**
     * @return 如果是销售，返回销售员ID，否则返回空字符串
     */
    public static String getSalesmanID() {
        UserBean user = GreenDaoUtils.getUser();
        if (user != null && "1".equals(user.getCurRole())) {
            return user.getEmployeeID();
        }
        return "";
    }

    /**
     * @return 是否 crm
     */
    public static boolean crm() {
        return !TextUtils.isEmpty(getSalesmanID());
    }
}
