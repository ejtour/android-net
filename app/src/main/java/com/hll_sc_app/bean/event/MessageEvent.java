package com.hll_sc_app.bean.event;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/6
 */

public class MessageEvent {
    private String count;

    public MessageEvent(String count) {
        this.count = count;
    }

    public String getCount() {
        return count;
    }
}
