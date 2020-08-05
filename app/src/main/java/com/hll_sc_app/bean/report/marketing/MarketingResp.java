package com.hll_sc_app.bean.report.marketing;

import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/27
 */

public class MarketingResp {
    private int totalProductNum;
    private int totalShopNum;
    private int totalBillNum;
    private double totalAmount;
    private double totalActualAmount;
    private double totalDiscountAmount;
    private double totalDiscountRate;

    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add("合计");
        list.add("");
        list.add("- -");
        list.add("- -");
        list.add(CommonUtils.formatNum(totalBillNum));
        list.add("¥" + CommonUtils.formatMoney(totalAmount));
        list.add("¥" + CommonUtils.formatMoney(totalActualAmount));
        list.add("¥" + CommonUtils.formatMoney(totalDiscountAmount));
        list.add(CommonUtils.formatNum(totalDiscountRate) + "%");
        return list;
    }

    public int getTotalProductNum() {
        return totalProductNum;
    }

    public void setTotalProductNum(int totalProductNum) {
        this.totalProductNum = totalProductNum;
    }

    public int getTotalShopNum() {
        return totalShopNum;
    }

    public void setTotalShopNum(int totalShopNum) {
        this.totalShopNum = totalShopNum;
    }

    public int getTotalBillNum() {
        return totalBillNum;
    }

    public void setTotalBillNum(int totalBillNum) {
        this.totalBillNum = totalBillNum;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalActualAmount() {
        return totalActualAmount;
    }

    public void setTotalActualAmount(double totalActualAmount) {
        this.totalActualAmount = totalActualAmount;
    }

    public double getTotalDiscountAmount() {
        return totalDiscountAmount;
    }

    public void setTotalDiscountAmount(double totalDiscountAmount) {
        this.totalDiscountAmount = totalDiscountAmount;
    }

    public double getTotalDiscountRate() {
        return totalDiscountRate;
    }

    public void setTotalDiscountRate(double totalDiscountRate) {
        this.totalDiscountRate = totalDiscountRate;
    }

    private List<MarketingBean> list;

    public List<MarketingBean> getList() {
        return list;
    }

    public void setList(List<MarketingBean> list) {
        this.list = list;
    }
}
