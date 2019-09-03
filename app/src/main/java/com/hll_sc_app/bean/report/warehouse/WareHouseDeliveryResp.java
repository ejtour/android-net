package com.hll_sc_app.bean.report.warehouse;

import java.util.List;

public class WareHouseDeliveryResp {
    /**
     * 总发货金额
     */
    private String totalDeliveryAmount;
    /**
     * 总发货单数
     */
    private long   totalDeliveryBillNum;
    /**
     * 总发货商品量
     */
    private String totalDeliveryGoodsNum;
    /**
     * 总发货商品数
     */
    private String totalDeliveryGoodsSpecNum;
    /**
     * 总条数
     */
    private int    totalSize;
    private String totalWareHouseDeliveryGoodsAmount;

    private List<WareHouseDeliveryItem> records;


    public String getTotalDeliveryAmount() {
        return totalDeliveryAmount;
    }

    public void setTotalDeliveryAmount(String totalDeliveryAmount) {
        this.totalDeliveryAmount = totalDeliveryAmount;
    }

    public long getTotalDeliveryBillNum() {
        return totalDeliveryBillNum;
    }

    public void setTotalDeliveryBillNum(long totalDeliveryBillNum) {
        this.totalDeliveryBillNum = totalDeliveryBillNum;
    }

    public String getTotalDeliveryGoodsNum() {
        return totalDeliveryGoodsNum;
    }

    public void setTotalDeliveryGoodsNum(String totalDeliveryGoodsNum) {
        this.totalDeliveryGoodsNum = totalDeliveryGoodsNum;
    }

    public String getTotalDeliveryGoodsSpecNum() {
        return totalDeliveryGoodsSpecNum;
    }

    public void setTotalDeliveryGoodsSpecNum(String totalDeliveryGoodsSpecNum) {
        this.totalDeliveryGoodsSpecNum = totalDeliveryGoodsSpecNum;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public String getTotalWareHouseDeliveryGoodsAmount() {
        return totalWareHouseDeliveryGoodsAmount;
    }

    public void setTotalWareHouseDeliveryGoodsAmount(String totalWareHouseDeliveryGoodsAmount) {
        this.totalWareHouseDeliveryGoodsAmount = totalWareHouseDeliveryGoodsAmount;
    }

    public List<WareHouseDeliveryItem> getRecords() {
        return records;
    }

    public void setRecords(List<WareHouseDeliveryItem> records) {
        this.records = records;
    }
}
