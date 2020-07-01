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
    private String companyType;
    private List<CategoryItem> category;

    public RegisterReq() {
    }

    protected RegisterReq(Parcel in) {
        source = in.readInt();
        loginPWD = in.readString();
        checkCode = in.readString();
        loginPhone = in.readString();
        checkLoginPWD = in.readString();
        groupName = in.readString();
        operationGroupID = in.readString();
        licencePhotoUrl = in.readString();
        linkman = in.readString();
        groupProvinceCode = in.readString();
        groupProvince = in.readString();
        groupCityCode = in.readString();
        groupCity = in.readString();
        groupDistrictCode = in.readString();
        groupDistrict = in.readString();
        selectArea = in.readString();
        groupAddress = in.readString();
        companyType = in.readString();
        category = in.createTypedArrayList(CategoryItem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(source);
        dest.writeString(loginPWD);
        dest.writeString(checkCode);
        dest.writeString(loginPhone);
        dest.writeString(checkLoginPWD);
        dest.writeString(groupName);
        dest.writeString(operationGroupID);
        dest.writeString(licencePhotoUrl);
        dest.writeString(linkman);
        dest.writeString(groupProvinceCode);
        dest.writeString(groupProvince);
        dest.writeString(groupCityCode);
        dest.writeString(groupCity);
        dest.writeString(groupDistrictCode);
        dest.writeString(groupDistrict);
        dest.writeString(selectArea);
        dest.writeString(groupAddress);
        dest.writeString(companyType);
        dest.writeTypedList(category);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RegisterReq> CREATOR = new Creator<RegisterReq>() {
        @Override
        public RegisterReq createFromParcel(Parcel in) {
            return new RegisterReq(in);
        }

        @Override
        public RegisterReq[] newArray(int size) {
            return new RegisterReq[size];
        }
    };

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

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public List<CategoryItem> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryItem> category) {
        this.category = category;
    }

}
