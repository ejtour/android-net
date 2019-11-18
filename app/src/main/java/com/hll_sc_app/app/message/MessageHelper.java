package com.hll_sc_app.app.message;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/18
 */

public class MessageHelper {
    public static String getMessageType(int code) {
        switch (code) {
            case 1001:
                return "订单消息";
            case 1002:
                return "业务消息";
            case 1003:
                return "通知消息";
            case 1004:
                return "活动消息";
            default:
                return "";
        }
    }
}
