package com.hll_sc_app.bean.aftersales;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.hll_sc_app.bean.order.detail.TransferDetailBean;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/8
 */

public class AfterSalesDetailsBean implements Parcelable {
    private String productID;
    private int discountRuleType;
    private double discountAmount;
    private boolean canRefund;
    private double couponDiscountAmount;
    private String productName;
    private int bundlingGoodsType;
    private double standardNum;
    private String productSpecID;
    private int action;
    private String discountID;
    private double refundableAmount;
    private double inspectionNum;
    private double refundedNum;
    private long signTime;
    private double auxiliaryNum;
    private String supplyShopID;
    private String subBillID;
    private String groupID;
    private String purchaserName;
    private String groupName;
    private String productCategoryID;
    private double inspectionPrice;
    private String belongedToProductSpecID;
    private int isRefundAllNum;
    private String shopID;
    private String ruleID;
    private double adjustmentAmount;
    private double productPrice;
    private double refundedAmount;
    private String inspectionUnit;
    private double orderAdjustmentDiscountAmount;
    private double refundableNum;
    private String saleUnitName;
    private long actionTime;
    private long deliveryTime;
    private String standardSpecCode;
    private double replenishmentNum;
    private String shopName;
    private String allotID;
    private double orderDiscountAmount;
    private double newPrice;
    private String adjustmentUnit;
    private int isDepositPoduct;
    private double convertRate;
    private String supplyShopName;
    private String createby;
    private String purchaserID;
    private int isGiveProduct;
    private String shopProductCategoryID;
    private int subBillStatus;
    private String standardSpecID;
    private double standardTaxRate;
    private double auxiliaryPrice;
    private int isAuxiliaryUnit;
    private int discountType;
    private double orderInspectionDiscountAmount;
    private int productType;
    private String actionBy;
    private double costPrice;
    private String detailID;
    private double standardPrice;
    private int isShopMall;
    private double productNum;
    private String standardUnit;
    private String subBillDate;
    private String subBillNo;
    private double adjustmentPrice;
    private double adjustmentNum;
    private double subtotalAmount;
    private String imgUrl;
    private String thirdInspectionNum;
    private double inspectionAmount;
    private String productCode;
    private long createTime;
    private String productSpec;
    private double refundNum;
    private String erpRefundBillDetailID;
    private String onlyReceiveOrder;
    private int refundBillDate;
    private String auxiliaryUnit;
    private int refundBillType;
    private String subBillDetailID;
    private String id;
    private String goodsName;
    private String refundUnit;
    private String goodsID;
    private String detailRemark;
    private double inAmount;
    private int refundBillStatus;
    private String skuCode;
    private int billSource;
    private double deliveryAmount;
    private String refundBillID;
    private String erpGroupID;
    private double inNum;
    private double refundAmount;
    private String spareField4;
    private String spareField3;
    private String refundBillNo;
    private int operateModel;
    private double deliveryNum;
    private String goodsCode;
    private String spareField1;
    private String spareField2;
    private int homologous;
    private boolean selected;

    public double getPendingRefundAmount() {
        return refundNum >= refundableNum ? refundableAmount :
                CommonUtils.mulDouble(refundNum, newPrice, 4).doubleValue();
    }

    public String getTargetDetailID() {
        return TextUtils.isEmpty(detailID) ? subBillDetailID : detailID;
    }

