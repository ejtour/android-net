package com.hll_sc_app.bean.report.receive;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;
import com.hll_sc_app.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ReceiveDiffDetailsBean implements IStringArrayGenerator {
    private String date;
    private double inspectionAmount;
    private double inspectionLackAmount;
    private double inspectionLackNum;
    private String inspectionLackRate;
    private double inspectionNum;
    private double oriDeliveryAmount;
    private double oriDeliveryNum;
    private String productCode;
    private String productName;
    private int shopNum;
    private String specID;
    private String specUnit;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(productName); // 商品名称
        list.add(specUnit); // 规格/单位
        list.add(CommonUtils.formatNumber(oriDeliveryNum)); // 发货量
        list.add(CommonUtils.formatMoney(oriDeliveryAmount)); // 发货金额(元)
        list.add(CommonUtils.formatNumber(inspectionNum)); // 收货量
        list.add(CommonUtils.formatNumber(inspectionLackNum)); // 差异量
        list.add(CommonUtils.formatMoney(inspectionLackAmount)); // 差异金额(元)
        list.add(inspectionLackRate);// 差异率
        return list;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getInspectionAmount() {
        return inspectionAmount;
    }

    public void setInspectionAmount(double inspectionAmount) {
        this.inspectionAmount = inspectionAmount;
    }

    public double getInspectionLackAmount() {
        return inspectionLackAmount;
    }

    public void setInspectionLackAmount(double inspectionLackAmount) {
        this.inspectionLackAmount = inspectionLackAmount;
    }

    public double getInspectionLackNum() {
        return inspectionLackNum;
    }

    public void setInspectionLackNum(double inspectionLackNum) {
        this.inspectionLackNum = inspectionLackNum;
    }

    public String getInspectionLackRate() {
        return inspectionLackRate;
    }

    public void setInspectionLackRate(String inspectionLackRate) {
        this.inspectionLackRate = inspectionLackRate;
    }

    public double getInspectionNum() {
        return inspectionNum;
    }

    public void setInspectionNum(double inspectionNum) {
        this.inspectionNum = inspectionNum;
    }

    public double getOriDeliveryAmount() {
        return oriDeliveryAmount;
    }

    public void setOriDeliveryAmount(double oriDeliveryAmount) {
        this.oriDeliveryAmount = oriDeliveryAmount;
    }

    public double getOriDeliveryNum() {
        return oriDeliveryNum;
    }

    public void setOriDeliveryNum(double oriDeliveryNum) {
        this.oriDeliveryNum = oriDeliveryNum;
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

    public int getShopNum() {
        return shopNum;
    }

    public void setShopNum(int shopNum) {
        this.shopNum = shopNum;
    }

    public String getSpecID() {
        return specID;
    }

    public void setSpecID(String specID) {
        this.specID = specID;
    }

    public String getSpecUnit() {
        return specUnit;
    }

    public void setSpecUnit(String specUnit) {
        this.specUnit = specUnit;
    }
}
