package com.hll_sc_app.bean.order.place;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/19
 */

public class ProductSpecBean implements Parcelable {
    public static final Creator<ProductSpecBean> CREATOR = new Creator<ProductSpecBean>() {
        @Override
        public ProductSpecBean createFromParcel(Parcel source) {
            return new ProductSpecBean(source);
        }

        @Override
        public ProductSpecBean[] newArray(int size) {
            return new ProductSpecBean[size];
        }
    };
    private String assistUnitID;
    private String assistUnitName;
    /**
     * 1 启用辅助单位
     */
    private int assistUnitStatus;
    private double buyMinNum;
    private double depositTotalPrice;
    /**
     * 1 允许小数后1位购买
     */
    private int isDecimalBuy;
    private boolean isLowStock;
    private double minOrder;
    private int nextDayDelivery;
    /**
     * 1 再自营大厅设置了优惠价
     */
    private int preferentialPriceType;
    /**
     * 1 设置了协议价
     */
    private int premiumType;
    private String productID;
    private double productPrice;
    private String productSpecID;
    private double ration;
    private String saleUnitID;
    private String saleUnitName;
    private String skuCode;
    private String specContent;
    private String specID;
    /**
     * 4 规格已上架 5 规格未上架
     */
    private int specStatus;
    private String standardSpecID;
    private double standardTaxRate;
    private String standardUnitName;
    /**
     * 1 启用标准单位
     */
    private int standardUnitStatus;
    private double usableStock;
    private double shopcartNum;

    public ProductSpecBean() {
    }

    protected ProductSpecBean(Parcel in) {
        this.assistUnitID = in.readString();
        this.assistUnitName = in.readString();
        this.assistUnitStatus = in.readInt();
        this.buyMinNum = in.readDouble();
        this.depositTotalPrice = in.readDouble();
        this.isDecimalBuy = in.readInt();
        this.isLowStock = in.readByte() != 0;
        this.minOrder = in.readDouble();
        this.nextDayDelivery = in.readInt();
        this.preferentialPriceType = in.readInt();
        this.premiumType = in.readInt();
        this.productID = in.readString();
        this.productPrice = in.readDouble();
        this.productSpecID = in.readString();
        this.ration = in.readDouble();
        this.saleUnitID = in.readString();
        this.saleUnitName = in.readString();
        this.skuCode = in.readString();
        this.specContent = in.readString();
        this.specID = in.readString();
        this.specStatus = in.readInt();
        this.standardSpecID = in.readString();
        this.standardTaxRate = in.readDouble();
        this.standardUnitName = in.readString();
        this.standardUnitStatus = in.readInt();
        this.usableStock = in.readDouble();
        this.shopcartNum = in.readDouble();
    }

    public String getAssistUnitID() {
        return assistUnitID;
    }

    public void setAssistUnitID(String assistUnitID) {
        this.assistUnitID = assistUnitID;
    }

    public String getAssistUnitName() {
        return assistUnitName;
    }

    public void setAssistUnitName(String assistUnitName) {
        this.assistUnitName = assistUnitName;
    }

    public int getAssistUnitStatus() {
        return assistUnitStatus;
    }

    public void setAssistUnitStatus(int assistUnitStatus) {
        this.assistUnitStatus = assistUnitStatus;
    }

    public double getBuyMinNum() {
        return buyMinNum;
    }

    public void setBuyMinNum(double buyMinNum) {
        this.buyMinNum = buyMinNum;
    }

    public double getDepositTotalPrice() {
        return depositTotalPrice;
    }

    public void setDepositTotalPrice(double depositTotalPrice) {
        this.depositTotalPrice = depositTotalPrice;
    }

    public int getIsDecimalBuy() {
        return isDecimalBuy;
    }

    public void setIsDecimalBuy(int isDecimalBuy) {
        this.isDecimalBuy = isDecimalBuy;
    }

    public boolean isLowStock() {
        return isLowStock;
    }

    public void setLowStock(boolean lowStock) {
        isLowStock = lowStock;
    }