    public TransferDetailBean convertToTransferDetail(String erpShopID) {
        TransferDetailBean detailBean = new TransferDetailBean();
        detailBean.setGoodsCode(goodsCode);
        detailBean.setThirdGroupID(erpGroupID);
        detailBean.setOperateModel(String.valueOf(operateModel));
        detailBean.setResourceType(String.valueOf(billSource - 1));
        detailBean.setErpShopID(erpShopID);
        detailBean.setGoodsName(goodsName);
        return detailBean;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getDiscountRuleType() {
        return discountRuleType;
    }

    public void setDiscountRuleType(int discountRuleType) {
        this.discountRuleType = discountRuleType;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public boolean isCanRefund() {
        return canRefund;
    }

    public void setCanRefund(boolean canRefund) {
        this.canRefund = canRefund;
    }

    public double getCouponDiscountAmount() {
        return couponDiscountAmount;
    }

    public void setCouponDiscountAmount(double couponDiscountAmount) {
        this.couponDiscountAmount = couponDiscountAmount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getBundlingGoodsType() {
        return bundlingGoodsType;
    }

    public void setBundlingGoodsType(int bundlingGoodsType) {
        this.bundlingGoodsType = bundlingGoodsType;
    }

    public double getStandardNum() {
        return standardNum;
    }

    public void setStandardNum(double standardNum) {
        this.standardNum = standardNum;
    }

    public String getProductSpecID() {
        return productSpecID;
    }

    public void setProductSpecID(String productSpecID) {
        this.productSpecID = productSpecID;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    public double getRefundableAmount() {
        return refundableAmount;
    }

    public void setRefundableAmount(double refundableAmount) {
        this.refundableAmount = refundableAmount;
    }

    public double getInspectionNum() {
        return inspectionNum;
    }

    public void setInspectionNum(double inspectionNum) {
        this.inspectionNum = inspectionNum;
    }

    public double getRefundedNum() {
        return refundedNum;
    }

    public void setRefundedNum(double refundedNum) {
        this.refundedNum = refundedNum;
    }

    public long getSignTime() {
        return signTime;
    }

    public void setSignTime(long signTime) {
        this.signTime = signTime;
    }

    public double getAuxiliaryNum() {
        return auxiliaryNum;
    }

    public void setAuxiliaryNum(double auxiliaryNum) {
        this.auxiliaryNum = auxiliaryNum;
    }

    public String getSupplyShopID() {
        return supplyShopID;
    }

    public void setSupplyShopID(String supplyShopID) {
        this.supplyShopID = supplyShopID;
    }

    public String getSubBillID() {
        return subBillID;
    }

    public void setSubBillID(String subBillID) {
        this.subBillID = subBillID;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getProductCategoryID() {
        return productCategoryID;
    }

    public void setProductCategoryID(String productCategoryID) {
        this.productCategoryID = productCategoryID;
    }

    public double getInspectionPrice() {
        return inspectionPrice;
    }

    public void setInspectionPrice(double inspectionPrice) {
        this.inspectionPrice = inspectionPrice;
    }

    public String getBelongedToProductSpecID() {
        return belongedToProductSpecID;
    }

    public void setBelongedToProductSpecID(String belongedToProductSpecID) {
        this.belongedToProductSpecID = belongedToProductSpecID;
    }

    public int getIsRefundAllNum() {
        return isRefundAllNum;
    }

    public void setIsRefundAllNum(int isRefundAllNum) {
        this.isRefundAllNum = isRefundAllNum;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getRuleID() {
        return ruleID;
    }

    public void setRuleID(String ruleID) {
        this.ruleID = ruleID;
    }

    public double getAdjustmentAmount() {
        return adjustmentAmount;
    }

    public void setAdjustmentAmount(double adjustmentAmount) {
        this.adjustmentAmount = adjustmentAmount;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getRefundedAmount() {
        return refundedAmount;
    }

    public void setRefundedAmount(double refundedAmount) {
        this.refundedAmount = refundedAmount;
    }

    public String getInspectionUnit() {
        return inspectionUnit;
    }

    public void setInspectionUnit(String inspectionUnit) {
        this.inspectionUnit = inspectionUnit;
    }

    public double getOrderAdjustmentDiscountAmount() {
        return orderAdjustmentDiscountAmount;
    }

    public void setOrderAdjustmentDiscountAmount(double orderAdjustmentDiscountAmount) {
        this.orderAdjustmentDiscountAmount = orderAdjustmentDiscountAmount;
    }

    public double getRefundableNum() {
        return refundableNum;
    }

    public void setRefundableNum(double refundableNum) {
        this.refundableNum = refundableNum;
    }

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

    public long getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(long deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getStandardSpecCode() {
        return standardSpecCode;
    }

    public void setStandardSpecCode(String standardSpecCode) {
        this.standardSpecCode = standardSpecCode;
    }

    public double getReplenishmentNum() {
        return replenishmentNum;
    }

    public void setReplenishmentNum(double replenishmentNum) {
        this.replenishmentNum = replenishmentNum;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAllotID() {
        return allotID;
    }

    public void setAllotID(String allotID) {
        this.allotID = allotID;
    }

    public double getOrderDiscountAmount() {
        return orderDiscountAmount;
    }

    public void setOrderDiscountAmount(double orderDiscountAmount) {
        this.orderDiscountAmount = orderDiscountAmount;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }

    public String getAdjustmentUnit() {
        return adjustmentUnit;
    }

    public void setAdjustmentUnit(String adjustmentUnit) {
        this.adjustmentUnit = adjustmentUnit;
    }

    public int getIsDepositPoduct() {
        return isDepositPoduct;
    }

    public void setIsDepositPoduct(int isDepositPoduct) {
        this.isDepositPoduct = isDepositPoduct;
    }

    public double getConvertRate() {
        return convertRate;
    }

    public void setConvertRate(double convertRate) {
        this.convertRate = convertRate;
    }

    public String getSupplyShopName() {
        return supplyShopName;
    }

    public void setSupplyShopName(String supplyShopName) {
        this.supplyShopName = supplyShopName;
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

    public int getIsGiveProduct() {
        return isGiveProduct;
    }

    public void setIsGiveProduct(int isGiveProduct) {
        this.isGiveProduct = isGiveProduct;
    }

    public String getShopProductCategoryID() {
        return shopProductCategoryID;
    }

    public void setShopProductCategoryID(String shopProductCategoryID) {
        this.shopProductCategoryID = shopProductCategoryID;
    }

    public int getSubBillStatus() {
        return subBillStatus;
    }

    public void setSubBillStatus(int subBillStatus) {
        this.subBillStatus = subBillStatus;
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

    public double getAuxiliaryPrice() {
        return auxiliaryPrice;
    }

    public void setAuxiliaryPrice(double auxiliaryPrice) {
        this.auxiliaryPrice = auxiliaryPrice;
    }

    public int getIsAuxiliaryUnit() {
        return isAuxiliaryUnit;
    }

    public void setIsAuxiliaryUnit(int isAuxiliaryUnit) {
        this.isAuxiliaryUnit = isAuxiliaryUnit;
    }

    public int getDiscountType() {
        return discountType;
    }

    public void setDiscountType(int discountType) {
        this.discountType = discountType;
    }

    public double getOrderInspectionDiscountAmount() {
        return orderInspectionDiscountAmount;
    }

    public void setOrderInspectionDiscountAmount(double orderInspectionDiscountAmount) {
        this.orderInspectionDiscountAmount = orderInspectionDiscountAmount;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public String getDetailID() {
        return detailID;
    }

    public void setDetailID(String detailID) {
        this.detailID = detailID;
    }

    public double getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(double standardPrice) {
        this.standardPrice = standardPrice;
    }

    public int getIsShopMall() {
        return isShopMall;
    }

    public void setIsShopMall(int isShopMall) {
        this.isShopMall = isShopMall;
    }

    public double getProductNum() {
        return productNum;
    }

    public void setProductNum(double productNum) {
        this.productNum = productNum;
    }

    public String getStandardUnit() {
        return standardUnit;
    }

    public void setStandardUnit(String standardUnit) {
        this.standardUnit = standardUnit;
    }

    public String getSubBillDate() {
        return subBillDate;
    }

    public void setSubBillDate(String subBillDate) {
        this.subBillDate = subBillDate;
    }

    public String getSubBillNo() {
        return subBillNo;
    }

    public void setSubBillNo(String subBillNo) {
        this.subBillNo = subBillNo;
    }

    public double getAdjustmentPrice() {
        return adjustmentPrice;
    }

    public void setAdjustmentPrice(double adjustmentPrice) {
        this.adjustmentPrice = adjustmentPrice;
    }

    public double getAdjustmentNum() {
        return adjustmentNum;
    }

    public void setAdjustmentNum(double adjustmentNum) {
        this.adjustmentNum = adjustmentNum;
    }

    public double getSubtotalAmount() {
        return subtotalAmount;
    }

    public void setSubtotalAmount(double subtotalAmount) {
        this.subtotalAmount = subtotalAmount;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getThirdInspectionNum() {
        return thirdInspectionNum;
    }

    public void setThirdInspectionNum(String thirdInspectionNum) {
        this.thirdInspectionNum = thirdInspectionNum;
    }

    public double getInspectionAmount() {
        return inspectionAmount;
    }

    public void setInspectionAmount(double inspectionAmount) {
        this.inspectionAmount = inspectionAmount;
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

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public double getRefundNum() {
        return refundNum;
    }

    public void setRefundNum(double refundNum) {
        this.refundNum = refundNum;
    }

    public String getErpRefundBillDetailID() {
        return erpRefundBillDetailID;
    }

    public void setErpRefundBillDetailID(String erpRefundBillDetailID) {
        this.erpRefundBillDetailID = erpRefundBillDetailID;
    }

    public String getOnlyReceiveOrder() {
        return onlyReceiveOrder;
    }

    public void setOnlyReceiveOrder(String onlyReceiveOrder) {
        this.onlyReceiveOrder = onlyReceiveOrder;
    }

    public int getRefundBillDate() {
        return refundBillDate;
    }

    public void setRefundBillDate(int refundBillDate) {
        this.refundBillDate = refundBillDate;
    }

    public String getAuxiliaryUnit() {
        return auxiliaryUnit;
    }

    public void setAuxiliaryUnit(String auxiliaryUnit) {
        this.auxiliaryUnit = auxiliaryUnit;
    }

    public int getRefundBillType() {
        return refundBillType;
    }

    public void setRefundBillType(int refundBillType) {
        this.refundBillType = refundBillType;
    }

    public String getSubBillDetailID() {
        return subBillDetailID;
    }

    public void setSubBillDetailID(String subBillDetailID) {
        this.subBillDetailID = subBillDetailID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getRefundUnit() {
        return TextUtils.isEmpty(refundUnit) ? inspectionUnit : refundUnit;
    }

    public void setRefundUnit(String refundUnit) {
        this.refundUnit = refundUnit;
    }

    public String getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(String goodsID) {
        this.goodsID = goodsID;
    }

    public String getDetailRemark() {
        return detailRemark;
    }

    public void setDetailRemark(String detailRemark) {
        this.detailRemark = detailRemark;
    }

    public double getInAmount() {
        return inAmount;
    }

    public void setInAmount(double inAmount) {
        this.inAmount = inAmount;
    }

    public int getRefundBillStatus() {
        return refundBillStatus;
    }

    public void setRefundBillStatus(int refundBillStatus) {
        this.refundBillStatus = refundBillStatus;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public int getBillSource() {
        return billSource;
    }

    public void setBillSource(int billSource) {
        this.billSource = billSource;
    }

    public double getDeliveryAmount() {
        return deliveryAmount;
    }

    public void setDeliveryAmount(double deliveryAmount) {
        this.deliveryAmount = deliveryAmount;
    }

    public String getRefundBillID() {
        return refundBillID;
    }

    public void setRefundBillID(String refundBillID) {
        this.refundBillID = refundBillID;
    }

    public String getErpGroupID() {
        return erpGroupID;
    }

    public void setErpGroupID(String erpGroupID) {
        this.erpGroupID = erpGroupID;
    }

    public double getInNum() {
        return inNum;
    }

    public void setInNum(double inNum) {
        this.inNum = inNum;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getSpareField4() {
        return spareField4;
    }

    public void setSpareField4(String spareField4) {
        this.spareField4 = spareField4;
    }

    public String getSpareField3() {
        return spareField3;
    }

    public void setSpareField3(String spareField3) {
        this.spareField3 = spareField3;
    }

    public String getRefundBillNo() {
        return refundBillNo;
    }

    public void setRefundBillNo(String refundBillNo) {
        this.refundBillNo = refundBillNo;
    }

    public int getOperateModel() {
        return operateModel;
    }

    public void setOperateModel(int operateModel) {
        this.operateModel = operateModel;
    }

    public double getDeliveryNum() {
        return deliveryNum;
    }

    public void setDeliveryNum(double deliveryNum) {
        this.deliveryNum = deliveryNum;
    }

    public String getSpareField2() {
        return spareField2;
    }

    public void setSpareField2(String spareField2) {
        this.spareField2 = spareField2;
    }

    public int getHomologous() {
        return homologous;
    }

    public void setHomologous(int homologous) {
        this.homologous = homologous;
    }

    public String getSpareField1() {
        return spareField1;
    }

    public void setSpareField1(String spareField1) {
        this.spareField1 = spareField1;
    }


    public AfterSalesDetailsBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productID);
        dest.writeInt(this.discountRuleType);
        dest.writeDouble(this.discountAmount);
        dest.writeByte(this.canRefund ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.couponDiscountAmount);
        dest.writeString(this.productName);
        dest.writeInt(this.bundlingGoodsType);
        dest.writeDouble(this.standardNum);
        dest.writeString(this.productSpecID);
        dest.writeInt(this.action);
        dest.writeString(this.discountID);
        dest.writeDouble(this.refundableAmount);
        dest.writeDouble(this.inspectionNum);
        dest.writeDouble(this.refundedNum);
        dest.writeLong(this.signTime);
        dest.writeDouble(this.auxiliaryNum);
        dest.writeString(this.supplyShopID);
        dest.writeString(this.subBillID);
        dest.writeString(this.groupID);
        dest.writeString(this.purchaserName);
        dest.writeString(this.groupName);
        dest.writeString(this.productCategoryID);
        dest.writeDouble(this.inspectionPrice);
        dest.writeString(this.belongedToProductSpecID);
        dest.writeInt(this.isRefundAllNum);
        dest.writeString(this.shopID);
        dest.writeString(this.ruleID);
        dest.writeDouble(this.adjustmentAmount);
        dest.writeDouble(this.productPrice);
        dest.writeDouble(this.refundedAmount);
        dest.writeString(this.inspectionUnit);
        dest.writeDouble(this.orderAdjustmentDiscountAmount);
        dest.writeDouble(this.refundableNum);
        dest.writeString(this.saleUnitName);
        dest.writeLong(this.actionTime);
        dest.writeLong(this.deliveryTime);
        dest.writeString(this.standardSpecCode);
        dest.writeDouble(this.replenishmentNum);
        dest.writeString(this.shopName);
        dest.writeString(this.allotID);
        dest.writeDouble(this.orderDiscountAmount);
        dest.writeDouble(this.newPrice);
        dest.writeString(this.adjustmentUnit);
        dest.writeInt(this.isDepositPoduct);
        dest.writeDouble(this.convertRate);
        dest.writeString(this.supplyShopName);
        dest.writeString(this.createby);
        dest.writeString(this.purchaserID);
        dest.writeInt(this.isGiveProduct);
        dest.writeString(this.shopProductCategoryID);
        dest.writeInt(this.subBillStatus);
        dest.writeString(this.standardSpecID);
        dest.writeDouble(this.standardTaxRate);
        dest.writeDouble(this.auxiliaryPrice);
        dest.writeInt(this.isAuxiliaryUnit);
        dest.writeInt(this.discountType);
        dest.writeDouble(this.orderInspectionDiscountAmount);
        dest.writeInt(this.productType);
        dest.writeString(this.actionBy);
        dest.writeDouble(this.costPrice);
        dest.writeString(this.detailID);
        dest.writeDouble(this.standardPrice);
        dest.writeInt(this.isShopMall);
        dest.writeDouble(this.productNum);
        dest.writeString(this.standardUnit);
        dest.writeString(this.subBillDate);
        dest.writeString(this.subBillNo);
        dest.writeDouble(this.adjustmentPrice);
        dest.writeDouble(this.adjustmentNum);
        dest.writeDouble(this.subtotalAmount);
        dest.writeString(this.imgUrl);
        dest.writeString(this.thirdInspectionNum);
        dest.writeDouble(this.inspectionAmount);
        dest.writeString(this.productCode);
        dest.writeLong(this.createTime);
        dest.writeString(this.productSpec);
        dest.writeDouble(this.refundNum);
        dest.writeString(this.erpRefundBillDetailID);
        dest.writeString(this.onlyReceiveOrder);
        dest.writeInt(this.refundBillDate);
        dest.writeString(this.auxiliaryUnit);
        dest.writeInt(this.refundBillType);
        dest.writeString(this.subBillDetailID);
        dest.writeString(this.id);
        dest.writeString(this.goodsName);
        dest.writeString(this.refundUnit);
        dest.writeString(this.goodsID);
        dest.writeString(this.detailRemark);
        dest.writeDouble(this.inAmount);
        dest.writeInt(this.refundBillStatus);
        dest.writeString(this.skuCode);
        dest.writeInt(this.billSource);
        dest.writeDouble(this.deliveryAmount);
        dest.writeString(this.refundBillID);
        dest.writeString(this.erpGroupID);
        dest.writeDouble(this.inNum);
        dest.writeDouble(this.refundAmount);
        dest.writeString(this.spareField4);
        dest.writeString(this.spareField3);
        dest.writeString(this.refundBillNo);
        dest.writeInt(this.operateModel);
        dest.writeDouble(this.deliveryNum);
        dest.writeString(this.goodsCode);
        dest.writeString(this.spareField1);
        dest.writeString(this.spareField2);
        dest.writeInt(this.homologous);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
    }

    protected AfterSalesDetailsBean(Parcel in) {
        this.productID = in.readString();
        this.discountRuleType = in.readInt();
        this.discountAmount = in.readDouble();
        this.canRefund = in.readByte() != 0;
        this.couponDiscountAmount = in.readDouble();
        this.productName = in.readString();
        this.bundlingGoodsType = in.readInt();
        this.standardNum = in.readDouble();
        this.productSpecID = in.readString();
        this.action = in.readInt();
        this.discountID = in.readString();
        this.refundableAmount = in.readDouble();
        this.inspectionNum = in.readDouble();
        this.refundedNum = in.readDouble();
        this.signTime = in.readLong();
        this.auxiliaryNum = in.readDouble();
        this.supplyShopID = in.readString();
        this.subBillID = in.readString();
        this.groupID = in.readString();
        this.purchaserName = in.readString();
        this.groupName = in.readString();
        this.productCategoryID = in.readString();
        this.inspectionPrice = in.readDouble();
        this.belongedToProductSpecID = in.readString();
        this.isRefundAllNum = in.readInt();
        this.shopID = in.readString();
        this.ruleID = in.readString();
        this.adjustmentAmount = in.readDouble();
        this.productPrice = in.readDouble();
        this.refundedAmount = in.readDouble();
        this.inspectionUnit = in.readString();
        this.orderAdjustmentDiscountAmount = in.readDouble();
        this.refundableNum = in.readDouble();
        this.saleUnitName = in.readString();
        this.actionTime = in.readLong();
        this.deliveryTime = in.readLong();
        this.standardSpecCode = in.readString();
        this.replenishmentNum = in.readDouble();
        this.shopName = in.readString();
        this.allotID = in.readString();
        this.orderDiscountAmount = in.readDouble();
        this.newPrice = in.readDouble();
        this.adjustmentUnit = in.readString();
        this.isDepositPoduct = in.readInt();
        this.convertRate = in.readDouble();
        this.supplyShopName = in.readString();
        this.createby = in.readString();
        this.purchaserID = in.readString();
        this.isGiveProduct = in.readInt();
        this.shopProductCategoryID = in.readString();
        this.subBillStatus = in.readInt();
        this.standardSpecID = in.readString();
        this.standardTaxRate = in.readDouble();
        this.auxiliaryPrice = in.readDouble();
        this.isAuxiliaryUnit = in.readInt();
        this.discountType = in.readInt();
        this.orderInspectionDiscountAmount = in.readDouble();
        this.productType = in.readInt();
        this.actionBy = in.readString();
        this.costPrice = in.readDouble();
        this.detailID = in.readString();
        this.standardPrice = in.readDouble();
        this.isShopMall = in.readInt();
        this.productNum = in.readDouble();
        this.standardUnit = in.readString();
        this.subBillDate = in.readString();
        this.subBillNo = in.readString();
        this.adjustmentPrice = in.readDouble();
        this.adjustmentNum = in.readDouble();
        this.subtotalAmount = in.readDouble();
        this.imgUrl = in.readString();
        this.thirdInspectionNum = in.readString();
        this.inspectionAmount = in.readDouble();
        this.productCode = in.readString();
        this.createTime = in.readLong();
        this.productSpec = in.readString();
        this.refundNum = in.readDouble();
        this.erpRefundBillDetailID = in.readString();
        this.onlyReceiveOrder = in.readString();
        this.refundBillDate = in.readInt();
        this.auxiliaryUnit = in.readString();
        this.refundBillType = in.readInt();
        this.subBillDetailID = in.readString();
        this.id = in.readString();
        this.goodsName = in.readString();
        this.refundUnit = in.readString();
        this.goodsID = in.readString();
        this.detailRemark = in.readString();
        this.inAmount = in.readDouble();
        this.refundBillStatus = in.readInt();
        this.skuCode = in.readString();
        this.billSource = in.readInt();
        this.deliveryAmount = in.readDouble();
        this.refundBillID = in.readString();
        this.erpGroupID = in.readString();
        this.inNum = in.readDouble();
        this.refundAmount = in.readDouble();
        this.spareField4 = in.readString();
        this.spareField3 = in.readString();
        this.refundBillNo = in.readString();
        this.operateModel = in.readInt();
        this.deliveryNum = in.readDouble();
        this.goodsCode = in.readString();
        this.spareField1 = in.readString();
        this.spareField2 = in.readString();
        this.homologous = in.readInt();
        this.selected = in.readByte() != 0;
    }

    public static final Creator<AfterSalesDetailsBean> CREATOR = new Creator<AfterSalesDetailsBean>() {
        @Override
        public AfterSalesDetailsBean createFromParcel(Parcel source) {
            return new AfterSalesDetailsBean(source);
        }

        @Override
        public AfterSalesDetailsBean[] newArray(int size) {
            return new AfterSalesDetailsBean[size];
        }
    };
}
