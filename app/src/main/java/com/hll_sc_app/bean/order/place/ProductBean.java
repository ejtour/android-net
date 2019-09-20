package com.hll_sc_app.bean.order.place;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/19
 */

public class ProductBean implements Parcelable {
    public static final Parcelable.Creator<ProductBean> CREATOR = new Parcelable.Creator<ProductBean>() {
        @Override
        public ProductBean createFromParcel(Parcel source) {
            return new ProductBean(source);
        }

        @Override
        public ProductBean[] newArray(int size) {
            return new ProductBean[size];
        }
    };
    /**
     * 1 组合商品
     */
    private int bundlingGoodsType;
    /**
     * 一级类目ID
     */
    private int categoryID;
    /**
     * 三级类目ID
     */
    private int categoryThreeID;
    /**
     * 1 押金商品
     */
    private int depositProductType;
    /**
     * 优惠规则：
     * 订单活动 1-满赠，2-满减，3-满折，5-赠券，
     * 商品活动 1-买赠，4-直降，5-赠券，6-商品满减，7-商品打折
     */
    private int discountRuleType;
    private String discountRuleTypeName;
    private String groupID;
    private String imgUrl;
    /**
     * 是否收藏
     */
    private boolean isCollection;
    private boolean isDeliveryRange;
    /**
     * 1 代仓
     */
    private int isSupplierWarehouse;
    /**
     * 1 代仓
     */
    private int isWareHourse;
    /**
     * 1 隔日达
     */
    private int nextDayDelivery;
    private boolean priceIsVisible;
    private String productBrief;
    private String productCode;
    private String productID;
    private String productName;
    private int productStatus;
    /**
     * 0-非自营商品，1-自营商品
     */
    private int productType;
    private List<ProductSpecBean> specs;
    private String supplierName;
    private String supplierShopID;
    private String supplierShopName;
    /**
     * 是否置顶 0-非置顶，大于0-置顶
     */
    private int top;

    public ProductBean() {
    }

    protected ProductBean(Parcel in) {
        this.bundlingGoodsType = in.readInt();
        this.categoryID = in.readInt();
        this.categoryThreeID = in.readInt();
        this.depositProductType = in.readInt();
        this.discountRuleType = in.readInt();
        this.discountRuleTypeName = in.readString();
        this.groupID = in.readString();
        this.imgUrl = in.readString();
        this.isCollection = in.readByte() != 0;
        this.isDeliveryRange = in.readByte() != 0;
        this.isSupplierWarehouse = in.readInt();
        this.isWareHourse = in.readInt();
        this.nextDayDelivery = in.readInt();
        this.priceIsVisible = in.readByte() != 0;
        this.productBrief = in.readString();
        this.productCode = in.readString();
        this.productID = in.readString();
        this.productName = in.readString();
        this.productStatus = in.readInt();
        this.productType = in.readInt();
        this.specs = in.createTypedArrayList(ProductSpecBean.CREATOR);
        this.supplierName = in.readString();
        this.supplierShopID = in.readString();
        this.supplierShopName = in.readString();
        this.top = in.readInt();
    }

    public int getBundlingGoodsType() {
        return bundlingGoodsType;
    }

    public void setBundlingGoodsType(int bundlingGoodsType) {
        this.bundlingGoodsType = bundlingGoodsType;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getCategoryThreeID() {
        return categoryThreeID;
    }

    public void setCategoryThreeID(int categoryThreeID) {
        this.categoryThreeID = categoryThreeID;
    }

    public int getDepositProductType() {
        return depositProductType;
    }

    public void setDepositProductType(int depositProductType) {
        this.depositProductType = depositProductType;
    }

    public int getDiscountRuleType() {
        return discountRuleType;
    }

    public void setDiscountRuleType(int discountRuleType) {
        this.discountRuleType = discountRuleType;
    }

    public String getDiscountRuleTypeName() {
        return discountRuleTypeName;
    }

    public void setDiscountRuleTypeName(String discountRuleTypeName) {
        this.discountRuleTypeName = discountRuleTypeName;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        isCollection = collection;
    }

    public boolean isDeliveryRange() {
        return isDeliveryRange;
    }

    public void setDeliveryRange(boolean deliveryRange) {
        isDeliveryRange = deliveryRange;
    }

    public int getIsSupplierWarehouse() {
        return isSupplierWarehouse;
    }

    public void setIsSupplierWarehouse(int isSupplierWarehouse) {
        this.isSupplierWarehouse = isSupplierWarehouse;
    }

    public int getIsWareHourse() {
        return isWareHourse;
    }

    public void setIsWareHourse(int isWareHourse) {
        this.isWareHourse = isWareHourse;
    }

    public int getNextDayDelivery() {
        return nextDayDelivery;
    }

    public void setNextDayDelivery(int nextDayDelivery) {
        this.nextDayDelivery = nextDayDelivery;
    }

    public boolean isPriceIsVisible() {
        return priceIsVisible;
    }

    public void setPriceIsVisible(boolean priceIsVisible) {
        this.priceIsVisible = priceIsVisible;
    }

    public String getProductBrief() {
        return productBrief;
    }

    public void setProductBrief(String productBrief) {
        this.productBrief = productBrief;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(int productStatus) {
        this.productStatus = productStatus;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public List<ProductSpecBean> getSpecs() {
        return specs;
    }

    public void setSpecs(List<ProductSpecBean> specs) {
        this.specs = specs;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierShopID() {
        return supplierShopID;
    }

    public void setSupplierShopID(String supplierShopID) {
        this.supplierShopID = supplierShopID;
    }

    public String getSupplierShopName() {
        return supplierShopName;
    }

    public void setSupplierShopName(String supplierShopName) {
        this.supplierShopName = supplierShopName;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.bundlingGoodsType);
        dest.writeInt(this.categoryID);
        dest.writeInt(this.categoryThreeID);
        dest.writeInt(this.depositProductType);
        dest.writeInt(this.discountRuleType);
        dest.writeString(this.discountRuleTypeName);
        dest.writeString(this.groupID);
        dest.writeString(this.imgUrl);
        dest.writeByte(this.isCollection ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isDeliveryRange ? (byte) 1 : (byte) 0);
        dest.writeInt(this.isSupplierWarehouse);
        dest.writeInt(this.isWareHourse);
        dest.writeInt(this.nextDayDelivery);
        dest.writeByte(this.priceIsVisible ? (byte) 1 : (byte) 0);
        dest.writeString(this.productBrief);
        dest.writeString(this.productCode);
        dest.writeString(this.productID);
        dest.writeString(this.productName);
        dest.writeInt(this.productStatus);
        dest.writeInt(this.productType);
        dest.writeTypedList(this.specs);
        dest.writeString(this.supplierName);
        dest.writeString(this.supplierShopID);
        dest.writeString(this.supplierShopName);
        dest.writeInt(this.top);
    }
}
