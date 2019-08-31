package com.hll_sc_app.bean.report.deliveryTime;

import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;
import com.hll_sc_app.utils.Constants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DeliveryTimeItem implements IStringArrayGenerator {

    private long   beyond30MinInspectionNum;
    private String beyond30MinInspectionRate;
    private long   date;
    private long   deliveryOrderNum;
    private long   executeOrderNum;
    private long   inspectionOrderNum;
    private long   onTimeInspectionNum;
    private String onTimeInspectionRate;
    private long   within15MinInspectionNum;
    private String within15MinInspectionRate;
    private long   within30MinInspectionNum;
    private String within30MinInspectionRate;

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(CalendarUtils.getDateFormatString(getDate() + "", CalendarUtils.FORMAT_LOCAL_DATE, Constants.SLASH_YYYY_MM_DD)); // 时间
        list.add(CommonUtils.formatNumber(getExecuteOrderNum())); // 要求到货单量
        list.add(CommonUtils.formatNumber(getDeliveryOrderNum())); // 发货单量
        list.add(CommonUtils.formatNumber(getInspectionOrderNum())); // 签收单量
        list.add(CommonUtils.formatNumber(getOnTimeInspectionNum())); //按要求时间配送单量
        list.add(CommonUtils.formatNumber(getOnTimeInspectionRate().equals("-2") ? "" : new BigDecimal(getOnTimeInspectionRate()).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "%")); // 按要求时间配送单量占比
        list.add(CommonUtils.formatNumber(getWithin15MinInspectionNum())); //差异15分钟内配送单量
        list.add(CommonUtils.formatNumber(getWithin15MinInspectionRate().equals("-2") ? "" : new BigDecimal(getWithin15MinInspectionRate()).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "%")); // 差异15分钟内配送单量占比
        list.add(CommonUtils.formatNumber(getWithin30MinInspectionNum())); //差异30分钟内配送单量
        list.add(CommonUtils.formatNumber(getWithin30MinInspectionRate().equals("-2") ? "" : new BigDecimal(getWithin30MinInspectionRate()).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "%")); // 差异30分钟内配送单量占比
        list.add(CommonUtils.formatNumber(getBeyond30MinInspectionNum())); //差异30分钟以上配送单量
        list.add(CommonUtils.formatNumber(getBeyond30MinInspectionRate().equals("-2") ? "" : new BigDecimal(getBeyond30MinInspectionRate()).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "%")); // 差异30分钟以上配送单量占比
        return list;
    }


    public long getBeyond30MinInspectionNum() {
        return beyond30MinInspectionNum;
    }

    public void setBeyond30MinInspectionNum(long beyond30MinInspectionNum) {
        this.beyond30MinInspectionNum = beyond30MinInspectionNum;
    }

    public String getBeyond30MinInspectionRate() {
        return beyond30MinInspectionRate;
    }

    public void setBeyond30MinInspectionRate(String beyond30MinInspectionRate) {
        this.beyond30MinInspectionRate = beyond30MinInspectionRate;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDeliveryOrderNum() {
        return deliveryOrderNum;
    }

    public void setDeliveryOrderNum(long deliveryOrderNum) {
        this.deliveryOrderNum = deliveryOrderNum;
    }

    public long getExecuteOrderNum() {
        return executeOrderNum;
    }

    public void setExecuteOrderNum(long executeOrderNum) {
        this.executeOrderNum = executeOrderNum;
    }

    public long getInspectionOrderNum() {
        return inspectionOrderNum;
    }

    public void setInspectionOrderNum(long inspectionOrderNum) {
        this.inspectionOrderNum = inspectionOrderNum;
    }

    public long getOnTimeInspectionNum() {
        return onTimeInspectionNum;
    }

    public void setOnTimeInspectionNum(long onTimeInspectionNum) {
        this.onTimeInspectionNum = onTimeInspectionNum;
    }

    public String getOnTimeInspectionRate() {
        return onTimeInspectionRate;
    }

    public void setOnTimeInspectionRate(String onTimeInspectionRate) {
        this.onTimeInspectionRate = onTimeInspectionRate;
    }

    public long getWithin15MinInspectionNum() {
        return within15MinInspectionNum;
    }

    public void setWithin15MinInspectionNum(long within15MinInspectionNum) {
        this.within15MinInspectionNum = within15MinInspectionNum;
    }

    public String getWithin15MinInspectionRate() {
        return within15MinInspectionRate;
    }

    public void setWithin15MinInspectionRate(String within15MinInspectionRate) {
        this.within15MinInspectionRate = within15MinInspectionRate;
    }

    public long getWithin30MinInspectionNum() {
        return within30MinInspectionNum;
    }

    public void setWithin30MinInspectionNum(long within30MinInspectionNum) {
        this.within30MinInspectionNum = within30MinInspectionNum;
    }

    public String getWithin30MinInspectionRate() {
        return within30MinInspectionRate;
    }

    public void setWithin30MinInspectionRate(String within30MinInspectionRate) {
        this.within30MinInspectionRate = within30MinInspectionRate;
    }
}
