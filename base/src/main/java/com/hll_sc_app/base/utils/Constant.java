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
     */
    public static final int LOGIN_EXTRA = 1;
    /**
     * 图片缓存子目录
     */
    public static final String GLIDE_CACHE_DIR = App.INSTANCE.getCacheDir() + "/glideCache" + "";

}
