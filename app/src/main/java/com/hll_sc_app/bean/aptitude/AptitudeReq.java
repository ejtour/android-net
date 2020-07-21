package com.hll_sc_app.bean.aptitude;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/10
 */

public class AptitudeReq {
    private String groupID;
    private String productID;
    private List<AptitudeBean> aptitudeList;

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public List<AptitudeBean> getAptitudeList() {
        return aptitudeList;
    }

    public void setAptitudeList(List<AptitudeBean> aptitudeList) {
        this.aptitudeList = aptitudeList;
    }
}
