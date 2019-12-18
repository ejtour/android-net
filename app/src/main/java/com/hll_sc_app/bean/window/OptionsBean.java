package com.hll_sc_app.bean.window;

import android.support.annotation.DrawableRes;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/10
 */

public class OptionsBean {
    @DrawableRes
    private int iconRes;
    @OptionType
    private String label;

    public OptionsBean(@DrawableRes int iconRes, @OptionType String label) {
        this.iconRes = iconRes;
        this.label = label;
    }

    public OptionsBean(@OptionType String label) {
        this.iconRes = 0;
        this.label = label;
    }


    public OptionsBean(String label, @DrawableRes int iconRes) {
        this.iconRes = iconRes;
        this.label = label;
    }

    @DrawableRes
    public int getIconRes() {
        return iconRes;
    }

    @OptionType
    public String getLabel() {
        return label;
    }
}
