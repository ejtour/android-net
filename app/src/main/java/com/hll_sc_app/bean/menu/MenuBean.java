package com.hll_sc_app.bean.menu;

import android.text.TextUtils;

import com.hll_sc_app.base.utils.router.RouterUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/12
 */

public class MenuBean {
    private int icon;
    private String label;
    private String path;
    private String extra;
    private boolean section;

    public MenuBean(String label) {
        this(0, label, null, false);
    }

    public MenuBean(String label, boolean section) {
        this(0, label, null, section);
    }

    public MenuBean(String label, String path) {
        this(0, label, path, false);
    }

    public MenuBean(String label, String path, boolean section) {
        this(0, label, path, section);
    }

    public MenuBean(int icon, String label, String path) {
        this(icon, label, path, false);
    }

    public MenuBean(int icon, String label, String path, boolean section) {
        this.icon = icon;
        this.label = label;
        this.path = path;
        this.section = section;
    }

    public boolean doFinally() {
        if (!TextUtils.isEmpty(path)) {
            RouterUtil.goToActivity(path);
            return true;
        }
        return false;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isSection() {
        return section;
    }

    public void setSection(boolean section) {
        this.section = section;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
