package com.hll_sc_app.bean.order.detail;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailBean;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/3
 */

public class TransferDetailBean implements Parcelable {
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
    private int specStatus;//4已上架 5未上架
    private double costPrice;
    private double enquiryPrice;
    private double productPrice;
    private String productDesc;
    private String enquiryID;

    public QuotationDetailBean convertToQuotationDetail() {
        QuotationDetailBean quotationDetailBean = new QuotationDetailBean();
        quotationDetailBean.setProductID(productID);
        quotationDetailBean.setProductName(productName);
        quotationDetailBean.setProductDesc(productDesc);
        quotationDetailBean.setProductCode(productCode);
        quotationDetailBean.setPrice(CommonUtils.formatNumber(enquiryPrice));
        quotationDetailBean.setProductPrice(CommonUtils.formatNumber(productPrice));
        quotationDetailBean.setCostPrice(CommonUtils.formatNumber(costPrice));
        return quotationDetailBean;
    }

    public String getEnquiryID() {
        return enquiryID;
    }

    public int getSpecStatus() {
        return specStatus;
    }

    public void setSpecStatus(int specStatus) {
        this.specStatus = specStatus;
    }

    public TransferDetailBean() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.purchaseUnit);
        dest.writeString(this.saleUnitName);
        dest.writeString(this.actionTime);
        dest.writeString(this.productID);
        dest.writeInt(this.billSource);
        dest.writeString(this.allotID);
        dest.writeString(this.allotName);
        dest.writeString(this.productName);
        dest.writeValue(this.isRelated);
        dest.writeString(this.shipperType);
        dest.writeString(this.unRelatedReason);
        dest.writeString(this.auxiliaryUnit);
        dest.writeString(this.productSpecID);
        dest.writeString(this.plateSupplierID);
        dest.writeInt(this.action);
        dest.writeString(this.failReason);
        dest.writeString(this.billDetailID);
        dest.writeString(this.id);
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
        dest.writeString(this.operateModel);
        dest.writeString(this.goodsCategoryID);
        dest.writeString(this.productCategoryID);
        dest.writeString(this.productCode);
        dest.writeLong(this.createTime);
        dest.writeString(this.goodsCategoryName);
        dest.writeDouble(this.goodsPrice);
        dest.writeString(this.odmId);
        dest.writeString(this.goodsCode);
        dest.writeString(this.shopID);
        dest.writeString(this.erpBillID);
        dest.writeString(this.skuCode);
        dest.writeInt(this.homologous);
        dest.writeInt(this.status);
        dest.writeString(this.shipperID);
        dest.writeString(this.relationStatus);
        dest.writeString(this.actionBy);
        dest.writeString(this.erpShopName);
        dest.writeString(this.shipperName);
        dest.writeString(this.thirdGroupName);
        dest.writeString(this.thirdGroupID);
        dest.writeString(this.erpShopID);
        dest.writeString(this.plateSupplierName);
        dest.writeString(this.resourceType);
        dest.writeInt(this.specStatus);
        dest.writeDouble(this.costPrice);
        dest.writeDouble(this.enquiryPrice);
        dest.writeDouble(this.productPrice);
        dest.writeString(this.productDesc);
        dest.writeString(this.enquiryID);
    }

    protected TransferDetailBean(Parcel in) {
        this.purchaseUnit = in.readString();
        this.saleUnitName = in.readString();
        this.actionTime = in.readString();
        this.productID = in.readString();
        this.billSource = in.readInt();
        this.allotID = in.readString();
        this.allotName = in.readString();
        this.productName = in.readString();
        this.isRelated = (Integer) in.readValue(Integer.class.getClassLoader());
        this.shipperType = in.readString();
        this.unRelatedReason = in.readString();
        this.auxiliaryUnit = in.readString();
        this.productSpecID = in.readString();
        this.plateSupplierID = in.readString();
        this.action = in.readInt();
        this.failReason = in.readString();
        this.billDetailID = in.readString();
        this.id = in.readString();
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
        this.operateModel = in.readString();
        this.goodsCategoryID = in.readString();
        this.productCategoryID = in.readString();
        this.productCode = in.readString();
        this.createTime = in.readLong();
        this.goodsCategoryName = in.readString();
        this.goodsPrice = in.readDouble();
        this.odmId = in.readString();
        this.goodsCode = in.readString();
        this.shopID = in.readString();
        this.erpBillID = in.readString();
        this.skuCode = in.readString();
        this.homologous = in.readInt();
        this.status = in.readInt();
        this.shipperID = in.readString();
        this.relationStatus = in.readString();
        this.actionBy = in.readString();
        this.erpShopName = in.readString();
        this.shipperName = in.readString();
        this.thirdGroupName = in.readString();
        this.thirdGroupID = in.readString();
        this.erpShopID = in.readString();
        this.plateSupplierName = in.readString();
        this.resourceType = in.readString();
        this.specStatus = in.readInt();
        this.costPrice = in.readDouble();
        this.enquiryPrice = in.readDouble();
        this.productPrice = in.readDouble();
        this.productDesc = in.readString();
        this.enquiryID = in.readString();
    }

    public static final Creator<TransferDetailBean> CREATOR = new Creator<TransferDetailBean>() {
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
