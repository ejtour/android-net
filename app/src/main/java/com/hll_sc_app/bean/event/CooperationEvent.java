package com.hll_sc_app.bean.event;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 12/15/20.
 */
public class CooperationEvent extends BaseEvent {
    public static final String SHOP_CHANGED = "shop_changed";
    public static final String SHOP_NUM_CHANGED = "shop_num_changed";

    public CooperationEvent(String msg) {
        super(msg, "");
    }

    public CooperationEvent(String msg, Object b) {
        super(msg, b);
    }
}
