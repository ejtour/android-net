package com.hll_sc_app.bean.report.marketing;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/28
 */

public class MarketingProductBean implements IStringArrayGenerator {
    private int sequenceNo;
    private String productCode;
    private String productName;
    private String productSpec;
    private String productCategoryName;
    private double productNum;
    private double saleAmount;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(String.valueOf(sequenceNo));
        list.add(productCode);
        list.add(productName);
        list.add(productSpec);
        list.add(productCategoryName);
        list.add(CommonUtils.formatNum(productNum));
        list.add("¥" + CommonUtils.formatMoney(saleAmount));
        return list;
    }

    public int getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
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

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public double getProductNum() {
        return productNum;
    }

    public void setProductNum(double productNum) {
        this.productNum = productNum;
    }

    public double getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(double saleAmount) {
        this.saleAmount = saleAmount;
    }
}
