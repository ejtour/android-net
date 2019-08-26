package com.hll_sc_app.bean.orientation;

import java.util.List;

public class OrientationDetailRes {
    private List<OrientationDetailBean> productList;
    private Integer total;

    public Integer getTotal() {
        return total;
    }

    public List<OrientationDetailBean> getProductList() {
        return productList;
    }

    public void setProductList(List<OrientationDetailBean> productList) {
        this.productList = productList;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
