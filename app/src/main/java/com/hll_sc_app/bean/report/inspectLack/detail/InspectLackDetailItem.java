package com.hll_sc_app.bean.report.inspectLack.detail;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;

import java.util.ArrayList;
import java.util.List;

public class InspectLackDetailItem implements IStringArrayGenerator {

    private long date;
    private String inspectionAmount;
    private String inspectionLackAmount;
    private String inspectionLackNum;
    private String inspectionLackRate;
    private String inspectionNum;
    private String oriDeliveryAmount;
    private String oriDeliveryNum;
    private String productCode;
    private String productName;
    private long shopNum;
    private long specID;
    private String specUnit;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(getProductName()); // 商品名称
        list.add(getSpecUnit()); // 规格
        list.add(CommonUtils.formatNumber(getOriDeliveryNum())); // 发货量
        list.add(CommonUtils.formatMoney(Double.parseDouble(getOriDeliveryAmount()))); // 原发货金额
        list.add(CommonUtils.formatNumber(getInspectionNum())); //收货差异商品数
        list.add(CommonUtils.formatNumber(getInspectionLackNum())); // 收货差异量
        list.add(CommonUtils.formatMoney(Double.parseDouble(getInspectionLackAmount()))); // 收货差异金额
        list.add(getInspectionLackRate());//收货差异率
        return list;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getInspectionAmount() {
        return inspectionAmount;
    }

    public void setInspectionAmount(String inspectionAmount) {
        this.inspectionAmount = inspectionAmount;
    }

    public String getInspectionLackAmount() {
        return inspectionLackAmount;
    }

    public void setInspectionLackAmount(String inspectionLackAmount) {
        this.inspectionLackAmount = inspectionLackAmount;
    }

    public String getInspectionLackNum() {
        return inspectionLackNum;
    }

    public void setInspectionLackNum(String inspectionLackNum) {
        this.inspectionLackNum = inspectionLackNum;
    }

    public String getInspectionLackRate() {
        return inspectionLackRate;
    }

    public void setInspectionLackRate(String inspectionLackRate) {
        this.inspectionLackRate = inspectionLackRate;
    }

    public String getInspectionNum() {
        return inspectionNum;
    }

    public void setInspectionNum(String inspectionNum) {
        this.inspectionNum = inspectionNum;
    }

    public String getOriDeliveryAmount() {
        return oriDeliveryAmount;
    }

    public void setOriDeliveryAmount(String oriDeliveryAmount) {
        this.oriDeliveryAmount = oriDeliveryAmount;
    }

    public String getOriDeliveryNum() {
        return oriDeliveryNum;
    }

    public void setOriDeliveryNum(String oriDeliveryNum) {
        this.oriDeliveryNum = oriDeliveryNum;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public long getShopNum() {
        return shopNum;
    }

    public void setShopNum(long shopNum) {
        this.shopNum = shopNum;
    }

    public long getSpecID() {
        return specID;
    }

    public void setSpecID(long specID) {
        this.specID = specID;
    }

    public String getSpecUnit() {
        return specUnit;
    }

    public void setSpecUnit(String specUnit) {
        this.specUnit = specUnit;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
