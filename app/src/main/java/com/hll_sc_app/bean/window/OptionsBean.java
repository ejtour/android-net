package com.hll_sc_app.bean.window;

import android.support.annotation.DrawableRes;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/10
 */

public class OptionsBean {
    @DrawableRes
    private int iconRes;
    private String label;
    private Object extra;

    public OptionsBean(@DrawableRes int iconRes,String label) {
        this.iconRes = iconRes;
        this.label = label;
    }

    public OptionsBean(String label) {
        this.iconRes = 0;
        this.label = label;
    }

    @DrawableRes
    public int getIconRes() {
        return iconRes;
    }

    public String getLabel() {
        return label;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }
}
