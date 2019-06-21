package com.hll_sc_app.bean.goods;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 规格的 Bean
 *
 * @author zhuyingsong
 * @date 2019-06-11
 */
public class SpecsBean implements Parcelable {
    public static final String SPEC_STATUS_UP = "4";
    public static final String SPEC_STATUS_DOWN = "5";
    public static final String STANDARD_UNIT = "1";
    public static final Creator<SpecsBean> CREATOR = new Creator<SpecsBean>() {
        @Override
        public SpecsBean createFromParcel(Parcel source) {
            return new SpecsBean(source);
        }

        @Override
        public SpecsBean[] newArray(int size) {
            return new SpecsBean[size];
        }
    };
    private String specID;
    private String productSale;
    private String saleUnitName;
    private String standardUnitStatus;
    private String actionTime;
    private String preferentialPriceType;
    private String productID;
    /**
     * 规格状态：4-已上架，5-未上架
     */
    private String specStatus;
    private String saleUnitID;
    private boolean isLowStock;
    private String assistUnitStatus;
    private String offShelfTime;
    private String displayPrice;
    private String ration;
    private String productSpecID;
    private String action;
    private String isDecimalBuy;
    /**
     * 规格内容
     */
    private String specContent;
    private String actionBy;
    private String standardUnitName;
    private String costPrice;
    private String convertRatio;
    private String premiumType;
    private String nextDayDelivery;
    private String onShelfTime;
    private String buyMinNum;
    private String productPrice;
    private String productStock;
    private String skuCode;
    private String minOrder;
    private List<DepositProductReq> depositProducts;
    private boolean edit;
    /**
     * 所属的商品是否为押金商品
     */
    private boolean isDepositProduct;
    private boolean select;

    public SpecsBean() {
    }

    protected SpecsBean(Parcel in) {
        this.specID = in.readString();
        this.productSale = in.readString();
        this.saleUnitName = in.readString();
        this.standardUnitStatus = in.readString();
        this.actionTime = in.readString();
        this.preferentialPriceType = in.readString();
        this.productID = in.readString();
        this.specStatus = in.readString();
        this.saleUnitID = in.readString();
        this.isLowStock = in.readByte() != 0;
        this.assistUnitStatus = in.readString();
        this.offShelfTime = in.readString();
        this.displayPrice = in.readString();
        this.ration = in.readString();
        this.productSpecID = in.readString();
        this.action = in.readString();
        this.isDecimalBuy = in.readString();
        this.specContent = in.readString();
        this.actionBy = in.readString();
        this.standardUnitName = in.readString();
        this.costPrice = in.readString();
        this.convertRatio = in.readString();
        this.premiumType = in.readString();
        this.nextDayDelivery = in.readString();
        this.onShelfTime = in.readString();
        this.buyMinNum = in.readString();
        this.productPrice = in.readString();
        this.productStock = in.readString();
        this.skuCode = in.readString();
        this.minOrder = in.readString();
        this.depositProducts = in.createTypedArrayList(DepositProductReq.CREATOR);
        this.edit = in.readByte() != 0;
        this.isDepositProduct = in.readByte() != 0;
        this.select = in.readByte() != 0;
    }

    public boolean isDepositProduct() {
        return isDepositProduct;
    }

    public void setDepositProduct(boolean depositProduct) {
        isDepositProduct = depositProduct;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public String getMinOrder() {
        return minOrder;
    }

    public void setMinOrder(String minOrder) {
        this.minOrder = minOrder;
    }

    public String getProductStock() {
        return productStock;
    }

    public void setProductStock(String productStock) {
        this.productStock = productStock;
    }

    public String getSpecID() {
        return specID;
    }

    public void setSpecID(String specID) {
        this.specID = specID;
    }

    public String getProductSale() {
        return productSale;
    }

    public void setProductSale(String productSale) {
        this.productSale = productSale;
    }

    public String getSaleUnitName() {
        return saleUnitName;
    }

    public void setSaleUnitName(String saleUnitName) {
        this.saleUnitName = saleUnitName;
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

    public String getPreferentialPriceType() {
        return preferentialPriceType;
    }

    public void setPreferentialPriceType(String preferentialPriceType) {
        this.preferentialPriceType = preferentialPriceType;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getSpecStatus() {
        return specStatus;
    }

    public void setSpecStatus(String specStatus) {
        this.specStatus = specStatus;
    }

    public String getSaleUnitID() {
        return saleUnitID;
    }

    public void setSaleUnitID(String saleUnitID) {
        this.saleUnitID = saleUnitID;
    }

    public boolean isIsLowStock() {
        return isLowStock;
    }

    public void setIsLowStock(boolean isLowStock) {
        this.isLowStock = isLowStock;
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

    public String getDisplayPrice() {
        return displayPrice;
    }

    public void setDisplayPrice(String displayPrice) {
        this.displayPrice = displayPrice;
    }

    public String getRation() {
        return ration;
    }

    public void setRation(String ration) {
        this.ration = ration;
    }

    public String getProductSpecID() {
        return productSpecID;
    }

    public void setProductSpecID(String productSpecID) {
        this.productSpecID = productSpecID;
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

    public String getSpecContent() {
        return specContent;
    }

    public void setSpecContent(String specContent) {
        this.specContent = specContent;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getStandardUnitName() {
        return standardUnitName;
    }

    public void setStandardUnitName(String standardUnitName) {
        this.standardUnitName = standardUnitName;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getConvertRatio() {
        return convertRatio;
    }

    public void setConvertRatio(String convertRatio) {
        this.convertRatio = convertRatio;
    }

    public String getPremiumType() {
        return premiumType;
    }

    public void setPremiumType(String premiumType) {
        this.premiumType = premiumType;
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

    public String getBuyMinNum() {
        return buyMinNum;
    }

    public void setBuyMinNum(String buyMinNum) {
        this.buyMinNum = buyMinNum;
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

    public List<DepositProductReq> getDepositProducts() {
        return depositProducts;
    }

    public void setDepositProducts(List<DepositProductReq> depositProducts) {
        this.depositProducts = depositProducts;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.specID);
        dest.writeString(this.productSale);
        dest.writeString(this.saleUnitName);
        dest.writeString(this.standardUnitStatus);
        dest.writeString(this.actionTime);
        dest.writeString(this.preferentialPriceType);
        dest.writeString(this.productID);
        dest.writeString(this.specStatus);
        dest.writeString(this.saleUnitID);
        dest.writeByte(this.isLowStock ? (byte) 1 : (byte) 0);
        dest.writeString(this.assistUnitStatus);
        dest.writeString(this.offShelfTime);
        dest.writeString(this.displayPrice);
        dest.writeString(this.ration);
        dest.writeString(this.productSpecID);
        dest.writeString(this.action);
        dest.writeString(this.isDecimalBuy);
        dest.writeString(this.specContent);
        dest.writeString(this.actionBy);
        dest.writeString(this.standardUnitName);
        dest.writeString(this.costPrice);
        dest.writeString(this.convertRatio);
        dest.writeString(this.premiumType);
        dest.writeString(this.nextDayDelivery);
        dest.writeString(this.onShelfTime);
        dest.writeString(this.buyMinNum);
        dest.writeString(this.productPrice);
        dest.writeString(this.productStock);
        dest.writeString(this.skuCode);
        dest.writeString(this.minOrder);
        dest.writeTypedList(this.depositProducts);
        dest.writeByte(this.edit ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isDepositProduct ? (byte) 1 : (byte) 0);
        dest.writeByte(this.select ? (byte) 1 : (byte) 0);
    }
}
