package com.hll_sc_app.app.aftersales.audit;

/**
 * @author <a href="mailto:xzx8023@vip.qq.com">Vixb</a>
 * @since 2019/7/8
 */

public class AuditParam {
    private String purchaserID;
    private String purchaserShopID;
    private int sourceType;
    private String startTime;
    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getPurchaserShopID() {
        return purchaserShopID;
    }

    public void setPurchaserShopID(String purchaserShopID) {
        this.purchaserShopID = purchaserShopID;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }
}
