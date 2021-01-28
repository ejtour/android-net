package com.hll_sc_app.base.bean;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 1/28/21.
 */
public class UserEvent {
    public static final String ONLY_RECEIVE = "only_receive";
    private final String name;

    public UserEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
