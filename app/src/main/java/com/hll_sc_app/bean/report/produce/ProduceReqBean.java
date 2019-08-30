package com.hll_sc_app.bean.report.produce;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/30
 */

public class ProduceReqBean extends ProduceBean {
    private String coopGroupName;
    private String hoursFee;

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
