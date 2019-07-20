package com.hll_sc_app.bean.report.resp.bill;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户销售/门店汇总查询响应列表
 */
public class CustomerSalesResp {

    private double totalAmount;
    private double totalAverageAmount;
    private int    totalCooperationShopNum;
    private int    totalOrderCustomerNum;
    private int    totalOrderCustomerShopNum;
    private long   totalOrderNum;
    private double totalRefundAmount;
    private long   totalRefundBillNum;
    private long   totalRefundCustomerNum;
    private int    totalRefundCustomerShopNum;
    private double totalSalesAmount;
    private int    totalSize;
    private long   totalValidBillNum;

    private List<CustomerSalesRecords> records = new ArrayList<>();

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalAverageAmount() {
        return totalAverageAmount;
    }

    public void setTotalAverageAmount(double totalAverageAmount) {
        this.totalAverageAmount = totalAverageAmount;
    }

    public int getTotalCooperationShopNum() {
        return totalCooperationShopNum;
    }

    public void setTotalCooperationShopNum(int totalCooperationShopNum) {
        this.totalCooperationShopNum = totalCooperationShopNum;
    }

    public int getTotalOrderCustomerNum() {
        return totalOrderCustomerNum;
    }

    public void setTotalOrderCustomerNum(int totalOrderCustomerNum) {
        this.totalOrderCustomerNum = totalOrderCustomerNum;
    }

    public int getTotalOrderCustomerShopNum() {
        return totalOrderCustomerShopNum;
    }

    public void setTotalOrderCustomerShopNum(int totalOrderCustomerShopNum) {
        this.totalOrderCustomerShopNum = totalOrderCustomerShopNum;
    }

    public long getTotalOrderNum() {
        return totalOrderNum;
    }

    public void setTotalOrderNum(long totalOrderNum) {
        this.totalOrderNum = totalOrderNum;
    }

    public double getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(double totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }

    public long getTotalRefundBillNum() {
        return totalRefundBillNum;
    }

    public void setTotalRefundBillNum(long totalRefundBillNum) {
        this.totalRefundBillNum = totalRefundBillNum;
    }

    public long getTotalRefundCustomerNum() {
        return totalRefundCustomerNum;
    }

    public void setTotalRefundCustomerNum(long totalRefundCustomerNum) {
        this.totalRefundCustomerNum = totalRefundCustomerNum;
    }

    public int getTotalRefundCustomerShopNum() {
        return totalRefundCustomerShopNum;
    }

    public void setTotalRefundCustomerShopNum(int totalRefundCustomerShopNum) {
        this.totalRefundCustomerShopNum = totalRefundCustomerShopNum;
    }

    public double getTotalSalesAmount() {
        return totalSalesAmount;
    }

    public void setTotalSalesAmount(double totalSalesAmount) {
        this.totalSalesAmount = totalSalesAmount;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public long getTotalValidBillNum() {
        return totalValidBillNum;
    }

    public void setTotalValidBillNum(long totalValidBillNum) {
        this.totalValidBillNum = totalValidBillNum;
    }

    public List<CustomerSalesRecords> getRecords() {
        return records;
    }

    public void setRecords(List<CustomerSalesRecords> records) {
        this.records = records;
    }
}
