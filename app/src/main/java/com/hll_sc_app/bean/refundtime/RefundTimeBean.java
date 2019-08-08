package com.hll_sc_app.bean.refundtime;

public class RefundTimeBean {
    private Long refundID;
    private Integer categoryID;
    private String categoryName;
    private Integer num;
    private Integer customerLevel;
    private Long groupID;

    public Integer getCategoryID() {
        return categoryID;
    }

    public Integer getCustomerLevel() {
        return customerLevel;
    }

    public Integer getNum() {
        return num;
    }

    public Long getGroupID() {
        return groupID;
    }

    public Long getRefundID() {
        return refundID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCustomerLevel(Integer customerLevel) {
        this.customerLevel = customerLevel;
    }

    public void setGroupID(Long groupID) {
        this.groupID = groupID;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setRefundID(Long refundID) {
        this.refundID = refundID;
    }
}
