package com.hll_sc_app.bean.report.marketing;

import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/28
 */

public class MarketingDetailResp {
    private double totalAmount;
    private double totalActualAmount;
    private double totalDiscountAmount;
    private double totalSaleAmount;
    private double totalBillNum;
    private List<MarketingOrderBean> billList;
    private List<MarketingProductBean> productList;
    private List<MarketingShopBean> shopList;

    public List<CharSequence> convertToOrderRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add("合计");
        list.add("");
        list.add("");
        list.add("¥" + CommonUtils.formatMoney(totalAmount));
        list.add("¥" + CommonUtils.formatMoney(totalActualAmount));
        list.add("¥" + CommonUtils.formatMoney(totalDiscountAmount));
        return list;
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

    public double getTotalSaleAmount() {
        return totalSaleAmount;
    }

    public void setTotalSaleAmount(double totalSaleAmount) {
        this.totalSaleAmount = totalSaleAmount;
    }

    public double getTotalBillNum() {
        return totalBillNum;
    }

    public void setTotalBillNum(double totalBillNum) {
        this.totalBillNum = totalBillNum;
    }

    public List<MarketingOrderBean> getBillList() {
        return billList;
    }

    public void setBillList(List<MarketingOrderBean> billList) {
        this.billList = billList;
    }

    public List<MarketingProductBean> getProductList() {
        return productList;
    }

    public void setProductList(List<MarketingProductBean> productList) {
        this.productList = productList;
    }

    public List<MarketingShopBean> getShopList() {
        return shopList;
    }

    public void setShopList(List<MarketingShopBean> shopList) {
        this.shopList = shopList;
    }
}
