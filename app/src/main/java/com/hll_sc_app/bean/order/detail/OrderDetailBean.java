package com.hll_sc_app.bean.order.detail;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/12
 */

public class OrderDetailBean implements Parcelable {

    private int discountRuleType;
    private double discountAmount;
    private double couponDiscountAmount;
    private String productName;
    private String auxiliaryUnit;
    private int bundlingGoodsType;
    private double standardNum;
    private String standardSpec;
    private int productSpecID;
    private double ruleDiscountValue;
    private String billDetailID;
    private int discountID;
    private double inspectionNum;
    private double refundedNum;
    private double auxiliaryNum;
    private int supplyShopID;
    private int subBillID;
    private int groupID;
    private String detailRemark;
    private String extGroupID;
    private int productCategoryID;
    private double inspectionPrice;
    private int belongedToProductSpecID;
    private int isRefundAllNum;
    private int shopID;
    private int ruleID;
    private double adjustmentAmount;
    private double productPrice;
    private double refundedAmount;
    private String inspectionUnit;
    private double orderAdjustmentDiscountAmount;
    private String saleUnitName;
    private String bundlingGoodsDetail;
    private String standardSpecCode;
    private double replenishmentNum;
    private String shopName;
    private double orderDiscountAmount;
    private String adjustmentUnit;
    private int isDepositPoduct;
    private double convertRate;
    private String supplyShopName;
    private String discountName;
    private int isGiveProduct;
    private int shopProductCategoryID;
    private int subBillStatus;
    private int standardSpecID;
    private double standardTaxRate;
    private double auxiliaryPrice;
    private int isAuxiliaryUnit;
    private String ruleName;
    private int discountType;
    private double orderInspectionDiscountAmount;
    private String productSpec;
    private int productType;
    private String detailID;
    private double standardPrice;
    private int isShopMall;
    private double productNum;
    private String standardUnit;
    private int subBillDate;
    private String subBillNo;
    private double adjustmentPrice;
    private double adjustmentNum;
    private double subtotalAmount;
    private String imgUrl;
    private double inspectionAmount;
    private String productCode;
    private String productID;
    private double oldProductPrice;
    private double couponInspectionDiscountAmount;
    private double couponAdjustmentDiscountAmount;
    private List<OrderDepositBean> depositList;

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

    public String getAuxiliaryUnit() {
        return auxiliaryUnit;
    }

