package com.hll_sc_app.bean.goods;

import java.util.List;

/**
 * 商品的 Bean
 *
 * @author zhuyingsong
 * @date 2019-06-11
 */
public class GoodsBean {
    /**
     * 是组合商品
     */
    public static final String BUNDLING_GOODS_TYPE = "1";
    /**
     * 是代仓商品
     */
    public static final String WAREHOUSE_TYPE = "1";
    private String productID;
    private String placeProvince;
    private String productName;
    private String productAttr;
    private String appointSellType;
    private String bundlingGoodsType;
    private List<BundlingGoodsBean> bundlingGoodsDetails;
    private String productBrief;
    private String isWareHourse;
    private String shopProductCategorySubID;
    private String action;
    private String depositProductType;
    private String purchaserIsVisible;
    private String orgName;
    private String cargoOwnerID;
    private String cargoOwnerName;
    private String standardUnitName;
    private String groupID;
    private boolean isCooperation;
    private boolean isDeliveryRange;
    private String nextDayDelivery;
    private String stockCheckType;
    private String brandId;
    private String producer;
    private String productSale;
    private String note;
    private boolean priceIsVisible;
    private String actionTime;
    private String saleSpecNum;
    private boolean isCollection;
    private String categoryThreeID;
    private String top;
    private String shopProductCategoryID;
    private String standardSpecID;
    private String placeCity;
    private String shopProductCategoryThreeID;
    private String categoryID;
    private String productType;
    private String supplierName;
    private String actionBy;
    private String productStatus;
    private String isSupplierWarehouse;
    private String imgUrl;
    private String productCode;
    private String categorySubID;
    private String placeCityCode;
    private String createTime;
    private String guaranteePeriod;
    private String resourceType;
    private String placeProvinceCode;
    private List<SpecsBean> specs;
    private List<?> productAttrs;
    private List<SupplierShopsBean> supplierShops;
    private List<NicknamesBean> nicknames;
    private List<?> labelList;

    public List<BundlingGoodsBean> getBundlingGoodsDetails() {
        return bundlingGoodsDetails;
    }

    public void setBundlingGoodsDetails(List<BundlingGoodsBean> bundlingGoodsDetails) {
        this.bundlingGoodsDetails = bundlingGoodsDetails;
    }

    public String getCargoOwnerName() {
        return cargoOwnerName;
    }

