package com.hll_sc_app.bean.report.customerreive;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/30
 */

public class ReceiveCustomerBean {
    private String name;
    private double inAmount;
    private double outAmount;
    private String purchaserID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getInAmount() {
        return inAmount;
    }

    public void setInAmount(double inAmount) {
        this.inAmount = inAmount;
    }

    public double getOutAmount() {
        return outAmount;
    }

    public void setOutAmount(double outAmount) {
        this.outAmount = outAmount;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }
}
