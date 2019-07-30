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
    /**
     * 该省下面的区的数量
     */
    private int allNum;
    private int selectedNum;
    private List<CityListBean> cityList;
    private List<String> codeList;
    private boolean select;

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public List<String> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<String> codeList) {
        this.codeList = codeList;
    }

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
