package com.hll_sc_app.bean.report.deliverytime;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class DeliveryTimeResp {
    private DeliveryTimeNearlyBean nearly7Days;
    private DeliveryTimeNearlyBean nearly30Days;
    private DeliveryTimeNearlyBean nearly90Days;
    private List<DeliveryTimeItem> records;
    private int totalBeyond30MinInspectionNum;
    private double totalBeyond30MinInspectionRate;
    private int totalDeliveryOrderNum;
    private int totalExecuteOrderNum;
    private int totalInspectionOrderNum;
    private int totalOnTimeInspectionNum;
    private double totalOnTimeInspectionRate;
    private int totalWithin15MinInspectionNum;
    private double totalWithin15MinInspectionRate;
    private int totalWithin30MinInspectionNum;
    private double totalWithin30MinInspectionRate;
    private int totalSize;

    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add("合计"); // 日期
        list.add(CommonUtils.formatNumber(totalExecuteOrderNum)); // 要求到货单量
        list.add(CommonUtils.formatNumber(totalDeliveryOrderNum)); // 发货单量
        list.add(CommonUtils.formatNumber(totalInspectionOrderNum)); // 签收单量
        list.add(CommonUtils.formatNumber(totalOnTimeInspectionNum)); // 按时单量
        list.add(-2 == totalOnTimeInspectionRate ? "- -" : Utils.numToPercent(totalOnTimeInspectionRate)); // 按时占比
        list.add(CommonUtils.formatNumber(totalWithin15MinInspectionNum)); // 15分内单量
        list.add(-2 == totalWithin15MinInspectionRate ? "- -" : Utils.numToPercent(totalWithin15MinInspectionRate)); // 15分内占比
        list.add(CommonUtils.formatNumber(totalWithin30MinInspectionNum)); // 30分内单量
        list.add(-2 == totalWithin30MinInspectionRate ? "- -" : Utils.numToPercent(totalWithin30MinInspectionRate)); // 30分内占比
        list.add(CommonUtils.formatNumber(totalBeyond30MinInspectionNum)); // 30分以上单量
        list.add(-2 == totalBeyond30MinInspectionRate ? "- -" : Utils.numToPercent(totalBeyond30MinInspectionRate)); // 30分以上占比
        return list;
    }


    public DeliveryTimeNearlyBean getNearly7Days() {
        return nearly7Days;
    }

    public void setNearly7Days(DeliveryTimeNearlyBean nearly7Days) {
        this.nearly7Days = nearly7Days;
    }

    public DeliveryTimeNearlyBean getNearly30Days() {
        return nearly30Days;
    }

    public void setNearly30Days(DeliveryTimeNearlyBean nearly30Days) {
        this.nearly30Days = nearly30Days;
    }

    public DeliveryTimeNearlyBean getNearly90Days() {
        return nearly90Days;
    }

    public void setNearly90Days(DeliveryTimeNearlyBean nearly90Days) {
        this.nearly90Days = nearly90Days;
    }

    public List<DeliveryTimeItem> getRecords() {
        return records;
    }

    public void setRecords(List<DeliveryTimeItem> records) {
        this.records = records;
    }

    public int getTotalBeyond30MinInspectionNum() {
        return totalBeyond30MinInspectionNum;
    }

    public void setTotalBeyond30MinInspectionNum(int totalBeyond30MinInspectionNum) {
        this.totalBeyond30MinInspectionNum = totalBeyond30MinInspectionNum;
    }

    public double getTotalBeyond30MinInspectionRate() {
        return totalBeyond30MinInspectionRate;
    }

    public void setTotalBeyond30MinInspectionRate(double totalBeyond30MinInspectionRate) {
        this.totalBeyond30MinInspectionRate = totalBeyond30MinInspectionRate;
    }

    public int getTotalDeliveryOrderNum() {
        return totalDeliveryOrderNum;
    }

    public void setTotalDeliveryOrderNum(int totalDeliveryOrderNum) {
        this.totalDeliveryOrderNum = totalDeliveryOrderNum;
    }

    public int getTotalExecuteOrderNum() {
        return totalExecuteOrderNum;
    }

    public void setTotalExecuteOrderNum(int totalExecuteOrderNum) {
        this.totalExecuteOrderNum = totalExecuteOrderNum;
    }

    public int getTotalInspectionOrderNum() {
        return totalInspectionOrderNum;
    }

    public void setTotalInspectionOrderNum(int totalInspectionOrderNum) {
        this.totalInspectionOrderNum = totalInspectionOrderNum;
    }

    public int getTotalOnTimeInspectionNum() {
        return totalOnTimeInspectionNum;
    }

    public void setTotalOnTimeInspectionNum(int totalOnTimeInspectionNum) {
        this.totalOnTimeInspectionNum = totalOnTimeInspectionNum;
    }

    public double getTotalOnTimeInspectionRate() {
        return totalOnTimeInspectionRate;
    }

    public void setTotalOnTimeInspectionRate(double totalOnTimeInspectionRate) {
        this.totalOnTimeInspectionRate = totalOnTimeInspectionRate;
    }

    public int getTotalWithin15MinInspectionNum() {
        return totalWithin15MinInspectionNum;
    }

    public void setTotalWithin15MinInspectionNum(int totalWithin15MinInspectionNum) {
        this.totalWithin15MinInspectionNum = totalWithin15MinInspectionNum;
    }

    public double getTotalWithin15MinInspectionRate() {
        return totalWithin15MinInspectionRate;
    }

    public void setTotalWithin15MinInspectionRate(double totalWithin15MinInspectionRate) {
        this.totalWithin15MinInspectionRate = totalWithin15MinInspectionRate;
    }

    public int getTotalWithin30MinInspectionNum() {
        return totalWithin30MinInspectionNum;
    }

    public void setTotalWithin30MinInspectionNum(int totalWithin30MinInspectionNum) {
        this.totalWithin30MinInspectionNum = totalWithin30MinInspectionNum;
    }

    public double getTotalWithin30MinInspectionRate() {
        return totalWithin30MinInspectionRate;
    }

    public void setTotalWithin30MinInspectionRate(double totalWithin30MinInspectionRate) {
        this.totalWithin30MinInspectionRate = totalWithin30MinInspectionRate;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }
}
