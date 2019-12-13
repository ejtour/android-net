package com.hll_sc_app.bean.report.deliverytime;

public class DeliveryTimeNearlyBean {

    private long beyond30MinInspectionNum;
    private long onTimeInspectionNum;
    private long within15MinInspectionNum;
    private long within30MinInspectionNum;


    public long getBeyond30MinInspectionNum() {
        return beyond30MinInspectionNum;
    }

    public void setBeyond30MinInspectionNum(long beyond30MinInspectionNum) {
        this.beyond30MinInspectionNum = beyond30MinInspectionNum;
    }

    public long getOnTimeInspectionNum() {
        return onTimeInspectionNum;
    }

    public void setOnTimeInspectionNum(long onTimeInspectionNum) {
        this.onTimeInspectionNum = onTimeInspectionNum;
    }

    public long getWithin15MinInspectionNum() {
        return within15MinInspectionNum;
    }

    public void setWithin15MinInspectionNum(long within15MinInspectionNum) {
        this.within15MinInspectionNum = within15MinInspectionNum;
    }

    public long getWithin30MinInspectionNum() {
        return within30MinInspectionNum;
    }

    public void setWithin30MinInspectionNum(long within30MinInspectionNum) {
        this.within30MinInspectionNum = within30MinInspectionNum;
    }
}
