package com.hll_sc_app.bean.aftersales;

import android.text.TextUtils;

import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/11
 */

public class AfterSalesApplyReq {

    private int billSource;
    private int clientSource;

    private List<AfterSalesApplyReq.DetailBean> detailList;

    private String groupID;
    private String groupName;
    private String id;
    private int payType;
    private int paymentWay;
    private String purchaserID;
    private String purchaserName;
    private String refundBillDate;
    private String refundBillRemark;
    private String refundBillNo;
    private int refundBillType;
    private String refundExplain;
    private String refundReason;
    private String refundVoucher;
    private String shopID;
    private String shopName;
    private String subBillID;
    private String subBillNo;
    private String supplyShopID;
    private String supplyShopName;
    private double totalAmount;

    public static AfterSalesApplyReq createFromAfterSalesParam(AfterSalesApplyParam param) {
        AfterSalesApplyReq req = new AfterSalesApplyReq();
        req.setBillSource(1);
        req.setClientSource(1);
        req.setRefundBillDate(CalendarUtils.toLocalDate(new Date()));
        req.setGroupID(param.getGroupID());
        req.setGroupName(param.getGroupName());
        req.setPayType(param.getPayType());
        req.setPaymentWay(param.getPaymentWay());
        req.setPurchaserID(param.getPurchaserID());
        req.setPurchaserName(param.getPurchaserName());
        req.setShopID(param.getShopID());
        req.setShopName(param.getShopName());
        req.setSubBillID(param.getSubBillID());
        req.setSubBillNo(param.getSubBillNo());
        req.setSupplyShopID(param.getSupplyShopID());
        req.setSupplyShopName(param.getSupplyShopName());
        req.setRefundExplain(param.getExplain());
        req.setRefundVoucher(param.getVoucher());
        req.setRefundReason(param.getReason());
        req.setRefundBillType(param.getAfterSalesType());
        if (!CommonUtils.isEmpty(param.getAfterSalesDetailList())) {
            double amount = 0;
            List<DetailBean> list = new ArrayList<>();
            for (AfterSalesDetailsBean bean : param.getAfterSalesDetailList()) {
                DetailBean detailBean = DetailBean.createFromAfterSalesDetail(bean);
                amount = CommonUtils.addDouble(detailBean.getRefundAmount(), amount, 0).doubleValue();
                list.add(detailBean);
            }
            req.setDetailList(list);
            req.setTotalAmount(amount);
        }
        if (!TextUtils.isEmpty(param.getId())) {
            req.setId(param.getId());
            req.setRefundBillNo(param.getRefundBillNo());
        }
        return req;
    }

    public int getBillSource() {
        return billSource;
    }

    public void setBillSource(int billSource) {
        this.billSource = billSource;
    }

    public int getClientSource() {
        return clientSource;
    }

    public void setClientSource(int clientSource) {
        this.clientSource = clientSource;
    }

