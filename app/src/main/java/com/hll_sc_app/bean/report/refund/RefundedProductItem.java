package com.hll_sc_app.bean.report.refund;

import com.alibaba.sdk.android.ams.common.util.StringUtil;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;

import java.util.ArrayList;
import java.util.List;

public class RefundedProductItem implements IStringArrayGenerator {

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
    private String refundAmount;
    /**
     * 数量
     */
    private String refundProductNum;
    /**
     * 单位
     */
    private String refundUnit;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(getProductCode());// 商品编号
        list.add(getProductName()); // 商品名称
        list.add(StringUtil.isBlank(getProductSpec())?"-":getProductSpec()); // 规格
        list.add(StringUtil.isBlank(getRefundUnit())?"-":getRefundUnit()); //单位
        list.add(getRefundProductNum()); // 数量
        list.add(CommonUtils.formatMoney(Double.parseDouble(getRefundAmount()))); //退款金额
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

    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundProductNum() {
        return refundProductNum;
    }

    public void setRefundProductNum(String refundProductNum) {
        this.refundProductNum = refundProductNum;
    }

    public String getRefundUnit() {
        return refundUnit;
    }

    public void setRefundUnit(String refundUnit) {
        this.refundUnit = refundUnit;
    }
}
