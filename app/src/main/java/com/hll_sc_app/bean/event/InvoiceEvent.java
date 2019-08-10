package com.hll_sc_app.bean.event;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/10
 */

public class InvoiceEvent extends BaseEvent {
    public static final String RELOAD_LIST = "reload_list";

    public InvoiceEvent(String msg) {
        this(msg, null);
    }

    public InvoiceEvent(String msg, Object b) {
        super(msg, b);
        switch (msg) {
            case RELOAD_LIST:
                break;
            default:
                throw new IllegalArgumentException("Unsupported type");
        }
    }
}
