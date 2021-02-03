package com.hll_sc_app.app.order.common;

import android.app.Activity;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.hll_sc_app.base.utils.UserConfig;
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

    /**
     * 获取订单支付类型
     */
    public static String getPayType(int payType) {
        switch (payType) {
            case 1:
                return "货到付款";
            case 2:
                return "账期支付";
            case 3:
                return "在线支付";
            default:
                return "";
        }
    }

    /**
     * 获取支付方式
     */
    public static String getPaymentWay(int way) {
        switch (way) {
            case 1:
                return "微信支付";
            case 2:
                return "支付宝支付";
            case 3:
                return "银联支付";
            case 4:
                return "现金支付";
            case 5:
                return "支票支付";
            case 6:
                return "快捷支付";
            case 7:
                return "余额支付";
            case 8:
                return "微信扫码";
            case 9:
                return "支付宝扫码";
            case 12:
                return "储值卡支付";
            case 13:
                return "善付通支付";
            default:
                return "";
        }
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
                source = "要求 " + formatTime + " 送达";
                SpannableString spannableString = new SpannableString(source);
                spannableString.setSpan(new ForegroundColorSpan(
                                Color.parseColor(ColorStr.COLOR_222222)),
                        3, source.length() - 2,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                return spannableString;
            case 3: // 已发货
                source = CalendarUtils.format(CalendarUtils.parse(resp.getDeliveryTime()), Constants.SIGNED_YYYY_MM_DD_HH_MM_SS) + " 发货";
                break;
            case 4: // 待结算
                source = CalendarUtils.format(CalendarUtils.parse(resp.getDeliveryTime()), Constants.SIGNED_YYYY_MM_DD_HH_MM_SS) + " 送达";
                break;
            case 5:
                source = CalendarUtils.format(CalendarUtils.parse(resp.getSettlementTime()), Constants.SIGNED_YYYY_MM_DD_HH_MM_SS) + " 结算";
                break;
            case 6: // 已签收
                source = CalendarUtils.format(CalendarUtils.parse(resp.getSignTime()), Constants.SIGNED_YYYY_MM_DD_HH_MM_SS) + " 签收";
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
                return "客户";
            case 2:
                return "供应商";
            case 3:
                return "客服";
            default:
                return "";
        }
    }

    public static void showDatePicker(String type, OrderParam param, Activity activity, IChangeListener changeListener) {
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
                        if (CalendarUtils.getDateBefore(endTime, 31).getTime() > beginTime.getTime() && !UserConfig.crm()) {
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
