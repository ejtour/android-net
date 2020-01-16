package com.hll_sc_app.bean.report.profit;

import android.text.TextUtils;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/17
 */

public class ProfitBean implements IStringArrayGenerator {
    private double outAmount;
    private double sendPrice;
    private double profitAmount;
    private int voucherType;
    private String goodsID;
    private String shopName;
    private String profitRate;
    private double untaxReceiveAmount;
    private double reserveNum;
    private String productName;
    private String purchaserName;
    private String spec;
    private String productCategory;
    private double outPrice;
    private double receiveAmount;
    private String unit;
    private String productCategoryID;
    private String purchaserID;
    private String unRefundProfitRate;
    private double unRefundOutAmount;
    private String shopID;
    private double unRefundProfitAmount;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        if (CommonUtils.getInt(shopID) != 0) {
            list.add(purchaserName);
            list.add(shopName);
        } else if (!TextUtils.isEmpty(productCategoryID)) {
            list.add(productCategory);
        } else {
            list.add(purchaserName);
        }
        list.add(CommonUtils.formatMoney(receiveAmount));
        list.add(CommonUtils.formatMoney(untaxReceiveAmount));
        list.add(CommonUtils.formatMoney(outAmount));
        list.add(CommonUtils.formatMoney(profitAmount));
        list.add(profitRate);
        return list;
    }

    public double getOutAmount() {
        return outAmount;
    }

    public void setOutAmount(double outAmount) {
        this.outAmount = outAmount;
    }

    public double getSendPrice() {
        return sendPrice;
    }

    public void setSendPrice(double sendPrice) {
        this.sendPrice = sendPrice;
    }

    public double getProfitAmount() {
        return profitAmount;
    }

    public void setProfitAmount(double profitAmount) {
        this.profitAmount = profitAmount;
    }

    public int getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(int voucherType) {
        this.voucherType = voucherType;
    }

    public String getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(String goodsID) {
        this.goodsID = goodsID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(String profitRate) {
        this.profitRate = profitRate;
    }

    public double getUntaxReceiveAmount() {
        return untaxReceiveAmount;
    }

    public void setUntaxReceiveAmount(double untaxReceiveAmount) {
        this.untaxReceiveAmount = untaxReceiveAmount;
    }

    public double getReserveNum() {
        return reserveNum;
    }

    public void setReserveNum(double reserveNum) {
        this.reserveNum = reserveNum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public double getOutPrice() {
        return outPrice;
    }

    public void setOutPrice(double outPrice) {
        this.outPrice = outPrice;
    }

    public double getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(double receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProductCategoryID() {
        return productCategoryID;
    }

    public void setProductCategoryID(String productCategoryID) {
        this.productCategoryID = productCategoryID;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getUnRefundProfitRate() {
        return unRefundProfitRate;
    }

    public void setUnRefundProfitRate(String unRefundProfitRate) {
        this.unRefundProfitRate = unRefundProfitRate;
    }

    public double getUnRefundOutAmount() {
        return unRefundOutAmount;
    }

    public void setUnRefundOutAmount(double unRefundOutAmount) {
        this.unRefundOutAmount = unRefundOutAmount;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public double getUnRefundProfitAmount() {
        return unRefundProfitAmount;
    }

    public void setUnRefundProfitAmount(double unRefundProfitAmount) {
        this.unRefundProfitAmount = unRefundProfitAmount;
    }
}
