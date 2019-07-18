package com.hll_sc_app.utils;

import android.text.TextUtils;

import com.hll_sc_app.citymall.util.CalendarUtils;

import java.util.Date;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/17
 */

public class DateUtil {

    /**
     * String -> Date
     */
    public static Date parse(String dateString) {
        String format = null;
        if (dateString.length() <= 4) format = Constants.UNSIGNED_HH_MM;
        else if (dateString.length() <= 6) format = Constants.UNSIGNED_YYYY_MM;
        else if (dateString.length() <= 8) format = Constants.UNSIGNED_YYYY_MM_DD;
        else if (dateString.length() <= 10) format = Constants.UNSIGNED_YYYY_MM_DD_HH;
        else if (dateString.length() <= 12) format = Constants.UNSIGNED_YYYY_MM_DD_HH_MM;
        else if (dateString.length() <= 14) format = Constants.UNSIGNED_YYYY_MM_DD_HH_MM_SS;
        else if (dateString.length() <= 17) format = Constants.UNSIGNED_YYYY_MM_DD_HH_MM_SS_SSS;
        if (format == null)
            throw new IllegalArgumentException("The length of dateString can't greater than 17");
        return CalendarUtils.parse(dateString, format);
    }

    /**
     * String -> Date -> String
     */
    public static String getReadableTime(String dateString) {
        if (TextUtils.isEmpty(dateString)) return "";
        String[] array = null;
        if (dateString.length() <= 4)
            array = new String[]{Constants.UNSIGNED_HH_MM, Constants.SIGNED_HH_MM};
        else if (dateString.length() <= 6)
            array = new String[]{Constants.UNSIGNED_YYYY_MM, Constants.SIGNED_YYYY_MM};
        else if (dateString.length() <= 8)
            array = new String[]{Constants.UNSIGNED_YYYY_MM_DD, Constants.SIGNED_YYYY_MM_DD};
        else if (dateString.length() <= 10)
            array = new String[]{Constants.UNSIGNED_YYYY_MM_DD_HH, Constants.SIGNED_YYYY_MM_DD_HH};
        else if (dateString.length() <= 12)
            array = new String[]{Constants.UNSIGNED_YYYY_MM_DD_HH_MM, Constants.SIGNED_YYYY_MM_DD_HH_MM};
        else if (dateString.length() <= 14)
            array = new String[]{Constants.UNSIGNED_YYYY_MM_DD_HH_MM_SS, Constants.SIGNED_YYYY_MM_DD_HH_MM_SS};
        else if (dateString.length() <= 17)
            array = new String[]{Constants.UNSIGNED_YYYY_MM_DD_HH_MM_SS_SSS, Constants.SIGNED_YYYY_MM_DD_HH_MM_SS_SSS};
        if (array == null)
            throw new IllegalArgumentException("The length of dateString can't greater than 17");
        return CalendarUtils.getDateFormatString(dateString, array[0], array[1]);
    }
}
