package com.hll_sc_app.base.utils;

import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.base.GlobalPreference;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.bean.UserEvent;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;

import org.greenrobot.eventbus.EventBus;

/**
 * UserConfig
 *
 * @author zhuyingsong
 * @date 2018 /12/19
 */
public class UserConfig {
    public static final String ACCESS_TOKEN = "access_token";
    public static final String WX_UNION_ID = "wx_union_id";
    private static String mToken;
    private static boolean sOnlyReceive;
    private static boolean sEnablePrint;
    private static boolean sCrmPlus;
    private static boolean sHindAccounts;//隐藏结算状态

    public static boolean isLogin() {
        return !TextUtils.isEmpty(UserConfig.accessToken()) && GreenDaoUtils.getUser() != null
                && UserConfig.accessToken().equals(GreenDaoUtils.getUser().getAccessToken());
    }

    /**
     * 获取token
     *
     * @return token
     */
    public static String accessToken() {
        if (TextUtils.isEmpty(mToken)) {
            mToken = GlobalPreference.getParam(ACCESS_TOKEN, "");
        }
        return mToken;
    }

    public static void reLogin() {
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
        sOnlyReceive = false;
        sEnablePrint = false;
        mToken = "";
        GlobalPreference.putParam(WX_UNION_ID, "");
        GlobalPreference.putParam(ACCESS_TOKEN, "");
        GreenDaoUtils.clear();
    }

    public static String getGroupID() {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean != null) {
            return userBean.getGroupID() == null ? "" : userBean.getGroupID();
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

    public static void setOnlyReceive(boolean onlyReceive) {
        if (sOnlyReceive != onlyReceive) {
            sOnlyReceive = onlyReceive;
            EventBus.getDefault().post(new UserEvent(UserEvent.ONLY_RECEIVE));
        }
    }

    public static boolean isOnlyReceive() {
        return sOnlyReceive;
    }

    public static void setEnablePrint(boolean enablePrint) {
        if (sEnablePrint != enablePrint) {
            sEnablePrint = enablePrint;
            EventBus.getDefault().post(new UserEvent(UserEvent.ENABLE_PRINT));
        }
    }

    public static boolean isEnablePrint() {
        return sEnablePrint;
    }

    public static void setCrmPlus(boolean crmPlus) {
        if (sCrmPlus != crmPlus) {
            sCrmPlus = crmPlus;
        }
    }

    public static boolean isCrmPlus() {
        return sCrmPlus;
    }

    public static void setHindAccounts(boolean hind){
        if (sHindAccounts != hind) {
            sHindAccounts = hind;
            EventBus.getDefault().post(new UserEvent(UserEvent.HIND_ACCOUNT));
        }
    }

    public static boolean isHindAccounts(){
        return sHindAccounts;
    }
}
