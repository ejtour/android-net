package com.hll_sc_app.app.wallet.authentication;

import android.app.Activity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.base.widget.ImgShowDelBlock;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.widget.AlertsDialog;

import java.util.Date;

public class CommonMethod {
    static final String PERMANENT_DATE = "99991231";

    /**
     * 反显时间的解析
     *
     * @return
     */
    static String transformDate(String date) {
        if (TextUtils.equals(PERMANENT_DATE, date)) {
            return " 长期有效";
        } else if (TextUtils.isEmpty(date) || TextUtils.equals("0", date)) {
            return "";
        } else {
            return CalendarUtils.getDateFormatString(date, CalendarUtils.FORMAT_LOCAL_DATE, CalendarUtils.FORMAT_YYYY_MM_DD_CHN);
        }
    }

    /**
     * 反显证件类型的解析
     * 根据cardType显示证件类型文本
     *
     * @param type
     * @return
     */
    static String transformCardType(int type) {
        String name = "";
        switch (type) {
            case 0:
                name = "身份证";
                break;
            case 2:
                name = "来往内地通信证";
                break;
            case 4:
                name = "台胞证";
                break;
            case 9:
                name = "护照";
                break;
            default:
                break;
        }
        return name;
    }

    /**
     * 显示起始时间 结束时间
     * 因为个页面的逻辑相同 就抽出来了
     *
     * @param activity
     * @param dateWindow
     * @param isStartDate
     * @param beginDate
     * @param endDate
     * @param addDateClickEvent
     */
    static void showDate(BaseLoadActivity activity, DateWindow dateWindow, boolean isStartDate, String beginDate, String endDate, AddDateClickEvent addDateClickEvent) {
        boolean isBeginDateEmpty = TextUtils.isEmpty(beginDate) || TextUtils.equals("0", beginDate);
        boolean isEndDateEmpty = TextUtils.isEmpty(endDate) || TextUtils.equals("0", endDate);
        if (!isStartDate && isBeginDateEmpty) {
            activity.showToast("请先选择起始日期");
            return;
        }
        if (!isStartDate && TextUtils.equals("99991231", beginDate)) {
            activity.showToast("请先选择起始日期的具体时间");
            return;
        }
        if (dateWindow == null) {
            dateWindow = new DateWindow(activity);
            dateWindow.setSelectListener(date -> {
                String sDate = CalendarUtils.format(date, CalendarUtils.FORMAT_LOCAL_DATE);
                String tDate = CalendarUtils.format(date, CalendarUtils.FORMAT_YYYY_MM_DD_CHN);
                addDateClickEvent.onSelect(sDate, tDate);
            });
        }
        if (isStartDate) {
            Date date;
            if (isBeginDateEmpty || TextUtils.equals("99991231", beginDate)) {
                date = new Date();
            } else {
                date = CalendarUtils.parse(beginDate, CalendarUtils.FORMAT_LOCAL_DATE);
            }
            dateWindow.setCalendar(date);
        } else {
            Date date;
            if (isEndDateEmpty || TextUtils.equals("99991231", endDate)) {
                date = CalendarUtils.parse(beginDate, CalendarUtils.FORMAT_LOCAL_DATE);
            } else {
                date = CalendarUtils.parse(endDate, CalendarUtils.FORMAT_LOCAL_DATE);
            }
            dateWindow.setCalendar(date);
        }
        dateWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.END, 0, 0);
    }


    /**
     * 设置上传组件的显示配置
     *
     * @param deleteListener
     * @param uploadImgListener
     * @param title
     */
    public static void setUploadImg(ImgUploadBlock imgUploadBlock, String title, View.OnClickListener deleteListener, ImgUploadBlock.UploadImgListener uploadImgListener) {
//        imgUploadBlock.setMaxSize(2097152);
        imgUploadBlock.setOnDeleteListener(v -> {
            deleteListener.onClick(v);
        });
        imgUploadBlock.setTitle(title);
        imgUploadBlock.setSubTitle(" ");
        imgUploadBlock.setIconResId(R.drawable.base_ic_img_add);
        imgUploadBlock.setmUploadImgListener(uploadImgListener);
    }

    /**
     * 选择时间先弹出是否选择长期有效的dialog
     *
     * @param activity
     * @param onClickListener
     */
    public static void showLongValidDateDialog(Activity activity, AlertsDialog.OnClickListener onClickListener) {
        new AlertsDialog.Builder(activity)
                .setTitle("选择有效期")
                .setContent("如果证件长期有效，请选择\"长期有效\",否则选择具体时间")
                .addClickListener(onClickListener, "长期有效", "具体时间")
                .create()
                .show();
    }


    public interface AddDateClickEvent {
        /**
         * @param oDate 网络传递的时间格式 20191231
         * @param sDate 页面显示的格式 2019年12月31日
         */
        void onSelect(String oDate, String sDate);
    }


    /**
     * 页面输入框的文本更改后 及时更改walletinfo的内容
     */
    public interface AddInputChangeWalletInfo {
        void onChanged(String value);
    }


}
