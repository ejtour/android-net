package com.hll_sc_app.base.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 登录接口返回的店铺信息列表
 *
 * @author zhuyingsong
 * @date 20190604
 */
@Entity
public class UserShop {
    /**
     * 店铺logo
     */
    private String logoUrl;
    /**
     * 城市名称
     */
    private String shopCity;
    /**
     * 城市编码
     */
    private String shopCityCode;
    /**
     * 地区名称
     */
    private String shopDistrict;
    /**
     * 地区编码
     */
    private String shopDistrictCode;
    /**
     * 店铺ID
     */
    @Id
    private String shopID;
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 省份名称
     */
    private String shopProvince;
    /**
     * 省份编码
     */
    private String shopProvinceCode;

    @Generated(hash = 1902275090)
    public UserShop(String logoUrl, String shopCity, String shopCityCode,
                    String shopDistrict, String shopDistrictCode, String shopID,
                    String shopName, String shopProvince, String shopProvinceCode) {
        this.logoUrl = logoUrl;
        this.shopCity = shopCity;
        this.shopCityCode = shopCityCode;
        this.shopDistrict = shopDistrict;
        this.shopDistrictCode = shopDistrictCode;
        this.shopID = shopID;
        this.shopName = shopName;
        this.shopProvince = shopProvince;
        this.shopProvinceCode = shopProvinceCode;
    }

    @Generated(hash = 100718418)
    public UserShop() {
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getShopCity() {
        return shopCity;
    }

    public void setShopCity(String shopCity) {
        this.shopCity = shopCity;
    }

    public String getShopCityCode() {
        return shopCityCode;
    }

    public void setShopCityCode(String shopCityCode) {
        this.shopCityCode = shopCityCode;
    }

    public String getShopDistrict() {
        return shopDistrict;
    }

    public void setShopDistrict(String shopDistrict) {
        this.shopDistrict = shopDistrict;
    }

    public String getShopDistrictCode() {
        return shopDistrictCode;
    }

    public void setShopDistrictCode(String shopDistrictCode) {
        this.shopDistrictCode = shopDistrictCode;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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
}
