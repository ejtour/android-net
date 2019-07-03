package com.hll_sc_app.bean.order.detail;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/3
 */

public class TransferDetailBean implements Parcelable {
    private String saleUnitName;
    private long actionTime;
    private int productID;
    private int billSource;
    private String allotID;
    private String allotName;
    private String productName;
    private int isRelated;
    private int shipperType;
    private String unRelatedReason;
    private String auxiliaryUnit;
    private int productSpecID;
    private int plateSupplierID;
    private int action;
    private String failReason;
    private String billDetailID;
    private int id;
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
    private int operateModel;
    private String goodsCategoryID;
    private int productCategoryID;
    private String productCode;
    private long createTime;
    private String goodsCategoryName;
    private double goodsPrice;
    private String odmId;
    private String goodsCode;
    private int shopID;
    private int erpBillID;
    private String skuCode;
    private int homologous;
    private int status;

    public String getSaleUnitName() {
        return saleUnitName;
    }

    public void setSaleUnitName(String saleUnitName) {
        this.saleUnitName = saleUnitName;
    }

    public long getActionTime() {
        return actionTime;
    }

    public void setActionTime(long actionTime) {
        this.actionTime = actionTime;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
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

    public int getIsRelated() {
        return isRelated;
    }

    public void setIsRelated(int isRelated) {
        this.isRelated = isRelated;
    }

    public int getShipperType() {
        return shipperType;
    }

    public void setShipperType(int shipperType) {
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

    public int getProductSpecID() {
        return productSpecID;
    }

    public void setProductSpecID(int productSpecID) {
        this.productSpecID = productSpecID;
    }

    public int getPlateSupplierID() {
        return plateSupplierID;
    }

    public void setPlateSupplierID(int plateSupplierID) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getOperateModel() {
        return operateModel;
    }

    public void setOperateModel(int operateModel) {
        this.operateModel = operateModel;
    }

    public String getGoodsCategoryID() {
        return goodsCategoryID;
    }

    public void setGoodsCategoryID(String goodsCategoryID) {
        this.goodsCategoryID = goodsCategoryID;
    }

    public int getProductCategoryID() {
        return productCategoryID;
    }

    public void setProductCategoryID(int productCategoryID) {
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

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }

    public int getErpBillID() {
        return erpBillID;
    }

    public void setErpBillID(int erpBillID) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.saleUnitName);
        dest.writeLong(this.actionTime);
        dest.writeInt(this.productID);
        dest.writeInt(this.billSource);
        dest.writeString(this.allotID);
        dest.writeString(this.allotName);
        dest.writeString(this.productName);
        dest.writeInt(this.isRelated);
        dest.writeInt(this.shipperType);
        dest.writeString(this.unRelatedReason);
        dest.writeString(this.auxiliaryUnit);
        dest.writeInt(this.productSpecID);
        dest.writeInt(this.plateSupplierID);
        dest.writeInt(this.action);
        dest.writeString(this.failReason);
        dest.writeString(this.billDetailID);
        dest.writeInt(this.id);
        dest.writeString(this.orderUnit);
        dest.writeString(this.billNo);
        dest.writeString(this.goodsName);
        dest.writeDouble(this.goodsNum);
        dest.writeString(this.productSpec);
        dest.writeString(this.goodsDesc);
        dest.writeDouble(this.auxiliaryConvertRate);
        dest.writeDouble(this.auxiliaryNum);
        dest.writeString(this.goodsCategoryCode);
        dest.writeString(this.goodsID);
        dest.writeString(this.groupID);
        dest.writeString(this.imgUrl);
        dest.writeString(this.detailRemark);
        dest.writeDouble(this.totalAmount);
        dest.writeInt(this.operateModel);
        dest.writeString(this.goodsCategoryID);
        dest.writeInt(this.productCategoryID);
        dest.writeString(this.productCode);
        dest.writeLong(this.createTime);
        dest.writeString(this.goodsCategoryName);
        dest.writeDouble(this.goodsPrice);
        dest.writeString(this.odmId);
        dest.writeString(this.goodsCode);
        dest.writeInt(this.shopID);
        dest.writeInt(this.erpBillID);
        dest.writeString(this.skuCode);
        dest.writeInt(this.homologous);
        dest.writeInt(this.status);
    }

    public TransferDetailBean() {
    }

    protected TransferDetailBean(Parcel in) {
        this.saleUnitName = in.readString();
        this.actionTime = in.readLong();
        this.productID = in.readInt();
        this.billSource = in.readInt();
        this.allotID = in.readString();
        this.allotName = in.readString();
        this.productName = in.readString();
        this.isRelated = in.readInt();
        this.shipperType = in.readInt();
        this.unRelatedReason = in.readString();
        this.auxiliaryUnit = in.readString();
        this.productSpecID = in.readInt();
        this.plateSupplierID = in.readInt();
        this.action = in.readInt();
        this.failReason = in.readString();
        this.billDetailID = in.readString();
        this.id = in.readInt();
        this.orderUnit = in.readString();
        this.billNo = in.readString();
        this.goodsName = in.readString();
        this.goodsNum = in.readDouble();
        this.productSpec = in.readString();
        this.goodsDesc = in.readString();
        this.auxiliaryConvertRate = in.readDouble();
        this.auxiliaryNum = in.readDouble();
        this.goodsCategoryCode = in.readString();
        this.goodsID = in.readString();
        this.groupID = in.readString();
        this.imgUrl = in.readString();
        this.detailRemark = in.readString();
        this.totalAmount = in.readDouble();
        this.operateModel = in.readInt();
        this.goodsCategoryID = in.readString();
        this.productCategoryID = in.readInt();
        this.productCode = in.readString();
        this.createTime = in.readLong();
        this.goodsCategoryName = in.readString();
        this.goodsPrice = in.readDouble();
        this.odmId = in.readString();
        this.goodsCode = in.readString();
        this.shopID = in.readInt();
        this.erpBillID = in.readInt();
        this.skuCode = in.readString();
        this.homologous = in.readInt();
        this.status = in.readInt();
    }

    public static final Parcelable.Creator<TransferDetailBean> CREATOR = new Parcelable.Creator<TransferDetailBean>() {
        @Override
        public TransferDetailBean createFromParcel(Parcel source) {
            return new TransferDetailBean(source);
        }

        @Override
        public TransferDetailBean[] newArray(int size) {
            return new TransferDetailBean[size];
        }
    };
}
