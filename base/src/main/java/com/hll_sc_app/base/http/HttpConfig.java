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
    private static final String VIP_HOST_DEFAULT = "https://api.http.5s.22city.com";
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
            return "https://api.http.5s.22city.com";
        }
        switch (mEnv) {
            case Env.DEV:
                return "http://shop-mall-5s-gw-dohko-5s.dohko.hualala.com/";
            case Env.ONLINE:
                return "http://api.http.5s.22city.com";
            case Env.VIP:
                return TextUtils.isEmpty(VIP_HOST) ? VIP_HOST_DEFAULT : VIP_HOST;
            case Env.TEST:
            default:
                return "http://shop-mall-5s-gw-dohko-5s.dohko.hualala.com/";
        }
    }

    /**
     * 请求小流量url的url
     */
    public static String getVipHost() {
        if (!BuildConfig.isDebug) {
            return "https://api.http.5s.22city.com";
        }
        switch (mEnv) {
            case Env.DEV:
                return "http://shop-mall-5s-gw-dohko-5s.dohko.hualala.com/";
            case Env.ONLINE:
            case Env.VIP:
                return "http://api.http.5s.22city.com";
            case Env.TEST:
            default:
                return "http://shop-mall-5s-gw-dohko-5s.dohko.hualala.com/";
        }
    }

    /**
     * 帮助中心的url
     */
    public static String getWebHost() {
        if (!BuildConfig.isDebug) {
            return "https://flea.22city.cn";
        }
        switch (mEnv) {
            case Env.ONLINE:
            case Env.VIP:
                return "https://flea.22city.cn";
            case Env.TEST:
            case Env.DEV:
            default:
                return "http://172.16.0.39:3002";
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
            case Env.ONLINE:
            case Env.VIP:
                return "im.22city.cn";
            case Env.TEST:
            case Env.DEV:
            default:
                return "172.20.5.169";
        }
    }

    /**
     * 消息的url
     */
    public static String getMessageHost() {
        if (!BuildConfig.isDebug) {
            return "https://api.http.5s.22city.com";
        }
        switch (mEnv) {
            case Env.DEV:
                return "http://shop-mall-5s-gw-dohko-5s.dohko.hualala.com/";
            case Env.ONLINE:
            case Env.VIP:
                return "http://api.http.5s.22city.com";
            case Env.TEST:
            default:
                return "http://shop-mall-5s-gw-dohko-5s.dohko.hualala.com/";
        }
    }

    public static String getSupplyChainHost(){
        if (!BuildConfig.isDebug) {
            return "https://scm.hualala.com";
        }
        switch (mEnv) {
            case Env.ONLINE:
            case Env.VIP:
                return "https://scm.hualala.com";
            case Env.DEV:
            case Env.TEST:
            default:
                return "http://dohko.supplychain.hw.hualala.com";
        }
    }

    public interface Env {
        String TEST = "测试环境";
        String ONLINE = "正式环境";
        String VIP = "小流量";
        String DEV = "开发环境";
    }
}
