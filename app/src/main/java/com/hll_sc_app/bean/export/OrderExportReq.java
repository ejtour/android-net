package com.hll_sc_app.bean.export;

import android.text.TextUtils;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/14
 */

public class OrderExportReq {
    private List<String> subBillIds;
    private String groupID;
    private String email;
    private int isBindEmail;
    /**
     * 1.直接导出2.发送邮件3.查询服务器文件地址
     */
    private String actionType = "2";
    /**
     * 导出投诉列表(complaint_info)
     * 导出售价变更日志（priceFlow）
     * 导出协议价列表（quotation）
     * 代仓商品导出（warehouse_product）
     * 平台分类（category）
     * 库存流水（stock_flow）
     * 规格上下记录导出（skuShelfFlow）
     * 供应商常采清单（supplier_common_export）
     * 合作关系（cooperation）
     * 税率（taxRate）
     * 商品价格（product_price）
     */
    private String typeCode;
    private String userID;

    public OrderExportReq(List<String> subBillIds, String groupID, String email) {
        this.subBillIds = subBillIds;
        this.groupID = groupID;
        this.email = email;
        this.isBindEmail = TextUtils.isEmpty(email) ? 0 : 1;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<String> getSubBillIds() {
        return subBillIds;
    }

    public void setSubBillIds(List<String> subBillIds) {
        this.subBillIds = subBillIds;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIsBindEmail() {
        return isBindEmail;
    }

    public void setIsBindEmail(int isBindEmail) {
        this.isBindEmail = isBindEmail;
    }
}
