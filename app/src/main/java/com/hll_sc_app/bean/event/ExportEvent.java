package com.hll_sc_app.bean.event;

import com.hll_sc_app.citymall.util.LogUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/14
 */

public class ExportEvent extends BaseEvent {
    private static final String TAG = "ExportEvent";

    public ExportEvent(String msg) {
        this(msg, "");
    }

    public ExportEvent(String msg, Object b) {
        super(msg, b);
        if (b instanceof String) {
            return;
        }
        LogUtil.e(TAG, "Wrong type");
    }
}
