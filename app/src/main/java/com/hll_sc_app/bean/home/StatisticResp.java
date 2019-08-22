package com.hll_sc_app.bean.home;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/20
 */

public class StatisticResp {
    private double amount;
    private int shopNum;
    private int needSettlementBillNum;
    private int financeReviewRefundNum;
    private int wareHousedRefundNum;
    private int billNum;
    private int customServicedRefundNum;
    private int settlementDateBillNum;
    private int settlementBillNum;
    private double settlementDateUnsettleAmount;
    private int drivedRefundNum;
    private int needSettlementShopNum;
    private double settlementAmount;
    private int settlementShopNum;
    private double needSettlementAmount;
    private int settlementDateShopNum;
    private int wareHouseBillNum;
    private double wareHouseDeliveryGoodsAmount;
    private int wareHouseShopNum;
    private boolean wareHouse;
    private List<TrendBean> orders;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getShopNum() {
        return shopNum;
    }

    public void setShopNum(int shopNum) {
        this.shopNum = shopNum;
    }

    public int getNeedSettlementBillNum() {
        return needSettlementBillNum;
    }

    public void setNeedSettlementBillNum(int needSettlementBillNum) {
        this.needSettlementBillNum = needSettlementBillNum;
    }

    public int getFinanceReviewRefundNum() {
        return financeReviewRefundNum;
    }

    public void setFinanceReviewRefundNum(int financeReviewRefundNum) {
        this.financeReviewRefundNum = financeReviewRefundNum;
    }

    public int getWareHousedRefundNum() {
        return wareHousedRefundNum;
    }

    public void setWareHousedRefundNum(int wareHousedRefundNum) {
        this.wareHousedRefundNum = wareHousedRefundNum;
    }

    public int getBillNum() {
        return billNum;
    }

    public void setBillNum(int billNum) {
        this.billNum = billNum;
    }

    public int getCustomServicedRefundNum() {
        return customServicedRefundNum;
    }

    public void setCustomServicedRefundNum(int customServicedRefundNum) {
        this.customServicedRefundNum = customServicedRefundNum;
    }

    public int getSettlementDateBillNum() {
        return settlementDateBillNum;
    }

    public void setSettlementDateBillNum(int settlementDateBillNum) {
        this.settlementDateBillNum = settlementDateBillNum;
    }

    public int getSettlementBillNum() {
        return settlementBillNum;
    }

    public void setSettlementBillNum(int settlementBillNum) {
        this.settlementBillNum = settlementBillNum;
    }

    public double getSettlementDateUnsettleAmount() {
        return settlementDateUnsettleAmount;
    }

    public void setSettlementDateUnsettleAmount(double settlementDateUnsettleAmount) {
        this.settlementDateUnsettleAmount = settlementDateUnsettleAmount;
    }

    public int getDrivedRefundNum() {
        return drivedRefundNum;
    }

    public void setDrivedRefundNum(int drivedRefundNum) {
        this.drivedRefundNum = drivedRefundNum;
    }

    public int getNeedSettlementShopNum() {
        return needSettlementShopNum;
    }

    public void setNeedSettlementShopNum(int needSettlementShopNum) {
        this.needSettlementShopNum = needSettlementShopNum;
    }

    public double getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(double settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public int getSettlementShopNum() {
        return settlementShopNum;
    }

    public void setSettlementShopNum(int settlementShopNum) {
        this.settlementShopNum = settlementShopNum;
    }

    public double getNeedSettlementAmount() {
        return needSettlementAmount;
    }

    public void setNeedSettlementAmount(double needSettlementAmount) {
        this.needSettlementAmount = needSettlementAmount;
    }

    public int getSettlementDateShopNum() {
        return settlementDateShopNum;
    }

    public void setSettlementDateShopNum(int settlementDateShopNum) {
        this.settlementDateShopNum = settlementDateShopNum;
    }

    public int getWareHouseBillNum() {
        return wareHouseBillNum;
    }

    public void setWareHouseBillNum(int wareHouseBillNum) {
        this.wareHouseBillNum = wareHouseBillNum;
    }

    public double getWareHouseDeliveryGoodsAmount() {
        return wareHouseDeliveryGoodsAmount;
    }

    public void setWareHouseDeliveryGoodsAmount(double wareHouseDeliveryGoodsAmount) {
        this.wareHouseDeliveryGoodsAmount = wareHouseDeliveryGoodsAmount;
    }

    public int getWareHouseShopNum() {
        return wareHouseShopNum;
    }

    public void setWareHouseShopNum(int wareHouseShopNum) {
        this.wareHouseShopNum = wareHouseShopNum;
    }

    public boolean isWareHouse() {
        return wareHouse;
    }

    public void setWareHouse(boolean wareHouse) {
        this.wareHouse = wareHouse;
    }

    public List<TrendBean> getOrders() {
        return orders;
    }

    public void setOrders(List<TrendBean> orders) {
        this.orders = orders;
    }
}
