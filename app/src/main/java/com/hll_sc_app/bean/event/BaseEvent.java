package com.hll_sc_app.bean.event;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/5
 */

public abstract class BaseEvent {
    protected String message;
    protected Object data;

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    private BaseEvent(){}

    protected BaseEvent(String msg, Object b) {
        this.message = msg;
        this.data = b;
    }

    protected void setMessage(String msg) {
        this.message = msg;
    }
}
