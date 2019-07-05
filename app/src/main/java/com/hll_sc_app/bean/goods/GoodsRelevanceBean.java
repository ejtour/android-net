package com.hll_sc_app.bean.goods;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 关联商品 Bean
 *
 * @author zhuyingsong
 * @date 2019-07-04
 */
public class GoodsRelevanceBean implements Parcelable {
    private String actionTime;
    private String shipperID;
    private String productID;
    private String relationStatus;
    private String actionBy;
    private String erpShopName;
    private String goodsID;
    private String shipperName;
    private String thirdGroupName;
    private String operateModel;
    private String shipperType;
    private String productSpecID;
    private String plateSupplierID;
    private String thirdGroupID;
    private String erpShopID;
    private String goodsCode;
    private String id;
    private String shopID;
    private String plateSupplierName;
    private String goodsName;
    private String resourceType;
    private String saleUnitName;
    private String productName;
    private String productSpec;

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getShipperID() {
        return shipperID;
    }

    public void setShipperID(String shipperID) {
        this.shipperID = shipperID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(String relationStatus) {
        this.relationStatus = relationStatus;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getErpShopName() {
        return erpShopName;
    }

    public void setErpShopName(String erpShopName) {
        this.erpShopName = erpShopName;
    }

    public String getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(String goodsID) {
        this.goodsID = goodsID;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public String getThirdGroupName() {
        return thirdGroupName;
    }

    public void setThirdGroupName(String thirdGroupName) {
        this.thirdGroupName = thirdGroupName;
    }

    public String getOperateModel() {
        return operateModel;
    }

    public void setOperateModel(String operateModel) {
        this.operateModel = operateModel;
    }

    public String getShipperType() {
        return shipperType;
    }

    public void setShipperType(String shipperType) {
        this.shipperType = shipperType;
    }

    public String getProductSpecID() {
        return productSpecID;
    }

    public void setProductSpecID(String productSpecID) {
        this.productSpecID = productSpecID;
    }

    public String getPlateSupplierID() {
        return plateSupplierID;
    }

    public void setPlateSupplierID(String plateSupplierID) {
        this.plateSupplierID = plateSupplierID;
    }

    public String getThirdGroupID() {
        return thirdGroupID;
    }

    public void setThirdGroupID(String thirdGroupID) {
        this.thirdGroupID = thirdGroupID;
    }

    public String getErpShopID() {
        return erpShopID;
    }

    public void setErpShopID(String erpShopID) {
        this.erpShopID = erpShopID;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getPlateSupplierName() {
        return plateSupplierName;
    }

    public void setPlateSupplierName(String plateSupplierName) {
        this.plateSupplierName = plateSupplierName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public static final Parcelable.Creator<GoodsRelevanceBean> CREATOR = new Parcelable.Creator<GoodsRelevanceBean>() {
        @Override
        public GoodsRelevanceBean createFromParcel(Parcel source) {
            return new GoodsRelevanceBean(source);
        }

        @Override
        public GoodsRelevanceBean[] newArray(int size) {
            return new GoodsRelevanceBean[size];
        }
    };

    public GoodsRelevanceBean() {
    }

    protected GoodsRelevanceBean(Parcel in) {
        this.actionTime = in.readString();
        this.shipperID = in.readString();
        this.productID = in.readString();
        this.relationStatus = in.readString();
        this.actionBy = in.readString();
        this.erpShopName = in.readString();
        this.goodsID = in.readString();
        this.shipperName = in.readString();
        this.thirdGroupName = in.readString();
        this.operateModel = in.readString();
        this.shipperType = in.readString();
        this.productSpecID = in.readString();
        this.plateSupplierID = in.readString();
        this.thirdGroupID = in.readString();
        this.erpShopID = in.readString();
        this.goodsCode = in.readString();
        this.id = in.readString();
        this.shopID = in.readString();
        this.plateSupplierName = in.readString();
        this.goodsName = in.readString();
        this.resourceType = in.readString();
        this.saleUnitName = in.readString();
        this.productName = in.readString();
        this.productSpec = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.actionTime);
        dest.writeString(this.shipperID);
        dest.writeString(this.productID);
        dest.writeString(this.relationStatus);
        dest.writeString(this.actionBy);
        dest.writeString(this.erpShopName);
        dest.writeString(this.goodsID);
        dest.writeString(this.shipperName);
        dest.writeString(this.thirdGroupName);
        dest.writeString(this.operateModel);
        dest.writeString(this.shipperType);
        dest.writeString(this.productSpecID);
        dest.writeString(this.plateSupplierID);
        dest.writeString(this.thirdGroupID);
        dest.writeString(this.erpShopID);
        dest.writeString(this.goodsCode);
        dest.writeString(this.id);
        dest.writeString(this.shopID);
        dest.writeString(this.plateSupplierName);
        dest.writeString(this.goodsName);
        dest.writeString(this.resourceType);
        dest.writeString(this.saleUnitName);
        dest.writeString(this.productName);
        dest.writeString(this.productSpec);
    }
}
