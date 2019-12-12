package com.hll_sc_app.bean.report.resp.bill;

import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户销售/门店汇总查询响应列表
 */
public class CustomerSalesResp {
    private double totalAmount;
    private double totalAverageAmount;
    private int totalCooperationShopNum; // 后台说合作门店总数算不出来，后来用 - - 代替
    private int totalOrderCustomerNum;
    private int totalOrderCustomerShopNum;
    private long totalOrderNum;
    private double totalRefundAmount;
    private long totalRefundBillNum;
    private long totalRefundCustomerNum;
    private int totalRefundCustomerShopNum;
    private double totalSalesAmount;
    private int totalSize;
    private long totalValidBillNum;
    private List<CustomerSalesBean> records = new ArrayList<>();

    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add("合计");
        list.add("- -");
        list.add(String.valueOf(totalOrderNum)); // 订单数
        list.add(String.valueOf(totalValidBillNum)); // 有效订单数
        list.add(CommonUtils.formatMoney(totalSalesAmount)); // 交易金额
        list.add(CommonUtils.formatMoney(totalAverageAmount)); // 单均
        list.add(String.valueOf(totalRefundBillNum)); // 退单数
        list.add(CommonUtils.formatMoney(totalRefundAmount)); // 退货金额
        list.add(CommonUtils.formatMoney(totalAmount)); // 小计金额
        return list;
    }

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

    public List<CustomerSalesBean> getRecords() {
        return records;
    }

    public void setRecords(List<CustomerSalesBean> records) {
        this.records = records;
    }
}
