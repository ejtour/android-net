package com.hll_sc_app.bean.report.refund;

import android.text.TextUtils;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;

import java.util.ArrayList;
import java.util.List;

public class RefundProductBean implements IStringArrayGenerator {

    /**
     * 商品编码
     */
    private String productCode;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品规格
     */
    private String productSpec;
    /**
     * 退款
     */
    private double refundAmount;
    /**
     * 数量
     */
    private double refundProductNum;
    /**
     * 单位
     */
    private String refundUnit;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(productCode);// 商品编号
        list.add(productName); // 商品名称
        list.add(TextUtils.isEmpty(productSpec) ? "- -" : productSpec); // 规格
        list.add(TextUtils.isEmpty(refundUnit) ? "- -" : refundUnit); //单位
        list.add(CommonUtils.formatNumber(refundProductNum)); // 数量
        list.add(CommonUtils.formatMoney(refundAmount)); //退款金额
        return list;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public double getRefundProductNum() {
        return refundProductNum;
    }

    public void setRefundProductNum(double refundProductNum) {
        this.refundProductNum = refundProductNum;
    }

    public String getRefundUnit() {
        return refundUnit;
    }

    public void setRefundUnit(String refundUnit) {
        this.refundUnit = refundUnit;
    }
}
