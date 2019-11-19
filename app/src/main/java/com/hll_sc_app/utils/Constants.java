package com.hll_sc_app.utils;

import com.hll_sc_app.BuildConfig;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/5
 */

public interface Constants {
    String WX_APP_ID = "wx1c0b06dc21cfdb93";
    String QQ_APP_ID = BuildConfig.DEBUG ? "101536988" : "1106551913";

    String UNSIGNED_HH_MM = "HHmm";
    String UNSIGNED_YYYY_MM = "yyyyMM";
    String UNSIGNED_YYYY_MM_DD = "yyyyMMdd";
    String UNSIGNED_YYYY_MM_DD_HH = "yyyyMMddHH";
    String UNSIGNED_YYYY_MM_DD_HH_MM = "yyyyMMddHHmm";
    String UNSIGNED_YYYY_MM_DD_HH_MM_SS = "yyyyMMddHHmmss";
    String UNSIGNED_YYYY_MM_DD_HH_MM_SS_SSS = "yyyyMMddHHmmssSSS";
    String SIGNED_HH_MM = "HH:mm";
    String SIGNED_YYYY_MM = "yyyy-MM";
    String SIGNED_YYYY_MM_DD = "yyyy-MM-dd";
    String SIGNED_YYYY_MM_DD_HH = "yyyy-MM-dd HH";
    String SIGNED_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    String SIGNED_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    String SIGNED_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    String SLASH_MM_DD = "MM/dd";
    String SLASH_YYYY_MM_DD = "yyyy/MM/dd";
    String SLASH_YYYY_MM_DD_HH_MM = "yyyy/MM/dd HH:mm";
    String SLASH_YYYY_MM_DD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";


    String WEB_TITLE = "web_title";
    String WEB_JS_FUNCTION = "js_function";
    String WEB_JS_NAME = "js_bridge_name";
    String WEB_URL = "web_url";
    String WEB_ZOOM = "web_zoom";

    int SEARCH_RESULT_CODE = 0x711;

    String KEYBOARD_KEY = "keyboard_key";
}
