package com.hll_sc_app.bean.report.refund;

import com.alibaba.sdk.android.ams.common.util.StringUtil;
import com.google.zxing.common.StringUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;

import java.util.ArrayList;
import java.util.List;

public class WaitRefundProductItem implements IStringArrayGenerator {

    /**
     * 商品编码
     */
    private String productCode;
    /**
     * 商品ID
     */
    private long   productID;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 规格内容
     */
    private String productSpec;
    /**
     * 规格ID
     */
    private long   productSpecID;
    /**
     * 退款金额
     */
    private String refundAmount;
    /**
     * 退货单数
     */
    private String refundNum;
    /**
     * 单位
     */
    private String refundUnit;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(getProductCode());// 商品编码
        list.add(getProductName()); // 商品名称
        list.add(StringUtil.isBlank(getProductSpec())?"-":getProductSpec()); // 规格
        list.add(StringUtil.isBlank(getRefundUnit())?"-":getRefundUnit()); //单位
        list.add(getRefundNum()); // 数量
        list.add(CommonUtils.formatMoney(Double.parseDouble(getRefundAmount()))); //金额
        return list;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
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

    public long getProductSpecID() {
        return productSpecID;
    }

    public void setProductSpecID(long productSpecID) {
        this.productSpecID = productSpecID;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundNum() {
        return refundNum;
    }

    public void setRefundNum(String refundNum) {
        this.refundNum = refundNum;
    }

    public String getRefundUnit() {
        return refundUnit;
    }

    public void setRefundUnit(String refundUnit) {
        this.refundUnit = refundUnit;
    }
}
