package com.hll_sc_app.bean.report.salesman;

/**
 * 业务员销售额绩效
 */
public class SalesManSalesAchievement {
    private String salesmanCode;
    private String salesmanName;
    private double salesAmount;
    private double refundAmount;
    private double settleAmount;
    private int settleBillNum;
    private int validOrderNum;

    public String getSalesmanCode() {
        return salesmanCode;
    }

    public void setSalesmanCode(String salesmanCode) {
        this.salesmanCode = salesmanCode;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public double getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(double salesAmount) {
        this.salesAmount = salesAmount;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public double getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(double settleAmount) {
        this.settleAmount = settleAmount;
    }

    public int getSettleBillNum() {
        return settleBillNum;
    }

    public void setSettleBillNum(int settleBillNum) {
        this.settleBillNum = settleBillNum;
    }

    public int getValidOrderNum() {
        return validOrderNum;
    }

    public void setValidOrderNum(int validOrderNum) {
        this.validOrderNum = validOrderNum;
    }
}
