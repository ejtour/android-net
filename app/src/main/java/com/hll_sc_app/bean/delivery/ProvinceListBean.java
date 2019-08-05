package com.hll_sc_app.bean.delivery;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 省
 *
 * @author zhuyingsong
 * @date 2019-07-30
 */
public class ProvinceListBean implements Parcelable {
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

    public static final Parcelable.Creator<ProvinceListBean> CREATOR = new Parcelable.Creator<ProvinceListBean>() {
        @Override
        public ProvinceListBean createFromParcel(Parcel source) {
            return new ProvinceListBean(source);
        }

        @Override
        public ProvinceListBean[] newArray(int size) {
            return new ProvinceListBean[size];
        }
    };

    public ProvinceListBean() {
    }

    protected ProvinceListBean(Parcel in) {
        this.provinceCode = in.readString();
        this.provinceName = in.readString();
        this.optionalNum = in.readInt();
        this.selectedNum = in.readInt();
        this.cityList = new ArrayList<CityListBean>();
        in.readList(this.cityList, CityListBean.class.getClassLoader());
        this.select = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.provinceCode);
        dest.writeString(this.provinceName);
        dest.writeInt(this.optionalNum);
        dest.writeInt(this.selectedNum);
        dest.writeList(this.cityList);
        dest.writeByte(this.select ? (byte) 1 : (byte) 0);
    }
}
