package com.hll_sc_app.bean.order.place;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/24
 */

public class OrderCommitBean {
    private double amount;
    private int payType;
    private String shopID;
    private String shopName;
    private int subbillNum;
    private List<SubBillBean> list;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
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

    public int getSubbillNum() {
        return subbillNum;
    }

    public void setSubbillNum(int subbillNum) {
        this.subbillNum = subbillNum;
    }

    public List<SubBillBean> getList() {
        return list;
    }

    public void setList(List<SubBillBean> list) {
        this.list = list;
    }

    public static class SubBillBean {
        private String groupID;
        private String groupName;
        private int payType;
        private int nextDayDelivery;
        private int payee;
        private int deliverType;
        private String shopID;
        private String shopName;
        private String subBillNo;
        private String subBillID;
        private String supplyShopID;
        private String supplyShopName;
        private double totalAmount;

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

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public int getNextDayDelivery() {
            return nextDayDelivery;
        }

        public void setNextDayDelivery(int nextDayDelivery) {
            this.nextDayDelivery = nextDayDelivery;
        }

        public int getPayee() {
            return payee;
        }

        public void setPayee(int payee) {
            this.payee = payee;
        }

        public int getDeliverType() {
            return deliverType;
        }

        public void setDeliverType(int deliverType) {
            this.deliverType = deliverType;
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

        public String getSubBillID() {
            return subBillID;
        }

        public void setSubBillID(String subBillID) {
            this.subBillID = subBillID;
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
    }
}
