package com.hll_sc_app.bean.goods;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 押金商品
 *
 * @author zhuyingsong
 * @date 2019-06-20
 */
public class DepositProductBean implements Parcelable {
    private String specID;
    private String productID;
    private String placeProvince;
    private String specStatus;
    private String productName;
    private String productAttr;
    private String assistUnitStatus;
    private String offShelfTime;
    private String appointSellType;
    private String productNameSuggest;
    private String bundlingGoodsType;
    private String productBrief;
    private String isWareHourse;
    private String shopProductCategorySubID;
    private String action;
    private String isDecimalBuy;
    private String depositProductType;
    private String purchaserIsVisible;
    private String orgName;
    private String cargoOwnerID;
    private String standardUnitName;
    private String groupID;
    private String nextDayDelivery;
    private String onShelfTime;
    private String stockCheckType;
    private String buyMinNum;
    private String brandId;
    private String producer;
    private String productPrice;
    private String skuCode;
    private String saleUnitName;
    private boolean priceIsVisible;
    private String standardUnitStatus;
    private String actionTime;
    private String saleUnitID;
    private String categoryThreeID;
    private String shopProductCategoryID;
    private String ration;
    private String placeCity;
    private String cargoOwnerName;
    private String shopProductCategoryThreeID;
    private String categoryID;
    private String productType;
    private String specContent;
    private String supplierName;
    private String actionBy;
    private String costPrice;
    private String productStatus;
    private String convertRatio;
    private String costPriceModifyFlag;
    private String isSupplierWarehouse;
    private String imgUrl;
    private String productNameKeyword;
    private String productCode;
    private String depositNum;
    private String categorySubID;
    private String placeCityCode;
    private String createTime;
    private String guaranteePeriod;
    private String placeProvinceCode;
    private String resourceType;
    private List<SupplierShopsBean> supplierShops;
    private List<NicknamesBean> nicknames;
    private boolean selected;

    public String getDepositNum() {
        return depositNum;
    }

