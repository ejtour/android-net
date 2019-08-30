package com.hll_sc_app.bean.report.produce;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/30
 */

public class ProduceDetailBean extends ProduceBean {
    private String classes;
    private String inputPer;
    private String coopGroupName;
    private String hoursFee;

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getInputPer() {
        return inputPer;
    }

    public void setInputPer(String inputPer) {
        this.inputPer = inputPer;
    }

    public String getCoopGroupName() {
        return coopGroupName;
    }

    public void setCoopGroupName(String coopGroupName) {
        this.coopGroupName = coopGroupName;
    }

    public String getHoursFee() {
        return hoursFee;
    }

    public void setHoursFee(String hoursFee) {
        this.hoursFee = hoursFee;
    }
}
