package com.hll_sc_app.app.wallet.common;

import android.app.Activity;
import android.text.TextUtils;
import android.view.Gravity;

import com.hll_sc_app.base.dialog.TipsDialog;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.ToastUtils;

import java.util.Date;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/1
 */

public class WalletHelper {
    public static final String PERMANENT_DATE = "99991231";

    public static String transformDate(String date) {
        if (TextUtils.equals(PERMANENT_DATE, date)) {
            return " 长期有效";
        } else if (TextUtils.isEmpty(date) || TextUtils.equals("0", date)) {
            return "";
        } else {
            return CalendarUtils.getDateFormatString(date, CalendarUtils.FORMAT_LOCAL_DATE, CalendarUtils.FORMAT_YYYY_MM_DD_CHN);
        }
    }

    public static void showLongValidDateDialog(Activity activity, TipsDialog.OnClickListener listener) {
        TipsDialog.newBuilder(activity)
                .setTitle("选择有效期")
                .setMessage("如果证件长期有效，请选择\"长期有效\",否则选择具体时间")
                .setButton(listener, "长期有效", "具体时间")
                .create()
                .show();
    }

   public static void showDate(Activity activity, DateWindow dateWindow, boolean isStart, String beginDate, String endDate) {
        boolean isBeginDateEmpty = TextUtils.isEmpty(beginDate) || TextUtils.equals("0", beginDate);
        boolean isEndDateEmpty = TextUtils.isEmpty(endDate) || TextUtils.equals("0", endDate);
        if (!isStart && isBeginDateEmpty) {
            ToastUtils.showShort(activity,"请先选择起始日期");
            return;
        }
        if (!isStart && TextUtils.equals("99991231", beginDate)) {
            ToastUtils.showShort(activity,"请先选择起始日期的具体时间");
            return;
        }
        if (isStart) {
            Date date;
            if (isBeginDateEmpty || TextUtils.equals(PERMANENT_DATE, beginDate)) {
                date = new Date();
            } else {
                date = CalendarUtils.parse(beginDate, CalendarUtils.FORMAT_LOCAL_DATE);
            }
            dateWindow.setCalendar(date);
        } else {
            Date date;
            if (isEndDateEmpty || TextUtils.equals(PERMANENT_DATE, endDate)) {
                date = CalendarUtils.parse(beginDate, CalendarUtils.FORMAT_LOCAL_DATE);
            } else {
                date = CalendarUtils.parse(endDate, CalendarUtils.FORMAT_LOCAL_DATE);
            }
            dateWindow.setCalendar(date);
        }
        dateWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.END, 0, 0);
    }
}
