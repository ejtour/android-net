package com.hll_sc_app.bean.delivery;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 市
 *
 * @author zhuyingsong
 * @date 2019-07-30
 */
public class CityListBean implements Parcelable {
    /**
     * cityName : 北京市
     * cityCode : 1101
     * areaList : [{"areaCode":"110101","flag":3,"areaName":"东城区"}]
     */
    private String cityName;
    private String cityCode;
    private transient boolean isSelect;
    private List<AreaListBean> areaList;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

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

    public static final Parcelable.Creator<CityListBean> CREATOR = new Parcelable.Creator<CityListBean>() {
        @Override
        public CityListBean createFromParcel(Parcel source) {
            return new CityListBean(source);
        }

        @Override
        public CityListBean[] newArray(int size) {
            return new CityListBean[size];
        }
    };

    public CityListBean() {
    }

    protected CityListBean(Parcel in) {
        this.cityName = in.readString();
        this.cityCode = in.readString();
        this.areaList = new ArrayList<AreaListBean>();
        in.readList(this.areaList, AreaListBean.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cityName);
        dest.writeString(this.cityCode);
        dest.writeList(this.areaList);
    }
}
