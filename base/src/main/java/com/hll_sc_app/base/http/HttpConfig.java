package com.hll_sc_app.base.http;


import android.text.TextUtils;

import com.hll_sc_app.base.BuildConfig;
import com.hll_sc_app.base.GlobalPreference;

/**
 * Http配置
 *
 * @author zhuyingsong
 * @date 2018/12/18
 */
public class HttpConfig {
    public static final String KEY = "env";
    /**
     * url-path
     */
    public static final String URL = "/shopmall/post/req";
    /**
     * 小流量默认地址
     */
    private static final String VIP_HOST_DEFAULT = "http://vip.22city.cn";
    private static String mEnv = GlobalPreference.getParam(KEY, Env.TEST);
    private static String VIP_HOST;

    /**
     * 当前配置是否小流量
     *
     * @return true
     */
    public static boolean isVip() {
        return TextUtils.equals(mEnv, Env.VIP);
    }

    /**
     * 当前配置是否正式环境
     *
     * @return true
     */
    public static boolean isOnline() {
        return TextUtils.equals(mEnv, Env.ONLINE);
    }

    public static void updateHost(String env) {
        mEnv = env;
        GlobalPreference.putParam(KEY, mEnv);
    }

    /**
     * 更新小流量的地址
     *
     * @param url 小流量的地址
     */
    public static void updateVipHost(String url) {
        VIP_HOST = url;
        mEnv = Env.VIP;
        GlobalPreference.putParam(KEY, mEnv);
    }

    /**
     * postUrl
     *
     * @return url
     */
    public static String getHost() {
        if (!BuildConfig.isDebug) {
            if (TextUtils.equals(Env.VIP, mEnv)) {
                return TextUtils.isEmpty(VIP_HOST) ? VIP_HOST_DEFAULT : VIP_HOST;
            }
            return "http://mobile.22city.cn";
        }
        switch (mEnv) {
            case Env.TEST:
                return "http://test.22city.cn";
            case Env.DEV:
                return "http://172.16.32.108:8991";
            case Env.ONLINE:
                return "http://mobile.22city.cn";
            case Env.VIP:
                return TextUtils.isEmpty(VIP_HOST) ? VIP_HOST_DEFAULT : VIP_HOST;
            default:
                return "http://test.22city.cn";
        }
    }

    /**
     * 请求小流量url的url
     */
    public static String getVipHost() {
        if (!BuildConfig.isDebug) {
            return "http://mobile.22city.cn";
        }
        switch (mEnv) {
            case Env.TEST:
                return "http://172.16.32.108:8991";
            case Env.DEV:
                return "http://test.22city.cn";
            case Env.ONLINE:
            case Env.VIP:
                return "http://mobile.22city.cn";
            default:
                return "http://test.22city.cn";
        }
    }

    /**
     * 帮助中心的url
     */
    public static String getHelpCenterHost() {
        if (!BuildConfig.isDebug) {
            return "http://flea.22city.cn/help_mobile";
        }
        switch (mEnv) {
            case Env.TEST:
            case Env.DEV:
                return "http://172.16.32.222:3002/help_mobile";
            case Env.ONLINE:
            case Env.VIP:
                return "http://flea.22city.cn/help_mobile";
            default:
                return "http://172.16.32.222:3002/help_mobile";
        }
    }

    /**
     * IM的url
     */
    public static String getIMHost() {
        if (!BuildConfig.isDebug) {
            return "im.22city.cn";
        }
        switch (mEnv) {
            case Env.TEST:
            case Env.DEV:
                return "172.20.5.169";
            case Env.ONLINE:
            case Env.VIP:
                return "im.22city.cn";
            default:
                return "172.20.5.169";
        }
    }

    /**
     * 消息的url
     */
    public static String getMessageHost() {
        if (!BuildConfig.isDebug) {
            return "http://message.22city.cn";
        }
        switch (mEnv) {
            case Env.TEST:
                return "http://172.16.32.108:8991";
            case Env.DEV:
                return "http://test.22city.cn";
            case Env.ONLINE:
            case Env.VIP:
                return "http://message.22city.cn";
            default:
                return "http://test.22city.cn";
        }
    }

    public interface Env {
        String TEST = "测试环境";
        String ONLINE = "正式环境";
        String VIP = "小流量";
        String DEV = "开发环境";
    }
}
