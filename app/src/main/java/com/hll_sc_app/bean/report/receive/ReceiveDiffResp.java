package com.hll_sc_app.bean.report.receive;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ReceiveDiffResp {

    private double totalInspectionLackAmount;
    private double totalInspectionLackNum;
    private double totalInspectionLackRate;
    private double totalDeliveryOrderNum;
    private double totalDeliveryTradeAmount;
    private int totalInspectionOrderNum;
    private double totalInspectionTotalAmount;
    private int totalSize;
    private List<ReceiveDiffBean> records = new ArrayList<>();

    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add("合计");
        list.add(String.valueOf(totalDeliveryOrderNum)); // 发货单数
        list.add(CommonUtils.formatMoney(totalDeliveryTradeAmount)); // 发货金额
        list.add(String.valueOf(totalInspectionOrderNum)); // 收货单数
        list.add(CommonUtils.formatMoney(totalInspectionTotalAmount)); // 收货金额
        list.add("- -"); // 收货差异商品数
        list.add(String.valueOf(totalInspectionLackNum)); // 收货差异量
        list.add(CommonUtils.formatMoney(totalInspectionLackAmount)); // 收货差异金额
        list.add(-2 == totalInspectionLackRate ? "- -" : Utils.numToPercent(totalInspectionLackRate)); // 收货差异率
        return list;
    }

    public double getTotalInspectionLackAmount() {
        return totalInspectionLackAmount;
    }

    public void setTotalInspectionLackAmount(double totalInspectionLackAmount) {
        this.totalInspectionLackAmount = totalInspectionLackAmount;
    }

    public double getTotalInspectionLackNum() {
        return totalInspectionLackNum;
    }

    public void setTotalInspectionLackNum(double totalInspectionLackNum) {
        this.totalInspectionLackNum = totalInspectionLackNum;
    }

    public double getTotalInspectionLackRate() {
        return totalInspectionLackRate;
    }

    public void setTotalInspectionLackRate(double totalInspectionLackRate) {
        this.totalInspectionLackRate = totalInspectionLackRate;
    }

    public double getTotalDeliveryOrderNum() {
        return totalDeliveryOrderNum;
    }

    public void setTotalDeliveryOrderNum(double totalDeliveryOrderNum) {
        this.totalDeliveryOrderNum = totalDeliveryOrderNum;
    }

    public double getTotalDeliveryTradeAmount() {
        return totalDeliveryTradeAmount;
    }

    public void setTotalDeliveryTradeAmount(double totalDeliveryTradeAmount) {
        this.totalDeliveryTradeAmount = totalDeliveryTradeAmount;
    }

    public int getTotalInspectionOrderNum() {
        return totalInspectionOrderNum;
    }

    public void setTotalInspectionOrderNum(int totalInspectionOrderNum) {
        this.totalInspectionOrderNum = totalInspectionOrderNum;
    }

    public double getTotalInspectionTotalAmount() {
        return totalInspectionTotalAmount;
    }

    public void setTotalInspectionTotalAmount(double totalInspectionTotalAmount) {
        this.totalInspectionTotalAmount = totalInspectionTotalAmount;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<ReceiveDiffBean> getRecords() {
        return records;
    }

    public void setRecords(List<ReceiveDiffBean> records) {
        this.records = records;
    }
}