    public void setDepositNum(String depositNum) {
        this.depositNum = depositNum;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getSpecID() {
        return specID;
    }

    public void setSpecID(String specID) {
        this.specID = specID;
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

    public String getSpecStatus() {
        return specStatus;
    }

    public void setSpecStatus(String specStatus) {
        this.specStatus = specStatus;
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

    public String getAssistUnitStatus() {
        return assistUnitStatus;
    }

    public void setAssistUnitStatus(String assistUnitStatus) {
        this.assistUnitStatus = assistUnitStatus;
    }

    public String getOffShelfTime() {
        return offShelfTime;
    }

    public void setOffShelfTime(String offShelfTime) {
        this.offShelfTime = offShelfTime;
    }

    public String getAppointSellType() {
        return appointSellType;
    }

    public void setAppointSellType(String appointSellType) {
        this.appointSellType = appointSellType;
    }

    public String getProductNameSuggest() {
        return productNameSuggest;
    }

    public void setProductNameSuggest(String productNameSuggest) {
        this.productNameSuggest = productNameSuggest;
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

    public String getIsDecimalBuy() {
        return isDecimalBuy;
    }

    public void setIsDecimalBuy(String isDecimalBuy) {
        this.isDecimalBuy = isDecimalBuy;
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

    public String getNextDayDelivery() {
        return nextDayDelivery;
    }

    public void setNextDayDelivery(String nextDayDelivery) {
        this.nextDayDelivery = nextDayDelivery;
    }

    public String getOnShelfTime() {
        return onShelfTime;
    }

    public void setOnShelfTime(String onShelfTime) {
        this.onShelfTime = onShelfTime;
    }

    public String getStockCheckType() {
        return stockCheckType;
    }

    public void setStockCheckType(String stockCheckType) {
        this.stockCheckType = stockCheckType;
    }

    public String getBuyMinNum() {
        return buyMinNum;
    }

    public void setBuyMinNum(String buyMinNum) {
        this.buyMinNum = buyMinNum;
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

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSaleUnitName() {
        return saleUnitName;
    }

    public void setSaleUnitName(String saleUnitName) {
        this.saleUnitName = saleUnitName;
    }

    public boolean isPriceIsVisible() {
        return priceIsVisible;
    }

    public void setPriceIsVisible(boolean priceIsVisible) {
        this.priceIsVisible = priceIsVisible;
    }

    public String getStandardUnitStatus() {
        return standardUnitStatus;
    }

    public void setStandardUnitStatus(String standardUnitStatus) {
        this.standardUnitStatus = standardUnitStatus;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getSaleUnitID() {
        return saleUnitID;
    }

    public void setSaleUnitID(String saleUnitID) {
        this.saleUnitID = saleUnitID;
    }

    public String getCategoryThreeID() {
        return categoryThreeID;
    }

    public void setCategoryThreeID(String categoryThreeID) {
        this.categoryThreeID = categoryThreeID;
    }

    public String getShopProductCategoryID() {
        return shopProductCategoryID;
    }

    public void setShopProductCategoryID(String shopProductCategoryID) {
        this.shopProductCategoryID = shopProductCategoryID;
    }

    public String getRation() {
        return ration;
    }

    public void setRation(String ration) {
        this.ration = ration;
    }

    public String getPlaceCity() {
        return placeCity;
    }

    public void setPlaceCity(String placeCity) {
        this.placeCity = placeCity;
    }

    public String getCargoOwnerName() {
        return cargoOwnerName;
    }

    public void setCargoOwnerName(String cargoOwnerName) {
        this.cargoOwnerName = cargoOwnerName;
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

    public String getSpecContent() {
        return specContent;
    }

    public void setSpecContent(String specContent) {
        this.specContent = specContent;
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

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getConvertRatio() {
        return convertRatio;
    }

    public void setConvertRatio(String convertRatio) {
        this.convertRatio = convertRatio;
    }

    public String getCostPriceModifyFlag() {
        return costPriceModifyFlag;
    }

    public void setCostPriceModifyFlag(String costPriceModifyFlag) {
        this.costPriceModifyFlag = costPriceModifyFlag;
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

    public String getProductNameKeyword() {
        return productNameKeyword;
    }

    public void setProductNameKeyword(String productNameKeyword) {
        this.productNameKeyword = productNameKeyword;
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

    public String getPlaceProvinceCode() {
        return placeProvinceCode;
    }

    public void setPlaceProvinceCode(String placeProvinceCode) {
        this.placeProvinceCode = placeProvinceCode;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
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

    public static final Parcelable.Creator<DepositProductBean> CREATOR = new Parcelable.Creator<DepositProductBean>() {
        @Override
        public DepositProductBean createFromParcel(Parcel source) {
            return new DepositProductBean(source);
        }

        @Override
        public DepositProductBean[] newArray(int size) {
            return new DepositProductBean[size];
        }
    };

    public DepositProductBean() {
    }

    protected DepositProductBean(Parcel in) {
        this.specID = in.readString();
        this.productID = in.readString();
        this.placeProvince = in.readString();
        this.specStatus = in.readString();
        this.productName = in.readString();
        this.productAttr = in.readString();
        this.assistUnitStatus = in.readString();
        this.offShelfTime = in.readString();
        this.appointSellType = in.readString();
        this.productNameSuggest = in.readString();
        this.bundlingGoodsType = in.readString();
        this.productBrief = in.readString();
        this.isWareHourse = in.readString();
        this.shopProductCategorySubID = in.readString();
        this.action = in.readString();
        this.isDecimalBuy = in.readString();
        this.depositProductType = in.readString();
        this.purchaserIsVisible = in.readString();
        this.orgName = in.readString();
        this.cargoOwnerID = in.readString();
        this.standardUnitName = in.readString();
        this.groupID = in.readString();
        this.nextDayDelivery = in.readString();
        this.onShelfTime = in.readString();
        this.stockCheckType = in.readString();
        this.buyMinNum = in.readString();
        this.brandId = in.readString();
        this.producer = in.readString();
        this.productPrice = in.readString();
        this.skuCode = in.readString();
        this.saleUnitName = in.readString();
        this.priceIsVisible = in.readByte() != 0;
        this.standardUnitStatus = in.readString();
        this.actionTime = in.readString();
        this.saleUnitID = in.readString();
        this.categoryThreeID = in.readString();
        this.shopProductCategoryID = in.readString();
        this.ration = in.readString();
        this.placeCity = in.readString();
        this.cargoOwnerName = in.readString();
        this.shopProductCategoryThreeID = in.readString();
        this.categoryID = in.readString();
        this.productType = in.readString();
        this.specContent = in.readString();
        this.supplierName = in.readString();
        this.actionBy = in.readString();
        this.costPrice = in.readString();
        this.productStatus = in.readString();
        this.convertRatio = in.readString();
        this.costPriceModifyFlag = in.readString();
        this.isSupplierWarehouse = in.readString();
        this.imgUrl = in.readString();
        this.productNameKeyword = in.readString();
        this.productCode = in.readString();
        this.categorySubID = in.readString();
        this.placeCityCode = in.readString();
        this.createTime = in.readString();
        this.guaranteePeriod = in.readString();
        this.placeProvinceCode = in.readString();
        this.resourceType = in.readString();
        this.supplierShops = new ArrayList<SupplierShopsBean>();
        in.readList(this.supplierShops, SupplierShopsBean.class.getClassLoader());
        this.nicknames = new ArrayList<NicknamesBean>();
        in.readList(this.nicknames, NicknamesBean.class.getClassLoader());
        this.selected = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.specID);
        dest.writeString(this.productID);
        dest.writeString(this.placeProvince);
        dest.writeString(this.specStatus);
        dest.writeString(this.productName);
        dest.writeString(this.productAttr);
        dest.writeString(this.assistUnitStatus);
        dest.writeString(this.offShelfTime);
        dest.writeString(this.appointSellType);
        dest.writeString(this.productNameSuggest);
        dest.writeString(this.bundlingGoodsType);
        dest.writeString(this.productBrief);
        dest.writeString(this.isWareHourse);
        dest.writeString(this.shopProductCategorySubID);
        dest.writeString(this.action);
        dest.writeString(this.isDecimalBuy);
        dest.writeString(this.depositProductType);
        dest.writeString(this.purchaserIsVisible);
        dest.writeString(this.orgName);
        dest.writeString(this.cargoOwnerID);
        dest.writeString(this.standardUnitName);
        dest.writeString(this.groupID);
        dest.writeString(this.nextDayDelivery);
        dest.writeString(this.onShelfTime);
        dest.writeString(this.stockCheckType);
        dest.writeString(this.buyMinNum);
        dest.writeString(this.brandId);
        dest.writeString(this.producer);
        dest.writeString(this.productPrice);
        dest.writeString(this.skuCode);
        dest.writeString(this.saleUnitName);
        dest.writeByte(this.priceIsVisible ? (byte) 1 : (byte) 0);
        dest.writeString(this.standardUnitStatus);
        dest.writeString(this.actionTime);
        dest.writeString(this.saleUnitID);
        dest.writeString(this.categoryThreeID);
        dest.writeString(this.shopProductCategoryID);
        dest.writeString(this.ration);
        dest.writeString(this.placeCity);
        dest.writeString(this.cargoOwnerName);
        dest.writeString(this.shopProductCategoryThreeID);
        dest.writeString(this.categoryID);
        dest.writeString(this.productType);
        dest.writeString(this.specContent);
        dest.writeString(this.supplierName);
        dest.writeString(this.actionBy);
        dest.writeString(this.costPrice);
        dest.writeString(this.productStatus);
        dest.writeString(this.convertRatio);
        dest.writeString(this.costPriceModifyFlag);
        dest.writeString(this.isSupplierWarehouse);
        dest.writeString(this.imgUrl);
        dest.writeString(this.productNameKeyword);
        dest.writeString(this.productCode);
        dest.writeString(this.categorySubID);
        dest.writeString(this.placeCityCode);
        dest.writeString(this.createTime);
        dest.writeString(this.guaranteePeriod);
        dest.writeString(this.placeProvinceCode);
        dest.writeString(this.resourceType);
        dest.writeList(this.supplierShops);
        dest.writeList(this.nicknames);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
    }
}
