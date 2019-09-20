package com.hll_sc_app.app.order.common;

import android.app.Activity;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.hll_sc_app.bean.filter.OrderParam;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.impl.IChangeListener;
import com.hll_sc_app.utils.ColorStr;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/6
 */

public class OrderHelper {

    private static final String[] PAY_TYPES = {"货到付款", "账期支付", "在线支付"};
    private static final String[] PAYMENT_WAYS = {"微信支付", "支付宝支付", "银联支付", "现金支付", "支票支付", "快捷支付", "余额支付", "微信扫码", "支付宝扫码"};

    /**
     * 获取订单支付类型
     */
    public static String getPayType(int payType) {
        if (payType - 1 < 0 || payType - 1 >= PAY_TYPES.length) {
            return "";
        }
        return PAY_TYPES[payType - 1];
    }

    /**
     * 获取支付方式
     */
    public static String getPaymentWay(int way) {
        if (way - 1 < 0 || way - 1 >= PAYMENT_WAYS.length) {
            return "";
        }
        return PAYMENT_WAYS[way - 1];
    }

    public static CharSequence handleExtraInfo(OrderResp resp) {
        String source = null;
        switch (resp.getSubBillStatus()) {
            case 0: // 待转单
            case 1: // 待接单
            case 2: // 待发货
                String formatTime = CalendarUtils.getDateFormatString(resp.getSubBillExecuteDate(),
                        Constants.UNSIGNED_YYYY_MM_DD_HH_MM,
                        Constants.SIGNED_YYYY_MM_DD_HH_MM);
                source = "要求：" + formatTime + "送达";
                SpannableString spannableString = new SpannableString(source);
                spannableString.setSpan(new ForegroundColorSpan(
                                Color.parseColor(ColorStr.COLOR_222222)),
                        source.indexOf("：") + 1,
                        source.length() - 2,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                return spannableString;
            case 3: // 已发货
                source = CalendarUtils.format(CalendarUtils.parse(resp.getDeliveryTime()), Constants.SIGNED_YYYY_MM_DD_HH_MM_SS) + "发货";
                break;
            case 4: // 待结算
                source = CalendarUtils.format(CalendarUtils.parse(resp.getDeliveryTime()), Constants.SIGNED_YYYY_MM_DD_HH_MM_SS) + "送达";
                break;
            case 5:
                source = CalendarUtils.format(CalendarUtils.parse(resp.getSettlementTime()), Constants.SIGNED_YYYY_MM_DD_HH_MM_SS) + "结算";
                break;
            case 6: // 已签收
                source = CalendarUtils.format(CalendarUtils.parse(resp.getSignTime()), Constants.SIGNED_YYYY_MM_DD_HH_MM_SS) + "签收";
                break;
            case 7: // 已取消
                source = getCancelRole(resp.getCanceler()) + "取消";
                break;
        }
        return source;
    }

    public static String getCancelRole(int canceler) {
        switch (canceler) {
            case 0:
                return "系统自动";
            case 1:
                return "采购商";
            case 2:
                return "供应商";
            case 3:
                return "客服";
            default:
                return "";
        }
    }

    public static void showDatePicker(@OptionType String type, OrderParam param, Activity activity, IChangeListener changeListener) {
        long selectBegin, selectEnd;
        selectBegin = selectEnd = System.currentTimeMillis();
        if (type.equals(OptionType.OPTION_FILTER_EXECUTE) && param.getExecuteStart() != 0) {
            selectBegin = param.getExecuteStart();
            selectEnd = param.getExecuteEnd();
        } else if (type.equals(OptionType.OPTION_FILTER_CREATE) && param.getCreateStart() != 0) {
            selectBegin = param.getCreateStart();
            selectEnd = param.getCreateEnd();
        } else if (type.equals(OptionType.OPTION_FILTER_SIGN) && param.getSignStart() != 0) {
            selectBegin = param.getSignStart();
            selectEnd = param.getSignEnd();
        }
        Calendar endTime = Calendar.getInstance();
        if (type.equals(OptionType.OPTION_FILTER_EXECUTE)) {
            int year = endTime.get(Calendar.YEAR);
            endTime.set(Calendar.YEAR, year + 3);
        }
        DatePickerDialog.newBuilder(activity)
                .setBeginTime(CalendarUtils.parse("20170101", Constants.UNSIGNED_YYYY_MM_DD).getTime())
                .setEndTime(endTime.getTimeInMillis())
                .setSelectBeginTime(selectBegin)
                .setSelectEndTime(selectEnd)
                .setTitle(type)
                .setShowHour(!type.equals(OptionType.OPTION_FILTER_CREATE))
                .setCallback(new DatePickerDialog.SelectCallback() {
                    @Override
                    public void select(Date beginTime, Date endTime) {
                        if (CalendarUtils.getDateBefore(endTime, 30).getTime() > beginTime.getTime()) {
                            ToastUtils.showShort(activity, "开始日期至结束日期限制选择31天以内");
                            return;
                        }
                        param.cancelTimeInterval();
                        switch (type) {
                            case OptionType.OPTION_FILTER_CREATE:
                                param.setCreateStart(beginTime.getTime());
                                param.setCreateEnd(endTime.getTime());
                                break;
                            case OptionType.OPTION_FILTER_EXECUTE:
                                param.setExecuteStart(beginTime.getTime());
                                param.setExecuteEnd(endTime.getTime());
                                break;
                            case OptionType.OPTION_FILTER_SIGN:
                                param.setSignStart(beginTime.getTime());
                                param.setSignEnd(endTime.getTime());
                                break;
                        }
                        if (changeListener != null)
                            changeListener.onChanged();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }
}