    public void setAuxiliaryUnit(String auxiliaryUnit) {
        this.auxiliaryUnit = auxiliaryUnit;
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

    public String getStandardSpec() {
        return standardSpec;
    }

    public void setStandardSpec(String standardSpec) {
        this.standardSpec = standardSpec;
    }

    public int getProductSpecID() {
        return productSpecID;
    }

    public void setProductSpecID(int productSpecID) {
        this.productSpecID = productSpecID;
    }

    public double getRuleDiscountValue() {
        return ruleDiscountValue;
    }

    public void setRuleDiscountValue(double ruleDiscountValue) {
        this.ruleDiscountValue = ruleDiscountValue;
    }

    public String getBillDetailID() {
        return billDetailID;
    }

    public void setBillDetailID(String billDetailID) {
        this.billDetailID = billDetailID;
    }

    public int getDiscountID() {
        return discountID;
    }

    public void setDiscountID(int discountID) {
        this.discountID = discountID;
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

    public double getAuxiliaryNum() {
        return auxiliaryNum;
    }

    public void setAuxiliaryNum(double auxiliaryNum) {
        this.auxiliaryNum = auxiliaryNum;
    }

    public int getSupplyShopID() {
        return supplyShopID;
    }

    public void setSupplyShopID(int supplyShopID) {
        this.supplyShopID = supplyShopID;
    }

    public int getSubBillID() {
        return subBillID;
    }

    public void setSubBillID(int subBillID) {
        this.subBillID = subBillID;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getDetailRemark() {
        return detailRemark;
    }

    public void setDetailRemark(String detailRemark) {
        this.detailRemark = detailRemark;
    }

    public String getExtGroupID() {
        return extGroupID;
    }

    public void setExtGroupID(String extGroupID) {
        this.extGroupID = extGroupID;
    }

    public int getProductCategoryID() {
        return productCategoryID;
    }

    public void setProductCategoryID(int productCategoryID) {
        this.productCategoryID = productCategoryID;
    }

    public double getInspectionPrice() {
        return inspectionPrice;
    }

    public void setInspectionPrice(double inspectionPrice) {
        this.inspectionPrice = inspectionPrice;
    }

    public int getBelongedToProductSpecID() {
        return belongedToProductSpecID;
    }

    public void setBelongedToProductSpecID(int belongedToProductSpecID) {
        this.belongedToProductSpecID = belongedToProductSpecID;
    }

    public int getIsRefundAllNum() {
        return isRefundAllNum;
    }

    public void setIsRefundAllNum(int isRefundAllNum) {
        this.isRefundAllNum = isRefundAllNum;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }

    public int getRuleID() {
        return ruleID;
    }

    public void setRuleID(int ruleID) {
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

    public String getSaleUnitName() {
        return saleUnitName;
    }

    public void setSaleUnitName(String saleUnitName) {
        this.saleUnitName = saleUnitName;
    }

    public String getBundlingGoodsDetail() {
        return bundlingGoodsDetail;
    }

    public void setBundlingGoodsDetail(String bundlingGoodsDetail) {
        this.bundlingGoodsDetail = bundlingGoodsDetail;
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

    public double getOrderDiscountAmount() {
        return orderDiscountAmount;
    }

    public void setOrderDiscountAmount(double orderDiscountAmount) {
        this.orderDiscountAmount = orderDiscountAmount;
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

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public int getIsGiveProduct() {
        return isGiveProduct;
    }

    public void setIsGiveProduct(int isGiveProduct) {
        this.isGiveProduct = isGiveProduct;
    }

    public int getShopProductCategoryID() {
        return shopProductCategoryID;
    }

    public void setShopProductCategoryID(int shopProductCategoryID) {
        this.shopProductCategoryID = shopProductCategoryID;
    }

    public int getSubBillStatus() {
        return subBillStatus;
    }

    public void setSubBillStatus(int subBillStatus) {
        this.subBillStatus = subBillStatus;
    }

    public int getStandardSpecID() {
        return standardSpecID;
    }

    public void setStandardSpecID(int standardSpecID) {
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

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
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

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
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

    public int getSubBillDate() {
        return subBillDate;
    }

    public void setSubBillDate(int subBillDate) {
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

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public double getOldProductPrice() {
        return oldProductPrice;
    }

    public void setOldProductPrice(double oldProductPrice) {
        this.oldProductPrice = oldProductPrice;
    }

    public double getCouponInspectionDiscountAmount() {
        return couponInspectionDiscountAmount;
    }

    public void setCouponInspectionDiscountAmount(double couponInspectionDiscountAmount) {
        this.couponInspectionDiscountAmount = couponInspectionDiscountAmount;
    }

    public double getCouponAdjustmentDiscountAmount() {
        return couponAdjustmentDiscountAmount;
    }

    public void setCouponAdjustmentDiscountAmount(double couponAdjustmentDiscountAmount) {
        this.couponAdjustmentDiscountAmount = couponAdjustmentDiscountAmount;
    }

    public List<OrderDepositBean> getDepositList() {
        return depositList;
    }

    public void setDepositList(List<OrderDepositBean> depositList) {
        this.depositList = depositList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.discountRuleType);
        dest.writeDouble(this.discountAmount);
        dest.writeDouble(this.couponDiscountAmount);
        dest.writeString(this.productName);
        dest.writeString(this.auxiliaryUnit);
        dest.writeInt(this.bundlingGoodsType);
        dest.writeDouble(this.standardNum);
        dest.writeString(this.standardSpec);
        dest.writeInt(this.productSpecID);
        dest.writeDouble(this.ruleDiscountValue);
        dest.writeString(this.billDetailID);
        dest.writeInt(this.discountID);
        dest.writeDouble(this.inspectionNum);
        dest.writeDouble(this.refundedNum);
        dest.writeDouble(this.auxiliaryNum);
        dest.writeInt(this.supplyShopID);
        dest.writeInt(this.subBillID);
        dest.writeInt(this.groupID);
        dest.writeString(this.detailRemark);
        dest.writeString(this.extGroupID);
        dest.writeInt(this.productCategoryID);
        dest.writeDouble(this.inspectionPrice);
        dest.writeInt(this.belongedToProductSpecID);
        dest.writeInt(this.isRefundAllNum);
        dest.writeInt(this.shopID);
        dest.writeInt(this.ruleID);
        dest.writeDouble(this.adjustmentAmount);
        dest.writeDouble(this.productPrice);
        dest.writeDouble(this.refundedAmount);
        dest.writeString(this.inspectionUnit);
        dest.writeDouble(this.orderAdjustmentDiscountAmount);
        dest.writeString(this.saleUnitName);
        dest.writeString(this.bundlingGoodsDetail);
        dest.writeString(this.standardSpecCode);
        dest.writeDouble(this.replenishmentNum);
        dest.writeString(this.shopName);
        dest.writeDouble(this.orderDiscountAmount);
        dest.writeString(this.adjustmentUnit);
        dest.writeInt(this.isDepositPoduct);
        dest.writeDouble(this.convertRate);
        dest.writeString(this.supplyShopName);
        dest.writeString(this.discountName);
        dest.writeInt(this.isGiveProduct);
        dest.writeInt(this.shopProductCategoryID);
        dest.writeInt(this.subBillStatus);
        dest.writeInt(this.standardSpecID);
        dest.writeDouble(this.standardTaxRate);
        dest.writeDouble(this.auxiliaryPrice);
        dest.writeInt(this.isAuxiliaryUnit);
        dest.writeString(this.ruleName);
        dest.writeInt(this.discountType);
        dest.writeDouble(this.orderInspectionDiscountAmount);
        dest.writeString(this.productSpec);
        dest.writeInt(this.productType);
        dest.writeString(this.detailID);
        dest.writeDouble(this.standardPrice);
        dest.writeInt(this.isShopMall);
        dest.writeDouble(this.productNum);
        dest.writeString(this.standardUnit);
        dest.writeInt(this.subBillDate);
        dest.writeString(this.subBillNo);
        dest.writeDouble(this.adjustmentPrice);
        dest.writeDouble(this.adjustmentNum);
        dest.writeDouble(this.subtotalAmount);
        dest.writeString(this.imgUrl);
        dest.writeDouble(this.inspectionAmount);
        dest.writeString(this.productCode);
        dest.writeString(this.productID);
        dest.writeDouble(this.oldProductPrice);
        dest.writeDouble(this.couponInspectionDiscountAmount);
        dest.writeDouble(this.couponAdjustmentDiscountAmount);
        dest.writeTypedList(this.depositList);
    }

    public OrderDetailBean() {
    }

    protected OrderDetailBean(Parcel in) {
        this.discountRuleType = in.readInt();
        this.discountAmount = in.readDouble();
        this.couponDiscountAmount = in.readDouble();
        this.productName = in.readString();
        this.auxiliaryUnit = in.readString();
        this.bundlingGoodsType = in.readInt();
        this.standardNum = in.readDouble();
        this.standardSpec = in.readString();
        this.productSpecID = in.readInt();
        this.ruleDiscountValue = in.readDouble();
        this.billDetailID = in.readString();
        this.discountID = in.readInt();
        this.inspectionNum = in.readDouble();
        this.refundedNum = in.readDouble();
        this.auxiliaryNum = in.readDouble();
        this.supplyShopID = in.readInt();
        this.subBillID = in.readInt();
        this.groupID = in.readInt();
        this.detailRemark = in.readString();
        this.extGroupID = in.readString();
        this.productCategoryID = in.readInt();
        this.inspectionPrice = in.readDouble();
        this.belongedToProductSpecID = in.readInt();
        this.isRefundAllNum = in.readInt();
        this.shopID = in.readInt();
        this.ruleID = in.readInt();
        this.adjustmentAmount = in.readDouble();
        this.productPrice = in.readDouble();
        this.refundedAmount = in.readDouble();
        this.inspectionUnit = in.readString();
        this.orderAdjustmentDiscountAmount = in.readDouble();
        this.saleUnitName = in.readString();
        this.bundlingGoodsDetail = in.readString();
        this.standardSpecCode = in.readString();
        this.replenishmentNum = in.readDouble();
        this.shopName = in.readString();
        this.orderDiscountAmount = in.readDouble();
        this.adjustmentUnit = in.readString();
        this.isDepositPoduct = in.readInt();
        this.convertRate = in.readDouble();
        this.supplyShopName = in.readString();
        this.discountName = in.readString();
        this.isGiveProduct = in.readInt();
        this.shopProductCategoryID = in.readInt();
        this.subBillStatus = in.readInt();
        this.standardSpecID = in.readInt();
        this.standardTaxRate = in.readDouble();
        this.auxiliaryPrice = in.readDouble();
        this.isAuxiliaryUnit = in.readInt();
        this.ruleName = in.readString();
        this.discountType = in.readInt();
        this.orderInspectionDiscountAmount = in.readDouble();
        this.productSpec = in.readString();
        this.productType = in.readInt();
        this.detailID = in.readString();
        this.standardPrice = in.readDouble();
        this.isShopMall = in.readInt();
        this.productNum = in.readDouble();
        this.standardUnit = in.readString();
        this.subBillDate = in.readInt();
        this.subBillNo = in.readString();
        this.adjustmentPrice = in.readDouble();
        this.adjustmentNum = in.readDouble();
        this.subtotalAmount = in.readDouble();
        this.imgUrl = in.readString();
        this.inspectionAmount = in.readDouble();
        this.productCode = in.readString();
        this.productID = in.readString();
        this.oldProductPrice = in.readDouble();
        this.couponInspectionDiscountAmount = in.readDouble();
        this.couponAdjustmentDiscountAmount = in.readDouble();
        this.depositList = in.createTypedArrayList(OrderDepositBean.CREATOR);
    }

    public static final Parcelable.Creator<OrderDetailBean> CREATOR = new Parcelable.Creator<OrderDetailBean>() {
        @Override
        public OrderDetailBean createFromParcel(Parcel source) {
            return new OrderDetailBean(source);
        }

        @Override
        public OrderDetailBean[] newArray(int size) {
            return new OrderDetailBean[size];
        }
    };
}
