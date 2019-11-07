package com.hll_sc_app.bean.report;

import androidx.annotation.DrawableRes;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/22
 */

public class ReportItem {
    private int icon;
    private String label;
    private boolean bottomDivider;
    private String path;

    public ReportItem(@DrawableRes int icon, String label, String path) {
        this(icon, label, path, false);
    }

    /**
     * @param icon          左侧图标
     * @param label         菜单文本
     * @param path          跳转路径
     * @param bottomDivider 是否显示底部分割线
     */
    public ReportItem(@DrawableRes int icon, String label, String path, boolean bottomDivider) {
        this.icon = icon;
        this.label = label;
        this.path = path;
        this.bottomDivider = bottomDivider;
    }

    public int getIcon() {
        return icon;
    }

    public String getLabel() {
        return label;
    }

    public boolean isBottomDivider() {
        return bottomDivider;
    }

    public String getPath() {
        return path;
    }
}
