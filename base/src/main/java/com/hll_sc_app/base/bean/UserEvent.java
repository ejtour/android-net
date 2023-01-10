package com.hll_sc_app.base.bean;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 1/28/21.
 */
public class UserEvent {
    public static final String ONLY_RECEIVE = "only_receive";
    public static final String ENABLE_PRINT = "enable_print";
    public static final String HIND_ACCOUNT = "hind_account";
    private final String name;

    public UserEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
