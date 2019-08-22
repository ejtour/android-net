package com.hll_sc_app.bean.report.deliveryTime;

import java.util.List;

public class DeliveryTimeResp {

    private DeliveryTimeNearlyItem nearly7Days;
    private DeliveryTimeNearlyItem nearly30Days;
    private DeliveryTimeNearlyItem nearly90Days;
    private List<DeliveryTimeItem> records;
    private long     totalBeyond30MinInspectionNum;
    private String   totalBeyond30MinInspectionRate;
    private long     totalDeliveryOrderNum;
    private long     totalExecuteOrderNum;
    private long     totalInspectionOrderNum;
    private long     totalOnTimeInspectionNum;
    private String   totalOnTimeInspectionRate;
    private long     totalWithin15MinInspectionNum;
    private String   totalWithin15MinInspectionRate;
    private long     totalWithin30MinInspectionNum;
    private String   totalWithin30MinInspectionRate;
    private int      totalSize;


    public DeliveryTimeNearlyItem getNearly7Days() {
        return nearly7Days;
    }

    public void setNearly7Days(DeliveryTimeNearlyItem nearly7Days) {
        this.nearly7Days = nearly7Days;
    }

    public DeliveryTimeNearlyItem getNearly30Days() {
        return nearly30Days;
    }

    public void setNearly30Days(DeliveryTimeNearlyItem nearly30Days) {
        this.nearly30Days = nearly30Days;
    }

    public DeliveryTimeNearlyItem getNearly90Days() {
        return nearly90Days;
    }

    public void setNearly90Days(DeliveryTimeNearlyItem nearly90Days) {
        this.nearly90Days = nearly90Days;
    }

    public List<DeliveryTimeItem> getRecords() {
        return records;
    }

    public void setRecords(List<DeliveryTimeItem> records) {
        this.records = records;
    }

    public long getTotalBeyond30MinInspectionNum() {
        return totalBeyond30MinInspectionNum;
    }

    public void setTotalBeyond30MinInspectionNum(long totalBeyond30MinInspectionNum) {
        this.totalBeyond30MinInspectionNum = totalBeyond30MinInspectionNum;
    }

    public String getTotalBeyond30MinInspectionRate() {
        return totalBeyond30MinInspectionRate;
    }

    public void setTotalBeyond30MinInspectionRate(String totalBeyond30MinInspectionRate) {
        this.totalBeyond30MinInspectionRate = totalBeyond30MinInspectionRate;
    }

    public long getTotalDeliveryOrderNum() {
        return totalDeliveryOrderNum;
    }

    public void setTotalDeliveryOrderNum(long totalDeliveryOrderNum) {
        this.totalDeliveryOrderNum = totalDeliveryOrderNum;
    }

    public long getTotalExecuteOrderNum() {
        return totalExecuteOrderNum;
    }

    public void setTotalExecuteOrderNum(long totalExecuteOrderNum) {
        this.totalExecuteOrderNum = totalExecuteOrderNum;
    }

    public long getTotalInspectionOrderNum() {
        return totalInspectionOrderNum;
    }

    public void setTotalInspectionOrderNum(long totalInspectionOrderNum) {
        this.totalInspectionOrderNum = totalInspectionOrderNum;
    }

    public long getTotalOnTimeInspectionNum() {
        return totalOnTimeInspectionNum;
    }

    public void setTotalOnTimeInspectionNum(long totalOnTimeInspectionNum) {
        this.totalOnTimeInspectionNum = totalOnTimeInspectionNum;
    }

    public String getTotalOnTimeInspectionRate() {
        return totalOnTimeInspectionRate;
    }

    public void setTotalOnTimeInspectionRate(String totalOnTimeInspectionRate) {
        this.totalOnTimeInspectionRate = totalOnTimeInspectionRate;
    }

    public long getTotalWithin15MinInspectionNum() {
        return totalWithin15MinInspectionNum;
    }

    public void setTotalWithin15MinInspectionNum(long totalWithin15MinInspectionNum) {
        this.totalWithin15MinInspectionNum = totalWithin15MinInspectionNum;
    }

    public String getTotalWithin15MinInspectionRate() {
        return totalWithin15MinInspectionRate;
    }

    public void setTotalWithin15MinInspectionRate(String totalWithin15MinInspectionRate) {
        this.totalWithin15MinInspectionRate = totalWithin15MinInspectionRate;
    }

    public long getTotalWithin30MinInspectionNum() {
        return totalWithin30MinInspectionNum;
    }

    public void setTotalWithin30MinInspectionNum(long totalWithin30MinInspectionNum) {
        this.totalWithin30MinInspectionNum = totalWithin30MinInspectionNum;
    }

    public String getTotalWithin30MinInspectionRate() {
        return totalWithin30MinInspectionRate;
    }

    public void setTotalWithin30MinInspectionRate(String totalWithin30MinInspectionRate) {
        this.totalWithin30MinInspectionRate = totalWithin30MinInspectionRate;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }
}
