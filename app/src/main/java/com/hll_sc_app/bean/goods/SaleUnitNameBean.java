package com.hll_sc_app.bean.goods;

/**
 * 售卖单位
 *
 * @author zhuyingsong
 * @date 2019-06-19
 */
public class SaleUnitNameBean {
    private String nameFirstLetter;
    private String saleUnitID;
    private String saleUnitName;
    private String updateTime;

    public String getNameFirstLetter() {
        return nameFirstLetter;
    }

    public void setNameFirstLetter(String nameFirstLetter) {
        this.nameFirstLetter = nameFirstLetter;
    }

    public String getSaleUnitId() {
        return saleUnitID;
    }

    public void setSaleUnitId(String saleUnitId) {
        this.saleUnitID = saleUnitId;
    }

    public String getSaleUnitName() {
        return saleUnitName;
    }

    public void setSaleUnitName(String saleUnitName) {
        this.saleUnitName = saleUnitName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
