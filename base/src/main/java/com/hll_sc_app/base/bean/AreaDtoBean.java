package com.hll_sc_app.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 省市区选择
 *
 * @author zhuyingsong
 * @date 2019-06-06
 */
public class AreaDtoBean implements Parcelable {

    public static final Creator<AreaDtoBean> CREATOR = new Creator<AreaDtoBean>() {
        @Override
        public AreaDtoBean createFromParcel(Parcel source) {
            return new AreaDtoBean(source);
        }

        @Override
        public AreaDtoBean[] newArray(int size) {
            return new AreaDtoBean[size];
        }
    };
    private String shopCity;
    private String shopDistrict;
    private String shopCityCode;
    private String shopDistrictCode;
    private String shopProvince;
    private String shopProvinceCode;

    public AreaDtoBean() {
    }

    protected AreaDtoBean(Parcel in) {
        this.shopCity = in.readString();
        this.shopDistrict = in.readString();
        this.shopCityCode = in.readString();
        this.shopDistrictCode = in.readString();
        this.shopProvince = in.readString();
        this.shopProvinceCode = in.readString();
    }

    public String getShopCity() {
        return shopCity;
    }

    public void setShopCity(String shopCity) {
        this.shopCity = shopCity;
    }

    public String getShopDistrict() {
        return shopDistrict;
    }

    public void setShopDistrict(String shopDistrict) {
        this.shopDistrict = shopDistrict;
    }

    public String getShopCityCode() {
        return shopCityCode;
    }

    public void setShopCityCode(String shopCityCode) {
        this.shopCityCode = shopCityCode;
    }

    public String getShopDistrictCode() {
        return shopDistrictCode;
    }

    public void setShopDistrictCode(String shopDistrictCode) {
        this.shopDistrictCode = shopDistrictCode;
    }

    public String getShopProvince() {
        return shopProvince;
    }

    public void setShopProvince(String shopProvince) {
        this.shopProvince = shopProvince;
    }

    public String getShopProvinceCode() {
        return shopProvinceCode;
    }

    public void setShopProvinceCode(String shopProvinceCode) {
        this.shopProvinceCode = shopProvinceCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.shopCity);
        dest.writeString(this.shopDistrict);
        dest.writeString(this.shopCityCode);
        dest.writeString(this.shopDistrictCode);
        dest.writeString(this.shopProvince);
        dest.writeString(this.shopProvinceCode);
    }
}