package com.hll_sc_app.bean.stockmanage;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/15
 */

public class DepotCategoryReq {
    private String groupID;
    private String houseID;
    private int isWholeCountry;
    private List<CategorySubBean> warehouseStoreCategoryList;

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

    public List<CategorySubBean> getWarehouseStoreCategoryList() {
        return warehouseStoreCategoryList;
    }

    public void setWarehouseStoreCategoryList(List<CategorySubBean> warehouseStoreCategoryList) {
        this.warehouseStoreCategoryList = warehouseStoreCategoryList;
    }
}
