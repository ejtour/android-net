package com.hll_sc_app.bean.stockmanage;

import com.hll_sc_app.bean.delivery.ProvinceListBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/14
 */

public class DepotRangeReq {
    private String groupID;
    private String houseID;
    private int isWholeCountry;
    private List<ProvinceListBean> warehouseDeliveryRangeList;

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getHouseID() {
        return houseID;
    }

    public void setHouseID(String houseID) {
        this.houseID = houseID;
    }

    public int getIsWholeCountry() {
        return isWholeCountry;
    }

    public void setIsWholeCountry(int isWholeCountry) {
        this.isWholeCountry = isWholeCountry;
    }

    public List<ProvinceListBean> getWarehouseDeliveryRangeList() {
        return warehouseDeliveryRangeList;
    }

    public void setWarehouseDeliveryRangeList(List<ProvinceListBean> warehouseDeliveryRangeList) {
        this.warehouseDeliveryRangeList = warehouseDeliveryRangeList;
    }
}
