package com.hll_sc_app.bean.order.detail;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/3
 */

public class TransferDetailBean implements Parcelable {
    public static final Creator<TransferDetailBean> CREATOR = new Creator<TransferDetailBean>() {
        @Override
        public TransferDetailBean createFromParcel(Parcel in) {
            return new TransferDetailBean(in);
        }

        @Override
        public TransferDetailBean[] newArray(int size) {
            return new TransferDetailBean[size];
        }
    };
    private String purchaseUnit;
    private String saleUnitName;
    private String actionTime;
    private String productID;
    private int billSource;
    private String allotID;
    private String allotName;
    private String productName;
    private Integer isRelated;
    private String shipperType;
    private String unRelatedReason;
    private String auxiliaryUnit;
    private String productSpecID;
    private String plateSupplierID;
    private int action;
    private String failReason;
    private String billDetailID;
    private String id;
    private String orderUnit;
    private String billNo;
    private String goodsName;
    private double goodsNum;
    private String productSpec;
    private String goodsDesc;
    private double auxiliaryConvertRate;
    private double auxiliaryNum;
    private String goodsCategoryCode;
    private String goodsID;
    private String groupID;
    private String imgUrl;
    private String detailRemark;
    private double totalAmount;
    private String operateModel;
    private String goodsCategoryID;
    private String productCategoryID;
    private String productCode;
    private long createTime;
    private String goodsCategoryName;
    private double goodsPrice;
    private String odmId;
    private String goodsCode;
    private String shopID;
    private String erpBillID;
    private String skuCode;
    private int homologous;
    private int status;
    private String shipperID;
    private String relationStatus;
    private String actionBy;
    private String erpShopName;
    private String shipperName;
    private String thirdGroupName;
    private String thirdGroupID;
    private String erpShopID;
    private String plateSupplierName;
    private String resourceType;

    public TransferDetailBean() {
    }

