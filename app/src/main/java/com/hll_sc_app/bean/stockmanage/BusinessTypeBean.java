package com.hll_sc_app.bean.stockmanage;

/*交易类型*/
public class BusinessTypeBean {
    private String businessName;
    private int sort;
    private String businessType;

    public BusinessTypeBean(String businessName, String businessType) {
        this.businessName = businessName;
        this.businessType = businessType;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }
}
