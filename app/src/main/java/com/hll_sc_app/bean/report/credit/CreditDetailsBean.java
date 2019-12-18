package com.hll_sc_app.bean.report.credit;

import android.text.TextUtils;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/18
 */

public class CreditDetailsBean implements IStringArrayGenerator {
    private String date;
    private double receiveAmount;
    private double payAmount;
    private String shopName;
    private double untaxReceiveAmount;
    private double unPayAmount;
    private String payAmountRate;
    private String purchaserName;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(DateUtil.getReadableTime(date, Constants.SLASH_YYYY_MM_DD));
        if (!TextUtils.isEmpty(purchaserName)) {
            list.add(purchaserName);
            list.add(shopName);
        }
        list.add(CommonUtils.formatMoney(receiveAmount));
        list.add(CommonUtils.formatMoney(untaxReceiveAmount));
        list.add(CommonUtils.formatMoney(payAmount));
        list.add(CommonUtils.formatMoney(unPayAmount));
        list.add(payAmountRate);
        return list;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(double receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public double getUntaxReceiveAmount() {
        return untaxReceiveAmount;
    }

    public void setUntaxReceiveAmount(double untaxReceiveAmount) {
        this.untaxReceiveAmount = untaxReceiveAmount;
    }

    public double getUnPayAmount() {
        return unPayAmount;
    }

    public void setUnPayAmount(double unPayAmount) {
        this.unPayAmount = unPayAmount;
    }

    public String getPayAmountRate() {
        return payAmountRate;
    }

    public void setPayAmountRate(String payAmountRate) {
        this.payAmountRate = payAmountRate;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }
}
