package com.hll_sc_app.bean.export;

import android.support.annotation.DrawableRes;

import com.hll_sc_app.R;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/24
 */

public enum ExportType {
    GOODS_TOTAL(R.drawable.ic_export_goods_total, "导出待发货商品总量", "点击可导出当前待发货订单的商品数量汇总"),
    ASSEMBLY_ORDER(R.drawable.ic_export_assembly_order, "导出配货单", "点击可导出当前待发货订单的配货单");
    private int image;
    private String label;
    private String desc;

    ExportType(@DrawableRes int image, String label, String desc) {
        this.image = image;
        this.label = label;
        this.desc = desc;

    }
    @DrawableRes
    public int getImage() {
        return image;
    }

    public String getLabel() {
        return label;
    }

    public String getDesc() {
        return desc;
    }
}
