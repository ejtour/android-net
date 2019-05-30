package com.hll_sc_app.base.utils;

import android.os.Build;
import android.telephony.PhoneNumberUtils;

import java.util.Locale;

public class PhoneUtil {
    /**
     * 格式化手机号
     *
     * @param phoneNum 手机号
     */
    public static String formatPhoneNum(String phoneNum) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return PhoneNumberUtils.formatNumber(phoneNum, Locale.CHINA.getCountry());
        } else {
            return PhoneNumberUtils.formatNumber(phoneNum);
        }
    }
}
