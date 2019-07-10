package com.hll_sc_app.bean.agreementprice.quotation;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 报价单详情-商品信息
 *
 * @author zhuyingsong
 * @date 2019-07-08
 */
public class QuotationDetailBean implements Parcelable {
    private String saleUnitName;
    private String actionTime;
    private String productID;
    private String categoryName;
    /**
     * 商品名称
     */
    private String productName;
    private String billCreateTime;
    private String createby;
    private String purchaserID;
    /**
     * 价格
     */
    private String price;
    private String billID;
    private String productSpecID;
    private int billStatus;
    private String action;
    private String id;
    private String billNo;
    private String shopProductCategoryThreeID;
    private String billCreateBy;
    private String categoryID;
    private String actionBy;
    private String billType;
    private String groupID;
    /**
     * 成本价
     */
    private String costPrice;
    private String billDate;
    private String categoryCode;
    private String purchaserName;
    private String imgUrl;
    /**
     * 商品规格
     */
    private String productDesc;
    private String productCode;
    private String createTime;
    /**
     * 平台价格
     */
    private String productPrice;
    private String categorySubID;

    public String getCategorySubID() {
        return categorySubID;
    }

    public void setCategorySubID(String categorySubID) {
        this.categorySubID = categorySubID;
    }

    public String getSaleUnitName() {
        return saleUnitName;
    }

    public void setSaleUnitName(String saleUnitName) {
        this.saleUnitName = saleUnitName;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBillCreateTime() {
        return billCreateTime;
    }

    public void setBillCreateTime(String billCreateTime) {
        this.billCreateTime = billCreateTime;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBillID() {
        return billID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }

    public String getProductSpecID() {
        return productSpecID;
    }

    public void setProductSpecID(String productSpecID) {
        this.productSpecID = productSpecID;
    }

    public int getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(int billStatus) {
        this.billStatus = billStatus;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getShopProductCategoryThreeID() {
        return shopProductCategoryThreeID;
    }

    public void setShopProductCategoryThreeID(String shopProductCategoryThreeID) {
        this.shopProductCategoryThreeID = shopProductCategoryThreeID;
    }

    public String getBillCreateBy() {
        return billCreateBy;
    }

    public void setBillCreateBy(String billCreateBy) {
        this.billCreateBy = billCreateBy;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public static final Parcelable.Creator<QuotationDetailBean> CREATOR =
        new Parcelable.Creator<QuotationDetailBean>() {
        @Override
        public QuotationDetailBean createFromParcel(Parcel source) {
            return new QuotationDetailBean(source);
        }

        @Override
        public QuotationDetailBean[] newArray(int size) {
            return new QuotationDetailBean[size];
        }
    };

    public QuotationDetailBean() {
    }

    protected QuotationDetailBean(Parcel in) {
        this.saleUnitName = in.readString();
        this.actionTime = in.readString();
        this.productID = in.readString();
        this.categoryName = in.readString();
        this.productName = in.readString();
        this.billCreateTime = in.readString();
        this.createby = in.readString();
        this.purchaserID = in.readString();
        this.price = in.readString();
        this.billID = in.readString();
        this.productSpecID = in.readString();
        this.billStatus = in.readInt();
        this.action = in.readString();
        this.id = in.readString();
        this.billNo = in.readString();
        this.shopProductCategoryThreeID = in.readString();
        this.billCreateBy = in.readString();
        this.categoryID = in.readString();
        this.actionBy = in.readString();
        this.billType = in.readString();
        this.groupID = in.readString();
        this.costPrice = in.readString();
        this.billDate = in.readString();
        this.categoryCode = in.readString();
        this.purchaserName = in.readString();
        this.imgUrl = in.readString();
        this.productDesc = in.readString();
        this.productCode = in.readString();
        this.createTime = in.readString();
        this.productPrice = in.readString();
        this.categorySubID = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.saleUnitName);
        dest.writeString(this.actionTime);
        dest.writeString(this.productID);
        dest.writeString(this.categoryName);
        dest.writeString(this.productName);
        dest.writeString(this.billCreateTime);
        dest.writeString(this.createby);
        dest.writeString(this.purchaserID);
        dest.writeString(this.price);
        dest.writeString(this.billID);
        dest.writeString(this.productSpecID);
        dest.writeInt(this.billStatus);
        dest.writeString(this.action);
        dest.writeString(this.id);
        dest.writeString(this.billNo);
        dest.writeString(this.shopProductCategoryThreeID);
        dest.writeString(this.billCreateBy);
        dest.writeString(this.categoryID);
        dest.writeString(this.actionBy);
        dest.writeString(this.billType);
        dest.writeString(this.groupID);
        dest.writeString(this.costPrice);
        dest.writeString(this.billDate);
        dest.writeString(this.categoryCode);
        dest.writeString(this.purchaserName);
        dest.writeString(this.imgUrl);
        dest.writeString(this.productDesc);
        dest.writeString(this.productCode);
        dest.writeString(this.createTime);
        dest.writeString(this.productPrice);
        dest.writeString(this.categorySubID);
    }
}
