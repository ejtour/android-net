package com.hll_sc_app.bean.delivery;

import java.util.List;

/**
 * 省
 *
 * @author zhuyingsong
 * @date 2019-07-30
 */
public class ProvinceListBean {
    private String provinceCode;
    private String provinceName;
    private int optionalNum;
    private int selectedNum;
    private List<CityListBean> cityList;
    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getOptionalNum() {
        return optionalNum;
    }

    public void setOptionalNum(int optionalNum) {
        this.optionalNum = optionalNum;
    }

    public int getSelectedNum() {
        return selectedNum;
    }

    public void setSelectedNum(int selectedNum) {
        this.selectedNum = selectedNum;
    }

    public List<CityListBean> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityListBean> cityList) {
        this.cityList = cityList;
    }
}
