package com.hll_sc_app.base.utils;

import android.os.Build;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;

import java.util.Locale;

public class PhoneUtil {
    /**
     * 格式化手机号
     *
     * @param phoneNum 手机号
     */
    public static String formatPhoneNum(String phoneNum) {
        String result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            result = PhoneNumberUtils.formatNumber(phoneNum, Locale.CHINA.getCountry());
        else result = PhoneNumberUtils.formatNumber(phoneNum);
        return TextUtils.isEmpty(result) ? phoneNum : result;
    }
}
