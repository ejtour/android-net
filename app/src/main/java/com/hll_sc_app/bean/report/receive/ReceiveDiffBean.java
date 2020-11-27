package com.hll_sc_app.bean.report.receive;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ReceiveDiffBean implements IStringArrayGenerator {

    private String date;
    private double inspectionLackAmount;
    private int inspectionLackKindNum;
    private double inspectionLackNum;
    private double inspectionLackRate;
    private double deliveryOrderNum;
    private double deliveryTradeAmount;
    private int inspectionOrderNum;
    private double inspectionTotalAmount;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(DateUtil.getReadableTime(date, Constants.SLASH_YYYY_MM_DD)); // 日期
        list.add(CommonUtils.formatNumber(deliveryOrderNum)); // 发货单数
        list.add(CommonUtils.formatMoney(deliveryTradeAmount)); // 发货金额
        list.add(CommonUtils.formatNumber(inspectionOrderNum)); // 收货单数
        list.add(CommonUtils.formatMoney(inspectionTotalAmount)); // 收货金额
        list.add(CommonUtils.formatNumber(inspectionLackKindNum)); // 收货差异商品数
        list.add(CommonUtils.formatNumber(inspectionLackNum)); // 收货差异量
        list.add(CommonUtils.formatMoney(inspectionLackAmount)); // 收货差异金额
        list.add(-2 == inspectionLackRate ? "- -" : Utils.numToPercent(inspectionLackRate));// 收货差异率
        return list;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getInspectionLackAmount() {
        return inspectionLackAmount;
    }

    public void setInspectionLackAmount(double inspectionLackAmount) {
        this.inspectionLackAmount = inspectionLackAmount;
    }

    public int getInspectionLackKindNum() {
        return inspectionLackKindNum;
    }

    public void setInspectionLackKindNum(int inspectionLackKindNum) {
        this.inspectionLackKindNum = inspectionLackKindNum;
    }

    public double getInspectionLackNum() {
        return inspectionLackNum;
    }

    public void setInspectionLackNum(double inspectionLackNum) {
        this.inspectionLackNum = inspectionLackNum;
    }

    public double getInspectionLackRate() {
        return inspectionLackRate;
    }

    public void setInspectionLackRate(double inspectionLackRate) {
        this.inspectionLackRate = inspectionLackRate;
    }

    public double getDeliveryOrderNum() {
        return deliveryOrderNum;
    }

    public void setDeliveryOrderNum(double deliveryOrderNum) {
        this.deliveryOrderNum = deliveryOrderNum;
    }

    public double getDeliveryTradeAmount() {
        return deliveryTradeAmount;
    }

    public void setDeliveryTradeAmount(double deliveryTradeAmount) {
        this.deliveryTradeAmount = deliveryTradeAmount;
    }

    public int getInspectionOrderNum() {
        return inspectionOrderNum;
    }

    public void setInspectionOrderNum(int inspectionOrderNum) {
        this.inspectionOrderNum = inspectionOrderNum;
    }

    public double getInspectionTotalAmount() {
        return inspectionTotalAmount;
    }

    public void setInspectionTotalAmount(double inspectionTotalAmount) {
        this.inspectionTotalAmount = inspectionTotalAmount;
    }
}
