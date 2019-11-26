package com.hll_sc_app.utils;

import android.text.TextUtils;

import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/17
 */

public class DateUtil {

    /**
     * String -> Date
     */
    public static Date parse(String dateString) {
        if (CommonUtils.getLong(dateString) == 0) return null;
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
        return getReadableTime(dateString, null);
    }

    /**
     * String -> Date -> String
     */
    public static String getReadableTime(String dateString, String format) {
        if (CommonUtils.getLong(dateString) == 0) return "";
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
        return CalendarUtils.getDateFormatString(dateString, array[0], TextUtils.isEmpty(format) ? array[1] : format);
    }

    /**
     * 获取周日时间
     *
     * @param week 本周 0 上周 -1 下周 1 以此类推
     * @return
     * @throws ParseException
     */
    public static Long getWeekLastDay(int week, Long date) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.UNSIGNED_YYYY_MM_DD);
        Calendar calendar = new GregorianCalendar();
        try {
            calendar.setTime(sdf.parse(date.toString()));
        } catch (Exception e) {
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
        }
        if (week != 0) {
            calendar.add(Calendar.WEEK_OF_YEAR, week);
        }
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        SimpleDateFormat fmt = new SimpleDateFormat(Constants.UNSIGNED_YYYY_MM_DD);
        return Long.parseLong(fmt.format(calendar.getTime()));
    }

    /**
     * 获取周一时间
     *
     * @param week 本周 0 上周 -1 下周 1 以此类推
     * @return
     * @throws ParseException
     */
    public static Long getWeekFirstDay(int week, Long date) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.UNSIGNED_YYYY_MM_DD);
        Calendar calendar = new GregorianCalendar();
        try {
            calendar.setTime(sdf.parse(date.toString()));
        } catch (Exception e) {
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.DAY_OF_YEAR, -1);
        }
        if (week != 0) {
            calendar.add(Calendar.WEEK_OF_YEAR, week);
        }
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        SimpleDateFormat fmt = new SimpleDateFormat(Constants.UNSIGNED_YYYY_MM_DD);
        return Long.parseLong(fmt.format(calendar.getTime()));
    }

    /**
     * 获取月第一天时间
     *
     * @param month 本月 0 上月  -1 下月  1 以此类推
     * @return
     * @throws ParseException
     */

    public static Long getMonthFirstDay(int month, Long date) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.UNSIGNED_YYYY_MM_DD);
        Calendar calendar = new GregorianCalendar();
        try {
            calendar.setTime(sdf.parse(date.toString()));
        } catch (Exception e) {
        }
        if (month != 0) {
            calendar.add(Calendar.MONTH, month);
        }
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat fmt = new SimpleDateFormat(Constants.UNSIGNED_YYYY_MM_DD);
        return Long.parseLong(fmt.format(calendar.getTime()));
    }

    /**
     * 获取月第一天时间
     *
     * @param month 本月 0 上月  -1 下月  1 以此类推
     * @return
     * @throws ParseException
     */

    public static Long getMonthLastDay(int month, Long date) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.UNSIGNED_YYYY_MM_DD);
        Calendar calendar = new GregorianCalendar();
        try {
            calendar.setTime(sdf.parse(date.toString()));
        } catch (Exception e) {
        }
        if (month != 0) {
            calendar.add(Calendar.MONTH, month);
        }
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat fmt = new SimpleDateFormat(Constants.UNSIGNED_YYYY_MM_DD);
        return Long.parseLong(fmt.format(calendar.getTime()));
    }

    /**
     * cd
     * 获取月第一天时间
     *
     * @param month 本月 0 上月  -1 下月  1 以此类推
     * @return
     */

    public static Long getMonthFirstDay(int month) {
        return getMonthFirstDay(month, currentTimeHllDT8());

    }

    /**
     * 获取月最后一天时间
     *
     * @param month 本月 0 上月  -1 下月  1 以此类推
     * @return
     */

    public static Long getMonthLastDay(int month) {
        return getMonthLastDay(month, currentTimeHllDT8());
    }

    /**
     * 获取周一时间
     *
     * @param week 本周 0 上周 -1 下周 1 以此类推
     * @return
     */
    public static Long getWeekFirstDay(int week) {
        return getWeekFirstDay(week, currentTimeHllDT8());
    }

    /**
     * 获取周日时间
     *
     * @param week 本周 0 上周 -1 下周 1 以此类推
     * @return
     */
    public static Long getWeekLastDay(int week) {
        return getWeekLastDay(week, currentTimeHllDT8());
    }

    /**
     * 返回当前时间的哗啦啦时间戳,格式yyyyMMdd
     *
     * @return
     */
    public static Long currentTimeHllDT8() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.UNSIGNED_YYYY_MM_DD);
        String formatDate = sdf.format(now);
        return Long.valueOf(formatDate);

    }
}
