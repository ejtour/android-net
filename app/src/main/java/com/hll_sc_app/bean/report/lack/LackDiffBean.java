package com.hll_sc_app.bean.report.lack;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 缺货汇总
 */
public class LackDiffBean implements IStringArrayGenerator {
    private String date;
    private double deliveryLackAmount;
    private int deliveryLackKindNum;
    private int deliveryLackNum;
    private double deliveryLackRate;
    private int deliveryLackShopNum;
    private int deliveryOrderNum;
    private double deliveryTradeAmount;
    private double oriReserveTotalAmount;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(DateUtil.getReadableTime(date, Constants.SLASH_YYYY_MM_DD)); // 日期
        list.add(CommonUtils.formatNumber(deliveryOrderNum)); // 发货量
        list.add(CommonUtils.formatMoney(deliveryTradeAmount)); // 发货金额
        list.add(CommonUtils.formatMoney(oriReserveTotalAmount)); // 原订货金额
        list.add(CommonUtils.formatNumber(deliveryLackKindNum)); // 缺货商品数
        list.add(CommonUtils.formatNumber(deliveryLackNum)); // 缺货数量
        list.add(CommonUtils.formatMoney(deliveryLackAmount)); // 缺货金额
        list.add(-2 == deliveryLackRate ? "- -" : Utils.numToPercent(deliveryLackRate)); // 缺货率
        list.add(String.valueOf(deliveryLackShopNum)); // 影响门店
        return list;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getDeliveryLackAmount() {
        return deliveryLackAmount;
    }

    public void setDeliveryLackAmount(double deliveryLackAmount) {
        this.deliveryLackAmount = deliveryLackAmount;
    }

    public int getDeliveryLackKindNum() {
        return deliveryLackKindNum;
    }

    public void setDeliveryLackKindNum(int deliveryLackKindNum) {
        this.deliveryLackKindNum = deliveryLackKindNum;
    }

    public int getDeliveryLackNum() {
        return deliveryLackNum;
    }

    public void setDeliveryLackNum(int deliveryLackNum) {
        this.deliveryLackNum = deliveryLackNum;
    }

    public double getDeliveryLackRate() {
        return deliveryLackRate;
    }

    public void setDeliveryLackRate(double deliveryLackRate) {
        this.deliveryLackRate = deliveryLackRate;
    }

    public int getDeliveryLackShopNum() {
        return deliveryLackShopNum;
    }

    public void setDeliveryLackShopNum(int deliveryLackShopNum) {
        this.deliveryLackShopNum = deliveryLackShopNum;
    }

    public int getDeliveryOrderNum() {
        return deliveryOrderNum;
    }

    public void setDeliveryOrderNum(int deliveryOrderNum) {
        this.deliveryOrderNum = deliveryOrderNum;
    }

    public double getDeliveryTradeAmount() {
        return deliveryTradeAmount;
    }

    public void setDeliveryTradeAmount(double deliveryTradeAmount) {
        this.deliveryTradeAmount = deliveryTradeAmount;
    }

    public double getOriReserveTotalAmount() {
        return oriReserveTotalAmount;
    }

    public void setOriReserveTotalAmount(double oriReserveTotalAmount) {
        this.oriReserveTotalAmount = oriReserveTotalAmount;
    }
}
