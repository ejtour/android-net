package com.hll_sc_app.bean.event;

import com.hll_sc_app.citymall.util.LogUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/14
 */

public class OrderExportEvent extends BaseEvent {
    private static final String TAG = "ExportEvent";

    private int exportWay;

    public OrderExportEvent(String msg) {
        this(msg, "");
    }

    public OrderExportEvent(int way, String msg) {
        this(msg, "");
        exportWay = way;
    }

    public OrderExportEvent(String msg, Object b) {
        super(msg, b);
        if (b instanceof String) {
            return;
        }
        LogUtil.e(TAG, "Wrong type");
    }

    public int getExportWay() {
        return exportWay;
    }

    public void setExportWay(int exportWay) {
        this.exportWay = exportWay;
    }
}