    public double getMinOrder() {
        return minOrder;
    }

    public void setMinOrder(double minOrder) {
        this.minOrder = minOrder;
    }

    public int getNextDayDelivery() {
        return nextDayDelivery;
    }

    public void setNextDayDelivery(int nextDayDelivery) {
        this.nextDayDelivery = nextDayDelivery;
    }

    public int getPreferentialPriceType() {
        return preferentialPriceType;
    }

    public void setPreferentialPriceType(int preferentialPriceType) {
        this.preferentialPriceType = preferentialPriceType;
    }

    public int getPremiumType() {
        return premiumType;
    }

    public void setPremiumType(int premiumType) {
        this.premiumType = premiumType;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductSpecID() {
        return productSpecID;
    }

    public void setProductSpecID(String productSpecID) {
        this.productSpecID = productSpecID;
    }

    public double getRation() {
        return ration;
    }

    public void setRation(double ration) {
        this.ration = ration;
    }

    public String getSaleUnitID() {
        return saleUnitID;
    }

    public void setSaleUnitID(String saleUnitID) {
        this.saleUnitID = saleUnitID;
    }

    public String getSaleUnitName() {
        return saleUnitName;
    }

    public void setSaleUnitName(String saleUnitName) {
        this.saleUnitName = saleUnitName;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSpecContent() {
        return specContent;
    }

    public void setSpecContent(String specContent) {
        this.specContent = specContent;
    }

    public String getSpecID() {
        return specID;
    }

    public void setSpecID(String specID) {
        this.specID = specID;
    }

    public int getSpecStatus() {
        return specStatus;
    }

    public void setSpecStatus(int specStatus) {
        this.specStatus = specStatus;
    }

    public String getStandardSpecID() {
        return standardSpecID;
    }

    public void setStandardSpecID(String standardSpecID) {
        this.standardSpecID = standardSpecID;
    }

    public double getStandardTaxRate() {
        return standardTaxRate;
    }

    public void setStandardTaxRate(double standardTaxRate) {
        this.standardTaxRate = standardTaxRate;
    }

    public String getStandardUnitName() {
        return standardUnitName;
    }

    public void setStandardUnitName(String standardUnitName) {
        this.standardUnitName = standardUnitName;
    }

    public int getStandardUnitStatus() {
        return standardUnitStatus;
    }

    public void setStandardUnitStatus(int standardUnitStatus) {
        this.standardUnitStatus = standardUnitStatus;
    }

    public double getUsableStock() {
        return usableStock;
    }

    public void setUsableStock(double usableStock) {
        this.usableStock = usableStock;
    }

    public double getShopcartNum() {
        return shopcartNum;
    }

    public void setShopcartNum(double shopcartNum) {
        this.shopcartNum = shopcartNum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.assistUnitID);
        dest.writeString(this.assistUnitName);
        dest.writeInt(this.assistUnitStatus);
        dest.writeDouble(this.buyMinNum);
        dest.writeDouble(this.depositTotalPrice);
        dest.writeInt(this.isDecimalBuy);
        dest.writeByte(this.isLowStock ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.minOrder);
        dest.writeInt(this.nextDayDelivery);
        dest.writeInt(this.preferentialPriceType);
        dest.writeInt(this.premiumType);
        dest.writeString(this.productID);
        dest.writeDouble(this.productPrice);
        dest.writeString(this.productSpecID);
        dest.writeDouble(this.ration);
        dest.writeString(this.saleUnitID);
        dest.writeString(this.saleUnitName);
        dest.writeString(this.skuCode);
        dest.writeString(this.specContent);
        dest.writeString(this.specID);
        dest.writeInt(this.specStatus);
        dest.writeString(this.standardSpecID);
        dest.writeDouble(this.standardTaxRate);
        dest.writeString(this.standardUnitName);
        dest.writeInt(this.standardUnitStatus);
        dest.writeDouble(this.usableStock);
        dest.writeDouble(this.shopcartNum);
    }
}
