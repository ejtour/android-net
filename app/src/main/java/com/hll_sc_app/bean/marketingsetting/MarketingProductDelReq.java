package com.hll_sc_app.bean.marketingsetting;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/9/10
 */
public class MarketingProductDelReq {
    private String discountID;
    private List<String> discountProductIDs;

    public MarketingProductDelReq(String discountID, String productID) {
        this.discountID = discountID;
        this.discountProductIDs = Collections.singletonList(productID);
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    public List<String> getDiscountProductIDs() {
        return discountProductIDs;
    }

    public void setDiscountProductIDs(List<String> discountProductIDs) {
        this.discountProductIDs = discountProductIDs;
    }
}
