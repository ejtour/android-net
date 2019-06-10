package com.hll_sc_app.bean.user;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 注册请求参数
 *
 * @author zhuyingsong on 2019-06-06
 * @date 2019-06-06
 */
public class RegisterReq implements Parcelable {
    /**
     * 0-注册页面,1-补全信息页面
     */
    private int source;
    private String loginPWD;
    private String checkCode;
    private String loginPhone;
    private String checkLoginPWD;
    private String groupName;
    private String operationGroupID;
    private String licencePhotoUrl;
    private String linkman;
    private String groupProvinceCode;
    private String groupProvince;
    private String groupCityCode;
    private String groupCity;
    private String groupDistrictCode;
    private String groupDistrict;
    private String selectArea;
    private String groupAddress;
    public static final Parcelable.Creator<RegisterReq> CREATOR = new Parcelable.Creator<RegisterReq>() {
        @Override
        public RegisterReq createFromParcel(Parcel source) {
            return new RegisterReq(source);
        }

        @Override
        public RegisterReq[] newArray(int size) {
            return new RegisterReq[size];
        }
    };
    private List<CategoryItem> category;

    public RegisterReq() {
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getLoginPWD() {
        return loginPWD;
    }

    public void setLoginPWD(String loginPWD) {
        this.loginPWD = loginPWD;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public String getLoginPhone() {
        return loginPhone;
    }

    public void setLoginPhone(String loginPhone) {
        this.loginPhone = loginPhone;
    }

    public String getCheckLoginPWD() {
        return checkLoginPWD;
    }

    public void setCheckLoginPWD(String checkLoginPWD) {
        this.checkLoginPWD = checkLoginPWD;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getOperationGroupID() {
        return operationGroupID;
    }

    public void setOperationGroupID(String operationGroupID) {
        this.operationGroupID = operationGroupID;
    }

    public String getLicencePhotoUrl() {
        return licencePhotoUrl;
    }

    public void setLicencePhotoUrl(String licencePhotoUrl) {
        this.licencePhotoUrl = licencePhotoUrl;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getGroupProvinceCode() {
        return groupProvinceCode;
    }

    public void setGroupProvinceCode(String groupProvinceCode) {
        this.groupProvinceCode = groupProvinceCode;
    }

    public String getGroupProvince() {
        return groupProvince;
    }

    public void setGroupProvince(String groupProvince) {
        this.groupProvince = groupProvince;
    }

    public String getGroupCityCode() {
        return groupCityCode;
    }

    public void setGroupCityCode(String groupCityCode) {
        this.groupCityCode = groupCityCode;
    }

    public String getGroupCity() {
        return groupCity;
    }

    public void setGroupCity(String groupCity) {
        this.groupCity = groupCity;
    }

    public String getGroupDistrictCode() {
        return groupDistrictCode;
    }

    public void setGroupDistrictCode(String groupDistrictCode) {
        this.groupDistrictCode = groupDistrictCode;
    }

    public String getGroupDistrict() {
        return groupDistrict;
    }

    public void setGroupDistrict(String groupDistrict) {
        this.groupDistrict = groupDistrict;
    }

    public String getSelectArea() {
        return selectArea;
    }

    public void setSelectArea(String selectArea) {
        this.selectArea = selectArea;
    }

    public String getGroupAddress() {
        return groupAddress;
    }

    public void setGroupAddress(String groupAddress) {
        this.groupAddress = groupAddress;
    }

    protected RegisterReq(Parcel in) {
        this.source = in.readInt();
        this.loginPWD = in.readString();
        this.checkCode = in.readString();
        this.loginPhone = in.readString();
        this.checkLoginPWD = in.readString();
        this.groupName = in.readString();
        this.operationGroupID = in.readString();
        this.licencePhotoUrl = in.readString();
        this.linkman = in.readString();
        this.groupProvinceCode = in.readString();
        this.groupProvince = in.readString();
        this.groupCityCode = in.readString();
        this.groupCity = in.readString();
        this.groupDistrictCode = in.readString();
        this.groupDistrict = in.readString();
        this.selectArea = in.readString();
        this.groupAddress = in.readString();
        this.category = in.createTypedArrayList(CategoryItem.CREATOR);
    }

    public List<CategoryItem> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryItem> category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.source);
        dest.writeString(this.loginPWD);
        dest.writeString(this.checkCode);
        dest.writeString(this.loginPhone);
        dest.writeString(this.checkLoginPWD);
        dest.writeString(this.groupName);
        dest.writeString(this.operationGroupID);
        dest.writeString(this.licencePhotoUrl);
        dest.writeString(this.linkman);
        dest.writeString(this.groupProvinceCode);
        dest.writeString(this.groupProvince);
        dest.writeString(this.groupCityCode);
        dest.writeString(this.groupCity);
        dest.writeString(this.groupDistrictCode);
        dest.writeString(this.groupDistrict);
        dest.writeString(this.selectArea);
        dest.writeString(this.groupAddress);
        dest.writeTypedList(this.category);
    }
}
