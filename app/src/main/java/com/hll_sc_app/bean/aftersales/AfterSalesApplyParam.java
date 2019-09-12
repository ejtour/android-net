package com.hll_sc_app.bean.aftersales;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.hll_sc_app.app.aftersales.common.AfterSalesType;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.order.detail.OrderDetailBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/11
 */

public class AfterSalesApplyParam implements Parcelable {
    private List<AfterSalesDetailsBean> afterSalesDetailList;
    private List<OrderDetailBean> orderDetailList;
    private String groupID;
    private String groupName;
    private String id;
    private int payType;
    private int paymentWay;
    private String purchaserID;
    private String purchaserName;
    private String refundBillNo;
    private int refundBillType;
    private String explain;
    private String reason;
    private String voucher;
    private String reasonDesc;
    private String shopID;
    private String shopName;
    private String subBillID;
    private String subBillNo;
    private String supplyShopID;
    private String supplyShopName;
    private int afterSalesType;

    public static AfterSalesApplyParam afterSalesFromOrder(OrderResp resp, int type) {
        AfterSalesApplyParam param = new AfterSalesApplyParam();
        param.setGroupID(resp.getGroupID());
        param.setGroupName(resp.getGroupName());
        param.setPayType(resp.getPayType());
        param.setPaymentWay(resp.getPaymentWay());
        param.setPurchaserID(resp.getPurchaserID());
        param.setPurchaserName(resp.getPurchaserName());
        param.setShopID(resp.getShopID());
        param.setShopName(resp.getShopName());
        param.setSubBillID(resp.getSubBillID());
        param.setSubBillNo(resp.getSubBillNo());
        param.setSupplyShopID(resp.getSupplyShopID());
        param.setSupplyShopName(resp.getSupplyShopName());
        param.setAfterSalesType(type);
        return param;
    }

    public static AfterSalesApplyParam afterSalesFromAfterSales(AfterSalesBean resp, int type) {
        AfterSalesApplyParam param = new AfterSalesApplyParam();
        param.setAfterSalesDetailList(resp.getDetailList());
        param.setGroupID(resp.getGroupID());
        param.setGroupName(resp.getGroupName());
        param.setPayType(resp.getPayType());
        param.setPaymentWay(resp.getPaymentWay());
        param.setPurchaserID(resp.getPurchaserID());
        param.setPurchaserName(resp.getPurchaserName());
        param.setShopID(resp.getShopID());
        param.setShopName(resp.getShopName());
        param.setSubBillID(resp.getSubBillID());
        param.setSubBillNo(resp.getSubBillNo());
        param.setSupplyShopID(resp.getSupplyShopID());
        param.setSupplyShopName(resp.getSupplyShopName());
        param.setAfterSalesType(type);

        param.setId(resp.getId());
        param.setRefundBillNo(resp.getRefundBillNo());
        param.setRefundBillType(resp.getRefundBillType());
        param.setExplain(resp.getRefundExplain());
        param.setReason(String.valueOf(resp.getRefundReason()));
        param.setVoucher(resp.getRefundVoucher());
        param.setReasonDesc(resp.getRefundReasonDesc());
        return param;
    }

    public static AfterSalesApplyParam rejectFromOrder(OrderResp resp) {
        AfterSalesApplyParam param = new AfterSalesApplyParam();
        param.setOrderDetailList(resp.getBillDetailList());
        param.setSubBillID(resp.getSubBillID());
        param.setAfterSalesType(AfterSalesType.ORDER_REJECT);
        return param;
    }

    public List<OrderDetailBean> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetailBean> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public List<AfterSalesDetailsBean> getAfterSalesDetailList() {
        return afterSalesDetailList;
    }

    public void setAfterSalesDetailList(List<AfterSalesDetailsBean> afterSalesDetailList) {
        this.afterSalesDetailList = afterSalesDetailList;
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

    public boolean isReApply() {
        return TextUtils.isEmpty(id);
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

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
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

    public String getReasonDesc() {
        return reasonDesc;
    }

    public void setReasonDesc(String reasonDesc) {
        this.reasonDesc = reasonDesc;
    }

    @AfterSalesType
    public int getAfterSalesType() {
        return afterSalesType;
    }

    public void setAfterSalesType(int afterSalesType) {
        this.afterSalesType = afterSalesType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.afterSalesDetailList);
        dest.writeTypedList(this.orderDetailList);
        dest.writeString(this.groupID);
        dest.writeString(this.groupName);
        dest.writeString(this.id);
        dest.writeInt(this.payType);
        dest.writeInt(this.paymentWay);
        dest.writeString(this.purchaserID);
        dest.writeString(this.purchaserName);
        dest.writeString(this.refundBillNo);
        dest.writeInt(this.refundBillType);
        dest.writeString(this.explain);
        dest.writeString(this.reason);
        dest.writeString(this.voucher);
        dest.writeString(this.reasonDesc);
        dest.writeString(this.shopID);
        dest.writeString(this.shopName);
        dest.writeString(this.subBillID);
        dest.writeString(this.subBillNo);
        dest.writeString(this.supplyShopID);
        dest.writeString(this.supplyShopName);
        dest.writeInt(this.afterSalesType);
    }

    public AfterSalesApplyParam() {
    }

    protected AfterSalesApplyParam(Parcel in) {
        this.afterSalesDetailList = in.createTypedArrayList(AfterSalesDetailsBean.CREATOR);
        this.orderDetailList = in.createTypedArrayList(OrderDetailBean.CREATOR);
        this.groupID = in.readString();
        this.groupName = in.readString();
        this.id = in.readString();
        this.payType = in.readInt();
        this.paymentWay = in.readInt();
        this.purchaserID = in.readString();
        this.purchaserName = in.readString();
        this.refundBillNo = in.readString();
        this.refundBillType = in.readInt();
        this.explain = in.readString();
        this.reason = in.readString();
        this.voucher = in.readString();
        this.reasonDesc = in.readString();
        this.shopID = in.readString();
        this.shopName = in.readString();
        this.subBillID = in.readString();
        this.subBillNo = in.readString();
        this.supplyShopID = in.readString();
        this.supplyShopName = in.readString();
        this.afterSalesType = in.readInt();
    }

    public static final Parcelable.Creator<AfterSalesApplyParam> CREATOR = new Parcelable.Creator<AfterSalesApplyParam>() {
        @Override
        public AfterSalesApplyParam createFromParcel(Parcel source) {
            return new AfterSalesApplyParam(source);
        }

        @Override
        public AfterSalesApplyParam[] newArray(int size) {
            return new AfterSalesApplyParam[size];
        }
    };
}
