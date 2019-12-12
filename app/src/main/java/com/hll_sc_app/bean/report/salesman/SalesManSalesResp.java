package com.hll_sc_app.bean.report.salesman;

import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务员销售额响应列表
 */
public class SalesManSalesResp {

    private double totalRefundAmount;
    private double totalSalesAmount;
    private double totalSettleAmount;
    private int totalSettleBillNum;
    private int totalValidBillNum;
    private int totalSize;

    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add("合计");
        list.add(String.valueOf(totalSize)); // 数量
        list.add(String.valueOf(totalValidBillNum)); // 有效订单数
        list.add(CommonUtils.formatMoney(totalSalesAmount)); // 销售金额
        list.add(String.valueOf(totalSettleBillNum)); // 结算订单数
        list.add(CommonUtils.formatMoney(totalSettleAmount)); // 结算金额
        list.add(CommonUtils.formatMoney(totalRefundAmount)); // 退款金额
        return list;
    }

    private List<SalesManSalesAchievement> records;

    public double getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(double totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }

    public double getTotalSalesAmount() {
        return totalSalesAmount;
    }

    public void setTotalSalesAmount(double totalSalesAmount) {
        this.totalSalesAmount = totalSalesAmount;
    }

    public double getTotalSettleAmount() {
        return totalSettleAmount;
    }

    public void setTotalSettleAmount(double totalSettleAmount) {
        this.totalSettleAmount = totalSettleAmount;
    }

    public int getTotalSettleBillNum() {
        return totalSettleBillNum;
    }

    public void setTotalSettleBillNum(int totalSettleBillNum) {
        this.totalSettleBillNum = totalSettleBillNum;
    }

    public int getTotalValidBillNum() {
        return totalValidBillNum;
    }

    public void setTotalValidBillNum(int totalValidBillNum) {
        this.totalValidBillNum = totalValidBillNum;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<SalesManSalesAchievement> getRecords() {
        return records;
    }

    public void setRecords(List<SalesManSalesAchievement> records) {
        this.records = records;
    }
}
