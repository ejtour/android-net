package com.hll_sc_app.bean.report.deliveryLack;

import java.util.List;

/**
 * 缺货汇总响应列表
 */
public class DeliveryLackGatherResp {

    List<DeliveryLackGather> records;
    private double totalDeliveryLackAmount;
    private int totalDeliveryLackNum;
    private String totalDeliveryLackRate;
    private int totalDeliveryLackShopNum;
    private int totalDeliveryOrderNum;
    private double totalDeliveryTradeAmount;
    private int totalDeliveryLackKindNum;
    private double totalOriReserveTotalAmount;
    private int totalSize;

    public double getTotalDeliveryLackAmount() {
        return totalDeliveryLackAmount;
    }

    public void setTotalDeliveryLackAmount(double totalDeliveryLackAmount) {
        this.totalDeliveryLackAmount = totalDeliveryLackAmount;
    }

    public int getTotalDeliveryLackNum() {
        return totalDeliveryLackNum;
    }

    public void setTotalDeliveryLackNum(int totalDeliveryLackNum) {
        this.totalDeliveryLackNum = totalDeliveryLackNum;
    }

    public String getTotalDeliveryLackRate() {
        return totalDeliveryLackRate;
    }

    public void setTotalDeliveryLackRate(String totalDeliveryLackRate) {
        this.totalDeliveryLackRate = totalDeliveryLackRate;
    }

    public int getTotalDeliveryLackShopNum() {
        return totalDeliveryLackShopNum;
    }

    public void setTotalDeliveryLackShopNum(int totalDeliveryLackShopNum) {
        this.totalDeliveryLackShopNum = totalDeliveryLackShopNum;
    }

    public int getTotalDeliveryOrderNum() {
        return totalDeliveryOrderNum;
    }

    public void setTotalDeliveryOrderNum(int totalDeliveryOrderNum) {
        this.totalDeliveryOrderNum = totalDeliveryOrderNum;
    }

    public double getTotalDeliveryTradeAmount() {
        return totalDeliveryTradeAmount;
    }

    public void setTotalDeliveryTradeAmount(double totalDeliveryTradeAmount) {
        this.totalDeliveryTradeAmount = totalDeliveryTradeAmount;
    }

    public double getTotalOriReserveTotalAmount() {
        return totalOriReserveTotalAmount;
    }

    public void setTotalOriReserveTotalAmount(double totalOriReserveTotalAmount) {
        this.totalOriReserveTotalAmount = totalOriReserveTotalAmount;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<DeliveryLackGather> getRecords() {
        return records;
    }

    public void setRecords(List<DeliveryLackGather> records) {
        this.records = records;
    }

    public int getTotalDeliveryLackKindNum() {
        return totalDeliveryLackKindNum;
    }

    public void setTotalDeliveryLackKindNum(int totalDeliveryLackKindNum) {
        this.totalDeliveryLackKindNum = totalDeliveryLackKindNum;
    }
}
