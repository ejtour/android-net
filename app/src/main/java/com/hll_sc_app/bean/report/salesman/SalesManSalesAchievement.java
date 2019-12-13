package com.hll_sc_app.bean.report.salesman;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务员销售额绩效
 */
public class SalesManSalesAchievement implements IStringArrayGenerator {
    private String salesmanCode;
    private String salesmanName;
    private double salesAmount;
    private double refundAmount;
    private double settleAmount;
    private int settleBillNum;
    private int validOrderNum;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(salesmanCode); // 编码
        list.add(salesmanName); // 姓名
        list.add(CommonUtils.formatNumber(validOrderNum)); // 有效订单数
        list.add(CommonUtils.formatMoney(salesAmount)); // 销售金额
        list.add(CommonUtils.formatNumber(settleBillNum)); // 结算订单数
        list.add(CommonUtils.formatMoney(settleAmount)); // 结算金额
        list.add(CommonUtils.formatMoney(refundAmount)); // 退款金额
        return list;
    }

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
