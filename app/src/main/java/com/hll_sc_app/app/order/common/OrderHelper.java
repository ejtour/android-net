package com.hll_sc_app.app.order.common;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.ColorStr;
import com.hll_sc_app.utils.Constants;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/6
 */

public class OrderHelper {
    public static CharSequence handleExtraInfo(OrderResp resp) {
        String source = null;
        switch (resp.getSubBillStatus()) {
            case 0: // 待转单
            case 1: // 待接单
            case 2: // 待发货
                String formatTime = CalendarUtils.getDateFormatString(resp.getSubBillExecuteDate(),
                        Constants.FORMAT_YYYY_MM_DD_HH_MM,
                        Constants.FORMAT_YYYY_MM_DD_HH_MM_DASH);
                source = "要求：" + formatTime + "送达";
                SpannableString spannableString = new SpannableString(source);
                spannableString.setSpan(new ForegroundColorSpan(
                                Color.parseColor(ColorStr.COLOR_222222)),
                        source.indexOf("：") + 1,
                        source.length() - 2,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                return spannableString;
            case 3: // 已发货
            case 4: // 待结算
                source = CalendarUtils.format(CalendarUtils.parse(resp.getDeliveryTime()), "yyyy-MM-dd HH:mm:ss") + "发货";
                break;
            case 6: // 已签收
                source = CalendarUtils.format(CalendarUtils.parse(resp.getSignTime()), "yyyy-MM-dd HH:mm:ss") + "签收";
                break;
            case 7: // 已取消
                source = getCancelRole(resp.getCanceler()) + "取消";
                break;
        }
        return source;
    }

    public static String getCancelRole(int canceler) {
        switch (canceler) {
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
}
