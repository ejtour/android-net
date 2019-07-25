package com.hll_sc_app.bean.report.orderGoods;

import android.text.TextUtils;

import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/23
 */

public class OrderGoodsDetailBean {

    private double inspectionNum;
    private double inspectionAmount;
    private double orderAmount;
    private String productCode;
    private double orderNum;
    private String orderUnit;
    private String productSpec;
    private int grossProfit;
    private int grossProfitRate;
    private String productName;
    private String inspectionUnit;

    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(productCode); // 商品编码
        list.add(productName); // 商品名称
        list.add(TextUtils.isEmpty(productSpec) ? "—" : productSpec); // 规格
        list.add(String.format("%s/%s", CommonUtils.formatNumber(orderNum), orderUnit)); // 订货数量/单位
        list.add(CommonUtils.formatMoney(orderAmount)); // 订货金额(元)
        list.add(String.format("%s/%s", CommonUtils.formatNumber(inspectionNum), inspectionUnit)); // 验货数量/单位
        list.add(CommonUtils.formatMoney(inspectionAmount)); // 验货金额(元)
        return list;
    }

    public double getInspectionNum() {
        return inspectionNum;
    }

    public void setInspectionNum(double inspectionNum) {
        this.inspectionNum = inspectionNum;
    }

    public double getInspectionAmount() {
        return inspectionAmount;
    }

    public void setInspectionAmount(double inspectionAmount) {
        this.inspectionAmount = inspectionAmount;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public double getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(double orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderUnit() {
        return orderUnit;
    }

    public void setOrderUnit(String orderUnit) {
        this.orderUnit = orderUnit;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public int getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(int grossProfit) {
        this.grossProfit = grossProfit;
    }

    public int getGrossProfitRate() {
        return grossProfitRate;
    }

    public void setGrossProfitRate(int grossProfitRate) {
        this.grossProfitRate = grossProfitRate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getInspectionUnit() {
        return inspectionUnit;
    }

    public void setInspectionUnit(String inspectionUnit) {
        this.inspectionUnit = inspectionUnit;
    }
}
