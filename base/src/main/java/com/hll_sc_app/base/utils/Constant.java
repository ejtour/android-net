package com.hll_sc_app.base.utils;

import com.hll_sc_app.citymall.App;

/**
 * 常量
 *
 * @author zhuyingsong
 * @date 2018/12/14
 */
public class Constant {
    public static final String LOGIN_INTERCEPTOR = "登录拦截器";
    /**
     * 需要额外登录
     *
     * warn: 由于供应商使用必须登录，改为使用 AUTH_PROCESS 来标记登录相关页面，其他页面都要判定是否登录状态，LOGIN_EXTRA 后续废弃
     */
    public static final int LOGIN_EXTRA = 1;

    /**
     * 由于供应商使用必须登录，改为使用 AUTH_PROCESS 来标记登录相关页面，其他页面都要判定是否登录状态
     */
    public static final int AUTH_PROCESS = 2;

    /**
     * 图片缓存子目录
     */
    public static final String GLIDE_CACHE_DIR = App.INSTANCE.getCacheDir() + "/glideCache" + "";

}
