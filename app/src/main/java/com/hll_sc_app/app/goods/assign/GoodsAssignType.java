package com.hll_sc_app.app.goods.assign;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/25
 */
public enum GoodsAssignType {
    TARGET_SALE("定向售卖", "定向售卖商品",
            "选择定向商品", "点击下面按钮添加要定向的商品",
            true, 1),
    BLOCK_LIST("品项黑名单", "品项黑名单",
            "选择黑名单商品", "点击下面按钮添加要加入黑名单的商品",
            false, 2);

    GoodsAssignType(String title, String detailLabel, String detailAddLabel, String detailAddTip, boolean canCopy, int type) {
        this.title = title;
        this.detailLabel = detailLabel;
        this.detailAddLabel = detailAddLabel;
        this.detailAddTip = detailAddTip;
        this.canCopy = canCopy;
        this.type = type;
    }

    private final String title;
    private final String detailLabel;
    private final String detailAddLabel;
    private final String detailAddTip;
    private final boolean canCopy;
    private final int type;

    public String getTitle() {
        return title;
    }

    public String getDetailLabel() {
        return detailLabel;
    }

    public String getDetailAddLabel() {
        return detailAddLabel;
    }

    public String getDetailAddTip() {
        return detailAddTip;
    }

    public boolean isCanCopy() {
        return canCopy;
    }

    public int getType() {
        return type;
    }
}
