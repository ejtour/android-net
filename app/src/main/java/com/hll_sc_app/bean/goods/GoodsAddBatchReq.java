package com.hll_sc_app.bean.goods;

import java.util.List;

/**
 * 商品库批量新增商品
 *
 * @author zhuyingsong
 * @date 2019-06-29
 */
public class GoodsAddBatchReq {
    /**
     * 按钮类型（1-仅保存，2-申请上架）
     */
    private String buttonType;
    /**
     * 集团编号
     */
    private String groupID;
    /**
     * 数据来源类型（商城-shopmall，供应链-supplyChain）
     */
    private String resourceType;
    /**
     * 商品模板列表
     */
    private List<GoodsBean> productTemplates;

    public String getButtonType() {
        return buttonType;
    }

    public void setButtonType(String buttonType) {
        this.buttonType = buttonType;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public List<GoodsBean> getProductTemplates() {
        return productTemplates;
    }

    public void setProductTemplates(List<GoodsBean> productTemplates) {
        this.productTemplates = productTemplates;
    }
}
