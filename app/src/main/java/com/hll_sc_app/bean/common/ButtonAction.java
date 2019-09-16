package com.hll_sc_app.bean.common;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/10
 */

public class ButtonAction {
    public static final int BUTTON_POSITIVE = 0;
    public static final int BUTTON_NEGATIVE = 1;
    public static final int BUTTON_NEGATIVE_GRAY = 2;
    public static final int TIP_CENTER = 3;
    public int type;
    public String label;
    public int id;

    public ButtonAction(int type, int id, String label) {
        this.type = type;
        this.id = id;
        this.label = label;
    }
}
