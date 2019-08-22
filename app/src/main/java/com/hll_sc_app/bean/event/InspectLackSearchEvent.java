package com.hll_sc_app.bean.event;

/**
 * 搜索词传递
 *
 * @author chukun
 * @date 2019-08-05
 */
public class InspectLackSearchEvent {
    private String productName;

    public InspectLackSearchEvent(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
