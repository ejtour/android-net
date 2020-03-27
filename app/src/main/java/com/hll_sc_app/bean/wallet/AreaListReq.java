package com.hll_sc_app.bean.wallet;

/**
 * 我的钱包 查询省市区表
 *
 * @author zc
 */
public class AreaListReq {

    public static final int PROVINCE = 2;
    public static final int CITY = 3;
    public static final int DISTRIBUTE = 4;

    private String areaParentId;
    private int areaType;

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
        if (areaType == PROVINCE) {
            areaParentId = "ZP1";
        }
        this.areaType = areaType;
    }
}
