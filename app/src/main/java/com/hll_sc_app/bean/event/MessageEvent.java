package com.hll_sc_app.bean.event;

import com.hll_sc_app.bean.message.ApplyMessageResp;
import com.hll_sc_app.citymall.util.LogUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/6
 */

public class MessageEvent extends BaseEvent {
    public static final String TOTAL = "event_total";
    public static final String APPLY = "event_apply";
    public static final String DEMAND = "event_demand";
    private static final String TAG = "MessageEvent";

    public MessageEvent(String msg, Object b) {
        super(msg, b);
        switch (msg) {
            case TOTAL:
                if (b instanceof String) {
                    break;
                }
                LogUtil.e(TAG, "Wrong type");
                break;
            case DEMAND:
                if (b instanceof Boolean) {
                    break;
                }
                LogUtil.e(TAG, "Wrong type");
                break;
            case APPLY:
                if (b instanceof ApplyMessageResp) {
                    break;
                }
                LogUtil.e(TAG, "Wrong type");
                break;
            default:
                break;
        }
    }
}
