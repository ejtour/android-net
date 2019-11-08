package com.hll_sc_app.bean.cardmanage;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CardTransactionListResp {
    private List<CardDealDetail> records;
    private int total;

    public List<CardDealDetail> getRecords() {
        return records;
    }

    public void setRecords(List<CardDealDetail> records) {
        this.records = records;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static class CardDealDetail implements Parcelable {
        private double balance;
        private double cashBalance;
        private double giftBalance;
        private double tradeAmount;
        private double tradeCashAmount;
        private double tradeGiftAmount;
        private String groupID;
        private String groupName;
        private String purchaserID;
        private String purchaserName;
        private String cardNo;
        private String remark;
        private String refundBillNo;
        private String shopID;
        private String shopName;
        private String subBillNo;
        private String supplierShopID;
        private String supplierShopName;
        private String tradeBy;
        private String tradeNo;
        private String tradeTime;
        private int tradeType;


        public String getStradeType() {
            if (tradeType == 1) {
                return "充值";
            } else if (tradeType == 2) {
                return "消费";
            } else if (tradeType == 3) {
                return "消费退款";
            } else {
                return "";
            }
        }


        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public double getCashBalance() {
            return cashBalance;
        }

        public void setCashBalance(double cashBalance) {
            this.cashBalance = cashBalance;
        }

        public double getGiftBalance() {
            return giftBalance;
        }

        public void setGiftBalance(double giftBalance) {
            this.giftBalance = giftBalance;
        }

        public double getTradeAmount() {
            return tradeAmount;
        }

        public void setTradeAmount(double tradeAmount) {
            this.tradeAmount = tradeAmount;
        }

        public double getTradeCashAmount() {
            return tradeCashAmount;
        }

        public void setTradeCashAmount(double tradeCashAmount) {
            this.tradeCashAmount = tradeCashAmount;
        }

        public double getTradeGiftAmount() {
            return tradeGiftAmount;
        }

        public void setTradeGiftAmount(double tradeGiftAmount) {
            this.tradeGiftAmount = tradeGiftAmount;
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

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRefundBillNo() {
            return refundBillNo;
        }

        public void setRefundBillNo(String refundBillNo) {
            this.refundBillNo = refundBillNo;
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

        public String getSubBillNo() {
            return subBillNo;
        }

        public void setSubBillNo(String subBillNo) {
            this.subBillNo = subBillNo;
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

        public String getTradeBy() {
            return tradeBy;
        }

        public void setTradeBy(String tradeBy) {
            this.tradeBy = tradeBy;
        }

        public String getTradeNo() {
            return tradeNo;
        }

        public void setTradeNo(String tradeNo) {
            this.tradeNo = tradeNo;
        }

        public String getTradeTime() {
            return tradeTime;
        }

        public void setTradeTime(String tradeTime) {
            this.tradeTime = tradeTime;
        }

        public int getTradeType() {
            return tradeType;
        }

        public void setTradeType(int tradeType) {
            this.tradeType = tradeType;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(this.balance);
            dest.writeDouble(this.cashBalance);
            dest.writeDouble(this.giftBalance);
            dest.writeDouble(this.tradeAmount);
            dest.writeDouble(this.tradeCashAmount);
            dest.writeDouble(this.tradeGiftAmount);
            dest.writeString(this.groupID);
            dest.writeString(this.groupName);
            dest.writeString(this.purchaserID);
            dest.writeString(this.purchaserName);
            dest.writeString(this.cardNo);
            dest.writeString(this.remark);
            dest.writeString(this.refundBillNo);
            dest.writeString(this.shopID);
            dest.writeString(this.shopName);
            dest.writeString(this.subBillNo);
            dest.writeString(this.supplierShopID);
            dest.writeString(this.supplierShopName);
            dest.writeString(this.tradeBy);
            dest.writeString(this.tradeNo);
            dest.writeString(this.tradeTime);
            dest.writeInt(this.tradeType);
        }

        public CardDealDetail() {
        }

        protected CardDealDetail(Parcel in) {
            this.balance = in.readDouble();
            this.cashBalance = in.readDouble();
            this.giftBalance = in.readDouble();
            this.tradeAmount = in.readDouble();
            this.tradeCashAmount = in.readDouble();
            this.tradeGiftAmount = in.readDouble();
            this.groupID = in.readString();
            this.groupName = in.readString();
            this.purchaserID = in.readString();
            this.purchaserName = in.readString();
            this.cardNo = in.readString();
            this.remark = in.readString();
            this.refundBillNo = in.readString();
            this.shopID = in.readString();
            this.shopName = in.readString();
            this.subBillNo = in.readString();
            this.supplierShopID = in.readString();
            this.supplierShopName = in.readString();
            this.tradeBy = in.readString();
            this.tradeNo = in.readString();
            this.tradeTime = in.readString();
            this.tradeType = in.readInt();
        }

        public static final Creator<CardDealDetail> CREATOR = new Creator<CardDealDetail>() {
            @Override
            public CardDealDetail createFromParcel(Parcel source) {
                return new CardDealDetail(source);
            }

            @Override
            public CardDealDetail[] newArray(int size) {
                return new CardDealDetail[size];
            }
        };
    }
}
