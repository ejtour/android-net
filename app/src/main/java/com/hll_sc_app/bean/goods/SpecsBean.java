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
        public SpecsBean createFromParcel(Parcel in) {
            return new SpecsBean(in);
        }

        @Override
        public SpecsBean[] newArray(int size) {
            return new SpecsBean[size];
        }
    };
    private String id;
    private String specTemplateID;
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
    private String specContent = "";
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
    /**
     * 所属的商品是否为押金商品
     */
    private boolean isDepositProduct;
    private boolean select;
    private String depositTotalPrice;
    private String usableStock;
    private String buyQty;
    private String shopcartNum;

    protected SpecsBean(Parcel in) {
        id = in.readString();
        specTemplateID = in.readString();
        specID = in.readString();
        productSale = in.readString();
        saleUnitName = in.readString();
        standardUnitStatus = in.readString();
        actionTime = in.readString();
        preferentialPriceType = in.readString();
        productID = in.readString();
        specStatus = in.readString();
        saleUnitID = in.readString();
        isLowStock = in.readByte() != 0;
        assistUnitStatus = in.readString();
        offShelfTime = in.readString();
        displayPrice = in.readString();
        ration = in.readString();
        productSpecID = in.readString();
        action = in.readString();
        isDecimalBuy = in.readString();
        specContent = in.readString();
        actionBy = in.readString();
        standardUnitName = in.readString();
        costPrice = in.readString();
        convertRatio = in.readString();
        premiumType = in.readString();
        nextDayDelivery = in.readString();
        onShelfTime = in.readString();
        buyMinNum = in.readString();
        productPrice = in.readString();
        productStock = in.readString();
        skuCode = in.readString();
        minOrder = in.readString();
        depositProducts = in.createTypedArrayList(DepositProductReq.CREATOR);
        isDepositProduct = in.readByte() != 0;
        select = in.readByte() != 0;
        depositTotalPrice = in.readString();
        usableStock = in.readString();
        shopcartNum = in.readString();
        buyQty = in.readString();
    }

    public SpecsBean() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(specTemplateID);
        dest.writeString(specID);
        dest.writeString(productSale);
        dest.writeString(saleUnitName);
        dest.writeString(standardUnitStatus);
        dest.writeString(actionTime);
        dest.writeString(preferentialPriceType);
        dest.writeString(productID);
        dest.writeString(specStatus);
        dest.writeString(saleUnitID);
        dest.writeByte((byte) (isLowStock ? 1 : 0));
        dest.writeString(assistUnitStatus);
        dest.writeString(offShelfTime);
        dest.writeString(displayPrice);
        dest.writeString(ration);
        dest.writeString(productSpecID);
        dest.writeString(action);
        dest.writeString(isDecimalBuy);
        dest.writeString(specContent);
        dest.writeString(actionBy);
        dest.writeString(standardUnitName);
        dest.writeString(costPrice);
        dest.writeString(convertRatio);
        dest.writeString(premiumType);
        dest.writeString(nextDayDelivery);
        dest.writeString(onShelfTime);
        dest.writeString(buyMinNum);
        dest.writeString(productPrice);
        dest.writeString(productStock);
        dest.writeString(skuCode);
        dest.writeString(minOrder);
        dest.writeTypedList(depositProducts);
        dest.writeByte((byte) (isDepositProduct ? 1 : 0));
        dest.writeByte((byte) (select ? 1 : 0));
        dest.writeString(depositTotalPrice);
        dest.writeString(usableStock);
        dest.writeString(shopcartNum);
        dest.writeString(buyQty);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getDepositTotalPrice() {
        return depositTotalPrice;
    }

    public void setDepositTotalPrice(String depositTotalPrice) {
        this.depositTotalPrice = depositTotalPrice;
    }

    public String getUsableStock() {
        return usableStock;
    }

    public void setUsableStock(String usableStock) {
        this.usableStock = usableStock;
    }

    public String getBuyQty() {
        return buyQty;
    }

    public void setBuyQty(String buyQty) {
        this.buyQty = buyQty;
    }

    public String getShopcartNum() {
        return shopcartNum;
    }

    public void setShopcartNum(String shopcartNum) {
        this.shopcartNum = shopcartNum;
    }

    public String getSpecTemplateID() {
        return specTemplateID;
    }

    public void setSpecTemplateID(String specTemplateID) {
        this.specTemplateID = specTemplateID;
    }

    public boolean isDepositProduct() {
        return isDepositProduct;
    }

    public void setDepositProduct(boolean depositProduct) {
        isDepositProduct = depositProduct;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isLowStock() {
        return isLowStock;
    }

    public void setLowStock(boolean lowStock) {
        isLowStock = lowStock;
    }

}
