package com.hll_sc_app.bean.rank;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/9
 */

public class SalesRankBean {
    /**
     * 销售编码
     */
    private String salesManCode;
    private String salesManID;
    private String salesManName;
    private double validTradeAmount;

    public String getSalesManCode() {
        return salesManCode;
    }

    public void setSalesManCode(String salesManCode) {
        this.salesManCode = salesManCode;
    }

    public String getSalesManID() {
        return salesManID;
    }

    public void setSalesManID(String salesManID) {
        this.salesManID = salesManID;
    }

    public String getSalesManName() {
        return salesManName;
    }

    public void setSalesManName(String salesManName) {
        this.salesManName = salesManName;
    }

    public double getValidTradeAmount() {
        return validTradeAmount;
    }

    public void setValidTradeAmount(double validTradeAmount) {
        this.validTradeAmount = validTradeAmount;
    }
}
