package com.hll_sc_app.bean.report.customerLack;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;

import java.util.ArrayList;
import java.util.List;

public class CustomerLackItem implements IStringArrayGenerator {

    private String deliveryAmount;
    private String deliveryLackAmount;
    private String deliveryLackNum;
    private String deliveryLackRate;
    private String deliveryNum;
    private String oriReserveAmount;
    private String oriReserveNum;
    private String productName;
    private String specUnit;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(getProductName()); // 商品名称
        list.add(getSpecUnit()); // 商品规格
        list.add(getOriReserveNum()); // 订货量
        list.add(CommonUtils.formatMoney(Double.parseDouble(getOriReserveAmount()))); // 订货金额
        list.add(getDeliveryNum()); //发货量
        list.add(getDeliveryNum()); // 缺货量
        list.add(CommonUtils.formatMoney(Double.parseDouble(getDeliveryLackAmount()))); // 缺货金额
        list.add(getDeliveryLackRate());
        return list;
    }

    public String getDeliveryAmount() {
        return deliveryAmount;
    }

    public void setDeliveryAmount(String deliveryAmount) {
        this.deliveryAmount = deliveryAmount;
    }

    public String getDeliveryLackAmount() {
        return deliveryLackAmount;
    }

    public void setDeliveryLackAmount(String deliveryLackAmount) {
        this.deliveryLackAmount = deliveryLackAmount;
    }

    public String getDeliveryLackNum() {
        return deliveryLackNum;
    }

    public void setDeliveryLackNum(String deliveryLackNum) {
        this.deliveryLackNum = deliveryLackNum;
    }

    public String getDeliveryLackRate() {
        return deliveryLackRate;
    }

    public void setDeliveryLackRate(String deliveryLackRate) {
        this.deliveryLackRate = deliveryLackRate;
    }

    public String getDeliveryNum() {
        return deliveryNum;
    }

    public void setDeliveryNum(String deliveryNum) {
        this.deliveryNum = deliveryNum;
    }

    public String getOriReserveAmount() {
        return oriReserveAmount;
    }

    public void setOriReserveAmount(String oriReserveAmount) {
        this.oriReserveAmount = oriReserveAmount;
    }

    public String getOriReserveNum() {
        return oriReserveNum;
    }

    public void setOriReserveNum(String oriReserveNum) {
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
