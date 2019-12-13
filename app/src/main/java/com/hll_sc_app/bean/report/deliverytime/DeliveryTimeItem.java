package com.hll_sc_app.bean.report.deliverytime;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class DeliveryTimeItem implements IStringArrayGenerator {

    private String date;
    private int beyond30MinInspectionNum;
    private double beyond30MinInspectionRate;
    private int deliveryOrderNum;
    private int executeOrderNum;
    private int inspectionOrderNum;
    private int onTimeInspectionNum;
    private double onTimeInspectionRate;
    private int within15MinInspectionNum;
    private double within15MinInspectionRate;
    private int within30MinInspectionNum;
    private double within30MinInspectionRate;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(DateUtil.getReadableTime(date, Constants.SLASH_YYYY_MM_DD)); // 日期
        list.add(CommonUtils.formatNumber(executeOrderNum)); // 要求到货单量
        list.add(CommonUtils.formatNumber(deliveryOrderNum)); // 发货单量
        list.add(CommonUtils.formatNumber(inspectionOrderNum)); // 签收单量
        list.add(CommonUtils.formatNumber(onTimeInspectionNum)); // 按要求时间配送单量
        list.add(-2 == onTimeInspectionRate ? "- -" : Utils.numToPercent(onTimeInspectionRate)); // 按要求时间配送单量占比
        list.add(CommonUtils.formatNumber(within15MinInspectionNum)); //差异15分钟内配送单量
        list.add(-2 == within15MinInspectionRate ? "- -" : Utils.numToPercent(within15MinInspectionRate)); // 差异15分钟内配送单量占比
        list.add(CommonUtils.formatNumber(within30MinInspectionNum)); //差异30分钟内配送单量
        list.add(-2 == within30MinInspectionRate ? "- -" : Utils.numToPercent(within30MinInspectionRate)); // 差异30分钟内配送单量占比
        list.add(CommonUtils.formatNumber(beyond30MinInspectionNum)); //差异30分钟以上配送单量
        list.add(-2 == beyond30MinInspectionRate ? "- -" : Utils.numToPercent(beyond30MinInspectionRate)); // 差异30分钟以上配送单量占比
        return list;
    }

    public int getBeyond30MinInspectionNum() {
        return beyond30MinInspectionNum;
    }

    public void setBeyond30MinInspectionNum(int beyond30MinInspectionNum) {
        this.beyond30MinInspectionNum = beyond30MinInspectionNum;
    }

    public double getBeyond30MinInspectionRate() {
        return beyond30MinInspectionRate;
    }

    public void setBeyond30MinInspectionRate(double beyond30MinInspectionRate) {
        this.beyond30MinInspectionRate = beyond30MinInspectionRate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDeliveryOrderNum() {
        return deliveryOrderNum;
    }

    public void setDeliveryOrderNum(int deliveryOrderNum) {
        this.deliveryOrderNum = deliveryOrderNum;
    }

    public int getExecuteOrderNum() {
        return executeOrderNum;
    }

    public void setExecuteOrderNum(int executeOrderNum) {
        this.executeOrderNum = executeOrderNum;
    }

    public int getInspectionOrderNum() {
        return inspectionOrderNum;
    }

    public void setInspectionOrderNum(int inspectionOrderNum) {
        this.inspectionOrderNum = inspectionOrderNum;
    }

    public int getOnTimeInspectionNum() {
        return onTimeInspectionNum;
    }

    public void setOnTimeInspectionNum(int onTimeInspectionNum) {
        this.onTimeInspectionNum = onTimeInspectionNum;
    }

    public double getOnTimeInspectionRate() {
        return onTimeInspectionRate;
    }

    public void setOnTimeInspectionRate(double onTimeInspectionRate) {
        this.onTimeInspectionRate = onTimeInspectionRate;
    }

    public int getWithin15MinInspectionNum() {
        return within15MinInspectionNum;
    }

    public void setWithin15MinInspectionNum(int within15MinInspectionNum) {
        this.within15MinInspectionNum = within15MinInspectionNum;
    }

    public double getWithin15MinInspectionRate() {
        return within15MinInspectionRate;
    }

    public void setWithin15MinInspectionRate(double within15MinInspectionRate) {
        this.within15MinInspectionRate = within15MinInspectionRate;
    }

    public int getWithin30MinInspectionNum() {
        return within30MinInspectionNum;
    }

    public void setWithin30MinInspectionNum(int within30MinInspectionNum) {
        this.within30MinInspectionNum = within30MinInspectionNum;
    }

    public double getWithin30MinInspectionRate() {
        return within30MinInspectionRate;
    }

    public void setWithin30MinInspectionRate(double within30MinInspectionRate) {
        this.within30MinInspectionRate = within30MinInspectionRate;
    }
}
