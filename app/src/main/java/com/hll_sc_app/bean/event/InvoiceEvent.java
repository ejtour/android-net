package com.hll_sc_app.bean.event;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/10
 */

public class InvoiceEvent extends BaseEvent {
    public static final String RELOAD_LIST = "reload_list";
    public static final String REMOVE_ITEM = "remove_item";
    public static final String EXPORT = "export";

    public InvoiceEvent(String msg) {
        this(msg, null);
    }

    public InvoiceEvent(String msg, Object b) {
        super(msg, b);
        switch (msg) {
            case RELOAD_LIST:
            case EXPORT:
            case REMOVE_ITEM:
                break;
            default:
                throw new IllegalArgumentException("Unsupported type");
        }
    }
}