    public List<DetailBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DetailBean> detailList) {
        this.detailList = detailList;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(int paymentWay) {
        this.paymentWay = paymentWay;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getRefundBillDate() {
        return refundBillDate;
    }

    public void setRefundBillDate(String refundBillDate) {
        this.refundBillDate = refundBillDate;
    }

    public String getRefundBillRemark() {
        return refundBillRemark;
    }

    public void setRefundBillRemark(String refundBillRemark) {
        this.refundBillRemark = refundBillRemark;
    }

    public String getRefundBillNo() {
        return refundBillNo;
    }

    public void setRefundBillNo(String refundBillNo) {
        this.refundBillNo = refundBillNo;
    }

    public int getRefundBillType() {
        return refundBillType;
    }

    public void setRefundBillType(int refundBillType) {
        this.refundBillType = refundBillType;
    }

    public String getRefundExplain() {
        return refundExplain;
    }

    public void setRefundExplain(String refundExplain) {
        this.refundExplain = refundExplain;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getRefundVoucher() {
        return refundVoucher;
    }

    public void setRefundVoucher(String refundVoucher) {
        this.refundVoucher = refundVoucher;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getSubBillID() {
        return subBillID;
    }

    public void setSubBillID(String subBillID) {
        this.subBillID = subBillID;
    }

    public String getSubBillNo() {
        return subBillNo;
    }

    public void setSubBillNo(String subBillNo) {
        this.subBillNo = subBillNo;
    }

    public String getSupplyShopID() {
        return supplyShopID;
    }

    public void setSupplyShopID(String supplyShopID) {
        this.supplyShopID = supplyShopID;
    }

    public String getSupplyShopName() {
        return supplyShopName;
    }

    public void setSupplyShopName(String supplyShopName) {
        this.supplyShopName = supplyShopName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public static class DetailBean {
        private double adjustmentNum;
        private String adjustmentUnit;
        private double auxiliaryPrice;
        private String auxiliaryUnit;
        private String convertRate;
        private String detailRemark;
        private String imgUrl;
        private double inspectionNum;
        private String inspectionUnit;
        private String productCategoryID;
        private String productCode;
        private String productID;
        private String productName;
        private double productNum;
        private double productPrice;
        private String productSpec;
        private String productSpecID;
        private double refundAmount;
        private double refundNum;
        private String refundUnit;
        private String saleUnitName;
        private String shopProductCategoryID;
        private String subBillDetailID;

        static DetailBean createFromAfterSalesDetail(AfterSalesDetailsBean item) {
            DetailBean bean = new DetailBean();
            bean.setAdjustmentNum(item.getAdjustmentNum());
            bean.setAdjustmentUnit(item.getAdjustmentUnit());
            bean.setAuxiliaryPrice(item.getAuxiliaryPrice());
            bean.setAuxiliaryUnit(item.getAuxiliaryUnit());
            bean.setConvertRate(String.valueOf(item.getConvertRate()));
            // NOTICE: 订单明细备注没有，传空字符串
            bean.setDetailRemark("");
            bean.setImgUrl(item.getImgUrl());
            bean.setInspectionNum(item.getInspectionNum());
            bean.setInspectionUnit(item.getInspectionUnit());
            bean.setProductCategoryID(item.getProductCategoryID());
            bean.setProductCode(item.getProductCode());
            bean.setProductID(item.getProductID());
            bean.setProductName(item.getProductName());
            bean.setProductNum(item.getProductNum());
            bean.setProductPrice(item.getProductPrice());
            bean.setProductSpecID(item.getProductSpecID());
            // 退款金额
            bean.setRefundAmount(item.getPendingRefundAmount());

            bean.setRefundNum(item.getRefundNum());
            // 退货单位为签收单位
            bean.setRefundUnit(item.getRefundUnit());
            bean.setSaleUnitName(item.getSaleUnitName());
            // 明细ID
            bean.setSubBillDetailID(TextUtils.isEmpty(item.getDetailID()) ?
                    item.getSubBillDetailID() : item.getDetailID());
            return bean;
        }

        public double getAdjustmentNum() {
            return adjustmentNum;
        }

        public void setAdjustmentNum(double adjustmentNum) {
            this.adjustmentNum = adjustmentNum;
        }

        public String getAdjustmentUnit() {
            return adjustmentUnit;
        }

        public void setAdjustmentUnit(String adjustmentUnit) {
            this.adjustmentUnit = adjustmentUnit;
        }

        public double getAuxiliaryPrice() {
            return auxiliaryPrice;
        }

        public void setAuxiliaryPrice(double auxiliaryPrice) {
            this.auxiliaryPrice = auxiliaryPrice;
        }

        public String getAuxiliaryUnit() {
            return auxiliaryUnit;
        }

        public void setAuxiliaryUnit(String auxiliaryUnit) {
            this.auxiliaryUnit = auxiliaryUnit;
        }

        public String getConvertRate() {
            return convertRate;
        }

        public void setConvertRate(String convertRate) {
            this.convertRate = convertRate;
        }

        public String getDetailRemark() {
            return detailRemark;
        }

        public void setDetailRemark(String detailRemark) {
            this.detailRemark = detailRemark;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public double getInspectionNum() {
            return inspectionNum;
        }

        public void setInspectionNum(double inspectionNum) {
            this.inspectionNum = inspectionNum;
        }

        public String getInspectionUnit() {
            return inspectionUnit;
        }

        public void setInspectionUnit(String inspectionUnit) {
            this.inspectionUnit = inspectionUnit;
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

        public double getProductNum() {
            return productNum;
        }

        public void setProductNum(double productNum) {
            this.productNum = productNum;
        }

        public double getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(double productPrice) {
            this.productPrice = productPrice;
        }

        public String getProductSpec() {
            return productSpec;
        }

        public void setProductSpec(String productSpec) {
            this.productSpec = productSpec;
        }

        public String getProductSpecID() {
            return productSpecID;
        }

        public void setProductSpecID(String productSpecID) {
            this.productSpecID = productSpecID;
        }

        public double getRefundAmount() {
            return refundAmount;
        }

        public void setRefundAmount(double refundAmount) {
            this.refundAmount = refundAmount;
        }

        public double getRefundNum() {
            return refundNum;
        }

        public void setRefundNum(double refundNum) {
            this.refundNum = refundNum;
        }

        public String getRefundUnit() {
            return refundUnit;
        }

        public void setRefundUnit(String refundUnit) {
            this.refundUnit = refundUnit;
        }

        public String getSaleUnitName() {
            return saleUnitName;
        }

        public void setSaleUnitName(String saleUnitName) {
            this.saleUnitName = saleUnitName;
        }

        public String getShopProductCategoryID() {
            return shopProductCategoryID;
        }

        public void setShopProductCategoryID(String shopProductCategoryID) {
            this.shopProductCategoryID = shopProductCategoryID;
        }

        public String getSubBillDetailID() {
            return subBillDetailID;
        }

        public void setSubBillDetailID(String subBillDetailID) {
            this.subBillDetailID = subBillDetailID;
        }
    }
}
