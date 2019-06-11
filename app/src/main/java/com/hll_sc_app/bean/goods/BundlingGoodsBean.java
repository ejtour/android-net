package com.hll_sc_app.bean.goods;

/**
 * 组合商品
 *
 * @author zhuyingsong
 * @date 2019-06-11
 */
public class BundlingGoodsBean {
    private String nextDayDelivery;
    private boolean priceIsVisible;
    private String purchaserIsVisible;
    private String bgdSpecID;
    private String specNum;
    private String bundlingGoodsType;
    private String isDecimalBuy;
    private String specPrice;
    private String bgdProductID;
    private String costPriceModifyFlag;
    private String productType;

    public String getNextDayDelivery() {
        return nextDayDelivery;
    }

    public void setNextDayDelivery(String nextDayDelivery) {
        this.nextDayDelivery = nextDayDelivery;
    }

    public boolean isPriceIsVisible() {
        return priceIsVisible;
    }

    public void setPriceIsVisible(boolean priceIsVisible) {
        this.priceIsVisible = priceIsVisible;
    }

    public String getPurchaserIsVisible() {
        return purchaserIsVisible;
    }

    public void setPurchaserIsVisible(String purchaserIsVisible) {
        this.purchaserIsVisible = purchaserIsVisible;
    }

    public String getBgdSpecID() {
        return bgdSpecID;
    }

    public void setBgdSpecID(String bgdSpecID) {
        this.bgdSpecID = bgdSpecID;
    }

    public String getSpecNum() {
        return specNum;
    }

    public void setSpecNum(String specNum) {
        this.specNum = specNum;
    }

    public String getBundlingGoodsType() {
        return bundlingGoodsType;
    }

    public void setBundlingGoodsType(String bundlingGoodsType) {
        this.bundlingGoodsType = bundlingGoodsType;
    }

    public String getIsDecimalBuy() {
        return isDecimalBuy;
    }

    public void setIsDecimalBuy(String isDecimalBuy) {
        this.isDecimalBuy = isDecimalBuy;
    }

    public String getSpecPrice() {
        return specPrice;
    }

    public void setSpecPrice(String specPrice) {
        this.specPrice = specPrice;
    }

    public String getBgdProductID() {
        return bgdProductID;
    }

    public void setBgdProductID(String bgdProductID) {
        this.bgdProductID = bgdProductID;
    }

    public String getCostPriceModifyFlag() {
        return costPriceModifyFlag;
    }

    public void setCostPriceModifyFlag(String costPriceModifyFlag) {
        this.costPriceModifyFlag = costPriceModifyFlag;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