    public void setCargoOwnerName(String cargoOwnerName) {
        this.cargoOwnerName = cargoOwnerName;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getPlaceProvince() {
        return placeProvince;
    }

    public void setPlaceProvince(String placeProvince) {
        this.placeProvince = placeProvince;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductAttr() {
        return productAttr;
    }

    public void setProductAttr(String productAttr) {
        this.productAttr = productAttr;
    }

    public String getAppointSellType() {
        return appointSellType;
    }

    public void setAppointSellType(String appointSellType) {
        this.appointSellType = appointSellType;
    }

    public String getBundlingGoodsType() {
        return bundlingGoodsType;
    }

    public void setBundlingGoodsType(String bundlingGoodsType) {
        this.bundlingGoodsType = bundlingGoodsType;
    }

    public String getProductBrief() {
        return productBrief;
    }

    public void setProductBrief(String productBrief) {
        this.productBrief = productBrief;
    }

    public String getIsWareHourse() {
        return isWareHourse;
    }

    public void setIsWareHourse(String isWareHourse) {
        this.isWareHourse = isWareHourse;
    }

    public String getShopProductCategorySubID() {
        return shopProductCategorySubID;
    }

    public void setShopProductCategorySubID(String shopProductCategorySubID) {
        this.shopProductCategorySubID = shopProductCategorySubID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDepositProductType() {
        return depositProductType;
    }

    public void setDepositProductType(String depositProductType) {
        this.depositProductType = depositProductType;
    }

    public String getPurchaserIsVisible() {
        return purchaserIsVisible;
    }

    public void setPurchaserIsVisible(String purchaserIsVisible) {
        this.purchaserIsVisible = purchaserIsVisible;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getCargoOwnerID() {
        return cargoOwnerID;
    }

    public void setCargoOwnerID(String cargoOwnerID) {
        this.cargoOwnerID = cargoOwnerID;
    }

    public String getStandardUnitName() {
        return standardUnitName;
    }

    public void setStandardUnitName(String standardUnitName) {
        this.standardUnitName = standardUnitName;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public boolean isIsCooperation() {
        return isCooperation;
    }

    public void setIsCooperation(boolean isCooperation) {
        this.isCooperation = isCooperation;
    }

    public boolean isIsDeliveryRange() {
        return isDeliveryRange;
    }

    public void setIsDeliveryRange(boolean isDeliveryRange) {
        this.isDeliveryRange = isDeliveryRange;
    }

    public String getNextDayDelivery() {
        return nextDayDelivery;
    }

    public void setNextDayDelivery(String nextDayDelivery) {
        this.nextDayDelivery = nextDayDelivery;
    }

    public String getStockCheckType() {
        return stockCheckType;
    }

    public void setStockCheckType(String stockCheckType) {
        this.stockCheckType = stockCheckType;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getProductSale() {
        return productSale;
    }

    public void setProductSale(String productSale) {
        this.productSale = productSale;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isPriceIsVisible() {
        return priceIsVisible;
    }

    public void setPriceIsVisible(boolean priceIsVisible) {
        this.priceIsVisible = priceIsVisible;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getSaleSpecNum() {
        return saleSpecNum;
    }

    public void setSaleSpecNum(String saleSpecNum) {
        this.saleSpecNum = saleSpecNum;
    }

    public boolean isIsCollection() {
        return isCollection;
    }

    public void setIsCollection(boolean isCollection) {
        this.isCollection = isCollection;
    }

    public String getCategoryThreeID() {
        return categoryThreeID;
    }

    public void setCategoryThreeID(String categoryThreeID) {
        this.categoryThreeID = categoryThreeID;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getShopProductCategoryID() {
        return shopProductCategoryID;
    }

    public void setShopProductCategoryID(String shopProductCategoryID) {
        this.shopProductCategoryID = shopProductCategoryID;
    }

    public String getStandardSpecID() {
        return standardSpecID;
    }

    public void setStandardSpecID(String standardSpecID) {
        this.standardSpecID = standardSpecID;
    }

    public String getPlaceCity() {
        return placeCity;
    }

    public void setPlaceCity(String placeCity) {
        this.placeCity = placeCity;
    }

    public String getShopProductCategoryThreeID() {
        return shopProductCategoryThreeID;
    }

    public void setShopProductCategoryThreeID(String shopProductCategoryThreeID) {
        this.shopProductCategoryThreeID = shopProductCategoryThreeID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getIsSupplierWarehouse() {
        return isSupplierWarehouse;
    }

    public void setIsSupplierWarehouse(String isSupplierWarehouse) {
        this.isSupplierWarehouse = isSupplierWarehouse;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCategorySubID() {
        return categorySubID;
    }

    public void setCategorySubID(String categorySubID) {
        this.categorySubID = categorySubID;
    }

    public String getPlaceCityCode() {
        return placeCityCode;
    }

    public void setPlaceCityCode(String placeCityCode) {
        this.placeCityCode = placeCityCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGuaranteePeriod() {
        return guaranteePeriod;
    }

    public void setGuaranteePeriod(String guaranteePeriod) {
        this.guaranteePeriod = guaranteePeriod;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getPlaceProvinceCode() {
        return placeProvinceCode;
    }

    public void setPlaceProvinceCode(String placeProvinceCode) {
        this.placeProvinceCode = placeProvinceCode;
    }

    public List<SpecsBean> getSpecs() {
        return specs;
    }

    public void setSpecs(List<SpecsBean> specs) {
        this.specs = specs;
    }

    public List<?> getProductAttrs() {
        return productAttrs;
    }

    public void setProductAttrs(List<?> productAttrs) {
        this.productAttrs = productAttrs;
    }

    public List<SupplierShopsBean> getSupplierShops() {
        return supplierShops;
    }

    public void setSupplierShops(List<SupplierShopsBean> supplierShops) {
        this.supplierShops = supplierShops;
    }

    public List<NicknamesBean> getNicknames() {
        return nicknames;
    }

    public void setNicknames(List<NicknamesBean> nicknames) {
        this.nicknames = nicknames;
    }

    public List<?> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<?> labelList) {
        this.labelList = labelList;
    }
}
