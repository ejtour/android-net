package com.hll_sc_app.bean.stockmanage;

import java.util.List;

/**
 * 代仓库存校验设置移除请求
 */
public class RemoveStockCheckSettingReq {
    private String actionType;
    private String groupID;
    private List<String> productIDList;

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public List<String> getProductIDList() {
        return productIDList;
    }

    public void setProductIDList(List<String> productIDList) {
        this.productIDList = productIDList;
    }
}
