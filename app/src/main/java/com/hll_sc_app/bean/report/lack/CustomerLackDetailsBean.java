package com.hll_sc_app.bean.report.lack;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;

import java.util.ArrayList;
import java.util.List;

public class CustomerLackDetailsBean implements IStringArrayGenerator {

    private double deliveryAmount;
    private double deliveryLackAmount;
    private double deliveryLackNum;
    private String deliveryLackRate;
    private double deliveryNum;
    private double oriReserveAmount;
    private int oriReserveNum;
    private String productName;
    private String specUnit;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(productName); // 商品名称
        list.add(specUnit); // 商品规格
        list.add(CommonUtils.formatNumber(oriReserveNum)); // 订货量
        list.add(CommonUtils.formatMoney(oriReserveAmount)); // 订货金额
        list.add(CommonUtils.formatNumber(deliveryNum)); //发货量
        list.add(CommonUtils.formatNumber(deliveryLackNum)); // 缺货量
        list.add(CommonUtils.formatMoney(deliveryLackAmount)); // 缺货金额
        list.add(deliveryLackRate);
        return list;
    }

    public double getDeliveryAmount() {
        return deliveryAmount;
    }

    public void setDeliveryAmount(double deliveryAmount) {
        this.deliveryAmount = deliveryAmount;
    }

    public double getDeliveryLackAmount() {
        return deliveryLackAmount;
    }

    public void setDeliveryLackAmount(double deliveryLackAmount) {
        this.deliveryLackAmount = deliveryLackAmount;
    }

    public double getDeliveryLackNum() {
        return deliveryLackNum;
    }

    public void setDeliveryLackNum(double deliveryLackNum) {
        this.deliveryLackNum = deliveryLackNum;
    }

    public String getDeliveryLackRate() {
        return deliveryLackRate;
    }

    public void setDeliveryLackRate(String deliveryLackRate) {
        this.deliveryLackRate = deliveryLackRate;
    }

    public double getDeliveryNum() {
        return deliveryNum;
    }

    public void setDeliveryNum(double deliveryNum) {
        this.deliveryNum = deliveryNum;
    }

    public double getOriReserveAmount() {
        return oriReserveAmount;
    }

    public void setOriReserveAmount(double oriReserveAmount) {
        this.oriReserveAmount = oriReserveAmount;
    }

    public int getOriReserveNum() {
        return oriReserveNum;
    }

    public void setOriReserveNum(int oriReserveNum) {
        this.oriReserveNum = oriReserveNum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSpecUnit() {
        return specUnit;
    }

    public void setSpecUnit(String specUnit) {
        this.specUnit = specUnit;
    }
}