    protected TransferDetailBean(Parcel in) {
        purchaseUnit = in.readString();
        saleUnitName = in.readString();
        actionTime = in.readString();
        productID = in.readString();
        billSource = in.readInt();
        allotID = in.readString();
        allotName = in.readString();
        productName = in.readString();
        if (in.readByte() == 0) {
            isRelated = null;
        } else {
            isRelated = in.readInt();
        }
        shipperType = in.readString();
        unRelatedReason = in.readString();
        auxiliaryUnit = in.readString();
        productSpecID = in.readString();
        plateSupplierID = in.readString();
        action = in.readInt();
        failReason = in.readString();
        billDetailID = in.readString();
        id = in.readString();
        orderUnit = in.readString();
        billNo = in.readString();
        goodsName = in.readString();
        goodsNum = in.readDouble();
        productSpec = in.readString();
        goodsDesc = in.readString();
        auxiliaryConvertRate = in.readDouble();
        auxiliaryNum = in.readDouble();
        goodsCategoryCode = in.readString();
        goodsID = in.readString();
        groupID = in.readString();
        imgUrl = in.readString();
        detailRemark = in.readString();
        totalAmount = in.readDouble();
        operateModel = in.readString();
        goodsCategoryID = in.readString();
        productCategoryID = in.readString();
        productCode = in.readString();
        createTime = in.readLong();
        goodsCategoryName = in.readString();
        goodsPrice = in.readDouble();
        odmId = in.readString();
        goodsCode = in.readString();
        shopID = in.readString();
        erpBillID = in.readString();
        skuCode = in.readString();
        homologous = in.readInt();
        status = in.readInt();
        shipperID = in.readString();
        relationStatus = in.readString();
        actionBy = in.readString();
        erpShopName = in.readString();
        shipperName = in.readString();
        thirdGroupName = in.readString();
        thirdGroupID = in.readString();
        erpShopID = in.readString();
        plateSupplierName = in.readString();
        resourceType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(purchaseUnit);
        dest.writeString(saleUnitName);
        dest.writeString(actionTime);
        dest.writeString(productID);
        dest.writeInt(billSource);
        dest.writeString(allotID);
        dest.writeString(allotName);
        dest.writeString(productName);
        if (isRelated == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(isRelated);
        }
        dest.writeString(shipperType);
        dest.writeString(unRelatedReason);
        dest.writeString(auxiliaryUnit);
        dest.writeString(productSpecID);
        dest.writeString(plateSupplierID);
        dest.writeInt(action);
        dest.writeString(failReason);
        dest.writeString(billDetailID);
        dest.writeString(id);
        dest.writeString(orderUnit);
        dest.writeString(billNo);
        dest.writeString(goodsName);
        dest.writeDouble(goodsNum);
        dest.writeString(productSpec);
        dest.writeString(goodsDesc);
        dest.writeDouble(auxiliaryConvertRate);
        dest.writeDouble(auxiliaryNum);
        dest.writeString(goodsCategoryCode);
        dest.writeString(goodsID);
        dest.writeString(groupID);
        dest.writeString(imgUrl);
        dest.writeString(detailRemark);
        dest.writeDouble(totalAmount);
        dest.writeString(operateModel);
        dest.writeString(goodsCategoryID);
        dest.writeString(productCategoryID);
        dest.writeString(productCode);
        dest.writeLong(createTime);
        dest.writeString(goodsCategoryName);
        dest.writeDouble(goodsPrice);
        dest.writeString(odmId);
        dest.writeString(goodsCode);
        dest.writeString(shopID);
        dest.writeString(erpBillID);
        dest.writeString(skuCode);
        dest.writeInt(homologous);
        dest.writeInt(status);
        dest.writeString(shipperID);
        dest.writeString(relationStatus);
        dest.writeString(actionBy);
        dest.writeString(erpShopName);
        dest.writeString(shipperName);
        dest.writeString(thirdGroupName);
        dest.writeString(thirdGroupID);
        dest.writeString(erpShopID);
        dest.writeString(plateSupplierName);
        dest.writeString(resourceType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getPurchaseUnit() {
        return purchaseUnit;
    }

    public void setPurchaseUnit(String purchaseUnit) {
        this.purchaseUnit = purchaseUnit;
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

    public int getBillSource() {
        return billSource;
    }

    public void setBillSource(int billSource) {
        this.billSource = billSource;
    }

    public String getAllotID() {
        return allotID;
    }

    public void setAllotID(String allotID) {
        this.allotID = allotID;
    }

    public String getAllotName() {
        return allotName;
    }

    public void setAllotName(String allotName) {
        this.allotName = allotName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getIsRelated() {
        return isRelated;
    }

    public void setIsRelated(Integer isRelated) {
        this.isRelated = isRelated;
    }

    public String getShipperType() {
        return shipperType;
    }

    public void setShipperType(String shipperType) {
        this.shipperType = shipperType;
    }

    public String getUnRelatedReason() {
        return unRelatedReason;
    }

    public void setUnRelatedReason(String unRelatedReason) {
        this.unRelatedReason = unRelatedReason;
    }

    public String getAuxiliaryUnit() {
        return auxiliaryUnit;
    }

    public void setAuxiliaryUnit(String auxiliaryUnit) {
        this.auxiliaryUnit = auxiliaryUnit;
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

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public String getBillDetailID() {
        return billDetailID;
    }

    public void setBillDetailID(String billDetailID) {
        this.billDetailID = billDetailID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderUnit() {
        return orderUnit;
    }

    public void setOrderUnit(String orderUnit) {
        this.orderUnit = orderUnit;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(double goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public double getAuxiliaryConvertRate() {
        return auxiliaryConvertRate;
    }

    public void setAuxiliaryConvertRate(double auxiliaryConvertRate) {
        this.auxiliaryConvertRate = auxiliaryConvertRate;
    }

    public double getAuxiliaryNum() {
        return auxiliaryNum;
    }

    public void setAuxiliaryNum(double auxiliaryNum) {
        this.auxiliaryNum = auxiliaryNum;
    }

    public String getGoodsCategoryCode() {
        return goodsCategoryCode;
    }

    public void setGoodsCategoryCode(String goodsCategoryCode) {
        this.goodsCategoryCode = goodsCategoryCode;
    }

    public String getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(String goodsID) {
        this.goodsID = goodsID;
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

    public String getDetailRemark() {
        return detailRemark;
    }

    public void setDetailRemark(String detailRemark) {
        this.detailRemark = detailRemark;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOperateModel() {
        return operateModel;
    }

    public void setOperateModel(String operateModel) {
        this.operateModel = operateModel;
    }

    public String getGoodsCategoryID() {
        return goodsCategoryID;
    }

    public void setGoodsCategoryID(String goodsCategoryID) {
        this.goodsCategoryID = goodsCategoryID;
    }

    public String getProductCategoryID() {
        return productCategoryID;
    }

    public void setProductCategoryID(String productCategoryID) {
        this.productCategoryID = productCategoryID;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getGoodsCategoryName() {
        return goodsCategoryName;
    }

    public void setGoodsCategoryName(String goodsCategoryName) {
        this.goodsCategoryName = goodsCategoryName;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getOdmId() {
        return odmId;
    }

    public void setOdmId(String odmId) {
        this.odmId = odmId;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getErpBillID() {
        return erpBillID;
    }

    public void setErpBillID(String erpBillID) {
        this.erpBillID = erpBillID;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public int getHomologous() {
        return homologous;
    }

    public void setHomologous(int homologous) {
        this.homologous = homologous;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getShipperID() {
        return shipperID;
    }

    public void setShipperID(String shipperID) {
        this.shipperID = shipperID;
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

    public String getThirdGroupID() {
        return TextUtils.isEmpty(thirdGroupID) ? groupID : thirdGroupID;
    }

    public void setThirdGroupID(String thirdGroupID) {
        this.thirdGroupID = thirdGroupID;
    }

    public String getErpShopID() {
        return TextUtils.isEmpty(erpShopID) ? allotID : erpShopID;
    }

    public void setErpShopID(String erpShopID) {
        this.erpShopID = erpShopID;
    }

    public String getPlateSupplierName() {
        return plateSupplierName;
    }

    public void setPlateSupplierName(String plateSupplierName) {
        this.plateSupplierName = plateSupplierName;
    }

    public String getResourceType() {
        return TextUtils.isEmpty(resourceType) ? String.valueOf(billSource) : resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

}
