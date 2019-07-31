package com.hll_sc_app.bean.wallet;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/31
 */

public class AreaInfo {
    private String areaCode;
    private String areaName;
    private String areaParentId;
    private int areaType;
    private String standardAreaName;
    private String standardAreaCode;
    public AreaInfo() {
    }
    public AreaInfo(String areaCode, String areaName) {
        this.areaCode = areaCode;
        this.areaName = areaName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaParentId() {
        return areaParentId;
    }

    public void setAreaParentId(String areaParentId) {
        this.areaParentId = areaParentId;
    }

    public int getAreaType() {
        return areaType;
    }

    public void setAreaType(int areaType) {
        this.areaType = areaType;
    }

    public String getStandardAreaName() {
        return standardAreaName;
    }

    public void setStandardAreaName(String standardAreaName) {
        this.standardAreaName = standardAreaName;
    }

    public String getStandardAreaCode() {
        return standardAreaCode;
    }

    public void setStandardAreaCode(String standardAreaCode) {
        this.standardAreaCode = standardAreaCode;
    }
}
