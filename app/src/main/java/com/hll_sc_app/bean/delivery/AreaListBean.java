package com.hll_sc_app.bean.delivery;

/**
 * 区
 *
 * @author zhuyingsong
 * @date 2019-07-30
 */
public class AreaListBean {

    /**
     * areaCode : 110101
     * flag : 3
     * areaName : 东城区
     */

    private String areaCode;
    /**
     * 数据状态标识 1-不可选 2-可选未选中 3-可选已选中
     */
    private String flag;
    private String areaName;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
