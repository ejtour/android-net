package com.hll_sc_app.bean.report.lack;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/16
 */

public class LackDetailsBean implements IStringArrayGenerator {
    private String specID;
    private int oriReserveNum;
    private int shopNum;
    private String deliveryLackRate;
    private double oriReserveAmount;
    private double deliveryAmount;
    private String shipperName;
    private String specUnit;
    private String productName;
    private int deliveryNum;
    private String productCode;
    private double deliveryLackAmount;
    private int deliveryLackNum;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(productName); // 商品名称
        list.add(specUnit); // 规格/单位
        list.add(CommonUtils.formatNumber(oriReserveNum)); // 订货量
        list.add(CommonUtils.formatMoney(oriReserveAmount)); // 订货金额
        list.add(CommonUtils.formatNumber(deliveryNum)); // 发货量
        list.add(CommonUtils.formatNumber(deliveryLackNum)); // 缺货量
        list.add(CommonUtils.formatMoney(deliveryLackAmount)); // 缺货金额
        list.add(deliveryLackRate); // 缺货率
        return list;
    }

    public String getSpecID() {
        return specID;
    }

    public void setSpecID(String specID) {
        this.specID = specID;
    }

    public int getOriReserveNum() {
        return oriReserveNum;
    }

    public void setOriReserveNum(int oriReserveNum) {
        this.oriReserveNum = oriReserveNum;
    }

    public int getShopNum() {
        return shopNum;
    }

    public void setShopNum(int shopNum) {
        this.shopNum = shopNum;
    }

    public String getDeliveryLackRate() {
        return deliveryLackRate;
    }

    public void setDeliveryLackRate(String deliveryLackRate) {
        this.deliveryLackRate = deliveryLackRate;
    }

    public double getOriReserveAmount() {
        return oriReserveAmount;
    }

    public void setOriReserveAmount(double oriReserveAmount) {
        this.oriReserveAmount = oriReserveAmount;
    }

    public double getDeliveryAmount() {
        return deliveryAmount;
    }

    public void setDeliveryAmount(double deliveryAmount) {
        this.deliveryAmount = deliveryAmount;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
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

    public int getDeliveryNum() {
        return deliveryNum;
    }

    public void setDeliveryNum(int deliveryNum) {
        this.deliveryNum = deliveryNum;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public double getDeliveryLackAmount() {
        return deliveryLackAmount;
    }

    public void setDeliveryLackAmount(double deliveryLackAmount) {
        this.deliveryLackAmount = deliveryLackAmount;
    }

    public int getDeliveryLackNum() {
        return deliveryLackNum;
    }

    public void setDeliveryLackNum(int deliveryLackNum) {
        this.deliveryLackNum = deliveryLackNum;
    }
}
