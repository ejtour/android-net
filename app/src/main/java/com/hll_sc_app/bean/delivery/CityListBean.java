package com.hll_sc_app.bean.delivery;

import java.util.List;

/**
 * 市
 *
 * @author zhuyingsong
 * @date 2019-07-30
 */
public class CityListBean {
    /**
     * cityName : 北京市
     * cityCode : 1101
     * areaList : [{"areaCode":"110101","flag":3,"areaName":"东城区"}]
     */
    private String cityName;
    private String cityCode;
    private List<AreaListBean> areaList;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public List<AreaListBean> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<AreaListBean> areaList) {
        this.areaList = areaList;
    }
}
