package com.hll_sc_app.bean.report.lack;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 缺货汇总响应列表
 */
public class LackDiffResp {
    private List<LackDiffBean> records;
    private double totalDeliveryLackAmount;
    private int totalDeliveryLackNum;
    private double totalDeliveryLackRate;
    private int totalDeliveryLackShopNum;
    private int totalDeliveryOrderNum;
    private double totalDeliveryTradeAmount;
    private int totalDeliveryLackKindNum;
    private double totalOriReserveTotalAmount;
    private int totalSize;

    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add("合计");
        list.add(String.valueOf(totalDeliveryOrderNum)); // 发货量
        list.add(CommonUtils.formatMoney(totalDeliveryTradeAmount)); // 发货金额
        list.add(CommonUtils.formatMoney(totalOriReserveTotalAmount)); // 原订货金额
        list.add("- -"); // 缺货商品数
        list.add(String.valueOf(totalDeliveryLackNum)); // 缺货数量
        list.add(CommonUtils.formatMoney(totalDeliveryLackAmount)); // 缺货金额
        list.add(-2 == totalDeliveryLackRate ? "- -" : Utils.numToPercent(totalDeliveryLackRate)); // 缺货率
        list.add(String.valueOf(totalDeliveryLackShopNum)); // 影响门店
        return list;
    }

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

    public double getTotalDeliveryLackRate() {
        return totalDeliveryLackRate;
    }

    public void setTotalDeliveryLackRate(double totalDeliveryLackRate) {
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

    public List<LackDiffBean> getRecords() {
        return records;
    }

    public void setRecords(List<LackDiffBean> records) {
        this.records = records;
    }

    public int getTotalDeliveryLackKindNum() {
        return totalDeliveryLackKindNum;
    }

    public void setTotalDeliveryLackKindNum(int totalDeliveryLackKindNum) {
        this.totalDeliveryLackKindNum = totalDeliveryLackKindNum;
    }
}
