package com.hll_sc_app.bean.user;

import android.text.TextUtils;

import com.hll_sc_app.base.GlobalPreference;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/13
 */

public class ThirdAccountBean {
    private String name;
    private boolean bound;
    private int type;
    private int icon;
    private String bindKey;

    public ThirdAccountBean(String name, String bindKey, int type, int icon) {
        this.name = name;
        this.bindKey = bindKey;
        this.bound = !TextUtils.isEmpty(GlobalPreference.getParam(bindKey, ""));
        this.type = type;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public boolean isBound() {
        return bound;
    }

    public void unBind() {
        GlobalPreference.putParam(bindKey, "");
        bound = false;
    }

    public int getType() {
        return type;
    }

    public int getIcon() {
        return icon;
    }
}
