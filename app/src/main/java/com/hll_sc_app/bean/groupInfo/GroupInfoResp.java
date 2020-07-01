package com.hll_sc_app.bean.groupInfo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 集团信息响应
 * @author zc
 */
public class GroupInfoResp implements Parcelable{

    /**
     * 0-未提交审核,1-待审核,2-已通过,3-未通过
     */
    public static final int NOTCERTIFY = 0;
    public static final int CERTIFYING = 1;
    public static final int PASS = 2;
    public static final int REJECT = 3;

    private String groupCityCode;
    private String reason;
    private String examInfo;
    private String entityIDNo;
    private int isActive;
    private String licencePhotoUrl;
    private String onLineTime;
    private int operationModel;
    private int action;
    private String groupCity;
    private String certifyReason;
    private String groupProvinceCode;
    private String fax;
    private String brandName;
    private int wareHourseStatus;
    private int isCertified;
    private String groupID;
    private String licenseName;
    private String linkman;
    private boolean receiptOnly;
    private String extGroupID;
    private String groupName;
    private String groupProvince;
    private String frontImg;
    private String groupAddress;
    private String groupPhone;
    private String operationGroupID;
    private String groupDistrict;
    private String groupArea;
    private String actionTime;
    private int groupType;
    private int businessModel;
    private int isOnline;
    private String businessEntity;
    private String groupMail;
    private String businessNo;
    private String createby;
    private String groupLogoUrl;
    private String operationGroupName;
    private String startTime;
    private String actionBy;
    private String mobile;
    private int isTestData;
    private String createTime;
    private String extData;
    private String endTime;
    private int isSelfOperated;
    private String groupDistrictCode;
    private int resourceType;
    private List<OtherLicensesBean> otherLicenses;

    private String supplierShopName;
    private String companyType;

    protected GroupInfoResp(Parcel in) {
        groupCityCode = in.readString();
        reason = in.readString();
        examInfo = in.readString();
        entityIDNo = in.readString();
        isActive = in.readInt();
        licencePhotoUrl = in.readString();
        onLineTime = in.readString();
        operationModel = in.readInt();
        action = in.readInt();
        groupCity = in.readString();
        certifyReason = in.readString();
        groupProvinceCode = in.readString();
        fax = in.readString();
        brandName = in.readString();
        wareHourseStatus = in.readInt();
        isCertified = in.readInt();
        groupID = in.readString();
        licenseName = in.readString();
        linkman = in.readString();
        receiptOnly = in.readByte() != 0;
        extGroupID = in.readString();
        groupName = in.readString();
        groupProvince = in.readString();
        frontImg = in.readString();
        groupAddress = in.readString();
        groupPhone = in.readString();
        operationGroupID = in.readString();
        groupDistrict = in.readString();
        groupArea = in.readString();
        actionTime = in.readString();
        groupType = in.readInt();
        businessModel = in.readInt();
        isOnline = in.readInt();
        businessEntity = in.readString();
        groupMail = in.readString();
        businessNo = in.readString();
        createby = in.readString();
        groupLogoUrl = in.readString();
        operationGroupName = in.readString();
        startTime = in.readString();
        actionBy = in.readString();
        mobile = in.readString();
        isTestData = in.readInt();
        createTime = in.readString();
        extData = in.readString();
        endTime = in.readString();
        isSelfOperated = in.readInt();
        groupDistrictCode = in.readString();
        resourceType = in.readInt();
        otherLicenses = in.createTypedArrayList(OtherLicensesBean.CREATOR);
        supplierShopName = in.readString();
        companyType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(groupCityCode);
        dest.writeString(reason);
        dest.writeString(examInfo);
        dest.writeString(entityIDNo);
        dest.writeInt(isActive);
        dest.writeString(licencePhotoUrl);
        dest.writeString(onLineTime);
        dest.writeInt(operationModel);
        dest.writeInt(action);
        dest.writeString(groupCity);
        dest.writeString(certifyReason);
        dest.writeString(groupProvinceCode);
        dest.writeString(fax);
        dest.writeString(brandName);
        dest.writeInt(wareHourseStatus);
        dest.writeInt(isCertified);
        dest.writeString(groupID);
        dest.writeString(licenseName);
        dest.writeString(linkman);
        dest.writeByte((byte) (receiptOnly ? 1 : 0));
        dest.writeString(extGroupID);
        dest.writeString(groupName);
        dest.writeString(groupProvince);
        dest.writeString(frontImg);
        dest.writeString(groupAddress);
        dest.writeString(groupPhone);
        dest.writeString(operationGroupID);
        dest.writeString(groupDistrict);
        dest.writeString(groupArea);
        dest.writeString(actionTime);
        dest.writeInt(groupType);
        dest.writeInt(businessModel);
        dest.writeInt(isOnline);
        dest.writeString(businessEntity);
        dest.writeString(groupMail);
        dest.writeString(businessNo);
        dest.writeString(createby);
        dest.writeString(groupLogoUrl);
        dest.writeString(operationGroupName);
        dest.writeString(startTime);
        dest.writeString(actionBy);
        dest.writeString(mobile);
        dest.writeInt(isTestData);
        dest.writeString(createTime);
        dest.writeString(extData);
        dest.writeString(endTime);
        dest.writeInt(isSelfOperated);
        dest.writeString(groupDistrictCode);
        dest.writeInt(resourceType);
        dest.writeTypedList(otherLicenses);
        dest.writeString(supplierShopName);
        dest.writeString(companyType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GroupInfoResp> CREATOR = new Creator<GroupInfoResp>() {
        @Override
        public GroupInfoResp createFromParcel(Parcel in) {
            return new GroupInfoResp(in);
        }

        @Override
        public GroupInfoResp[] newArray(int size) {
            return new GroupInfoResp[size];
        }
    };

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getSupplierShopName() {
        return supplierShopName;
    }

    public void setSupplierShopName(String supplierShopName) {
        this.supplierShopName = supplierShopName;
    }

    public String getGroupCityCode() {
        return groupCityCode;
    }

    public void setGroupCityCode(String groupCityCode) {
        this.groupCityCode = groupCityCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getExamInfo() {
        return examInfo;
    }

    public void setExamInfo(String examInfo) {
        this.examInfo = examInfo;
    }

    public String getEntityIDNo() {
        return entityIDNo;
    }

    public void setEntityIDNo(String entityIDNo) {
        this.entityIDNo = entityIDNo;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getLicencePhotoUrl() {
        return licencePhotoUrl;
    }

    public void setLicencePhotoUrl(String licencePhotoUrl) {
        this.licencePhotoUrl = licencePhotoUrl;
    }

    public String getOnLineTime() {
        return onLineTime;
    }

    public void setOnLineTime(String onLineTime) {
        this.onLineTime = onLineTime;
    }

    public int getOperationModel() {
        return operationModel;
    }

    public void setOperationModel(int operationModel) {
        this.operationModel = operationModel;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getGroupCity() {
        return groupCity;
    }

    public void setGroupCity(String groupCity) {
        this.groupCity = groupCity;
    }

    public String getCertifyReason() {
        return certifyReason;
    }

    public void setCertifyReason(String certifyReason) {
        this.certifyReason = certifyReason;
    }

    public String getGroupProvinceCode() {
        return groupProvinceCode;
    }

    public void setGroupProvinceCode(String groupProvinceCode) {
        this.groupProvinceCode = groupProvinceCode;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getWareHourseStatus() {
        return wareHourseStatus;
    }

    public void setWareHourseStatus(int wareHourseStatus) {
        this.wareHourseStatus = wareHourseStatus;
    }

    public int getIsCertified() {
        return isCertified;
    }

    public void setIsCertified(int isCertified) {
        this.isCertified = isCertified;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getLicenseName() {
        return licenseName;
    }

    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public boolean isReceiptOnly() {
        return receiptOnly;
    }

    public void setReceiptOnly(boolean receiptOnly) {
        this.receiptOnly = receiptOnly;
    }

    public String getExtGroupID() {
        return extGroupID;
    }

    public void setExtGroupID(String extGroupID) {
        this.extGroupID = extGroupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupProvince() {
        return groupProvince;
    }

    public void setGroupProvince(String groupProvince) {
        this.groupProvince = groupProvince;
    }

    public String getFrontImg() {
        return frontImg;
    }

    public void setFrontImg(String frontImg) {
        this.frontImg = frontImg;
    }

    public String getGroupAddress() {
        return groupAddress;
    }

    public void setGroupAddress(String groupAddress) {
        this.groupAddress = groupAddress;
    }

    public String getGroupPhone() {
        return groupPhone;
    }

    public void setGroupPhone(String groupPhone) {
        this.groupPhone = groupPhone;
    }

    public String getOperationGroupID() {
        return operationGroupID;
    }

    public void setOperationGroupID(String operationGroupID) {
        this.operationGroupID = operationGroupID;
    }

    public String getGroupDistrict() {
        return groupDistrict;
    }

    public void setGroupDistrict(String groupDistrict) {
        this.groupDistrict = groupDistrict;
    }

    public String getGroupArea() {
        return groupArea;
    }

    public void setGroupArea(String groupArea) {
        this.groupArea = groupArea;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    public int getBusinessModel() {
        return businessModel;
    }

    public void setBusinessModel(int businessModel) {
        this.businessModel = businessModel;
    }

    public int getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }

    public String getBusinessEntity() {
        return businessEntity;
    }

    public void setBusinessEntity(String businessEntity) {
        this.businessEntity = businessEntity;
    }

    public String getGroupMail() {
        return groupMail;
    }

    public void setGroupMail(String groupMail) {
        this.groupMail = groupMail;
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public String getGroupLogoUrl() {
        return groupLogoUrl;
    }

    public void setGroupLogoUrl(String groupLogoUrl) {
        this.groupLogoUrl = groupLogoUrl;
    }

    public String getOperationGroupName() {
        return operationGroupName;
    }

    public void setOperationGroupName(String operationGroupName) {
        this.operationGroupName = operationGroupName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getIsTestData() {
        return isTestData;
    }

    public void setIsTestData(int isTestData) {
        this.isTestData = isTestData;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getExtData() {
        return extData;
    }

    public void setExtData(String extData) {
        this.extData = extData;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getIsSelfOperated() {
        return isSelfOperated;
    }

    public void setIsSelfOperated(int isSelfOperated) {
        this.isSelfOperated = isSelfOperated;
    }

    public String getGroupDistrictCode() {
        return groupDistrictCode;
    }

    public void setGroupDistrictCode(String groupDistrictCode) {
        this.groupDistrictCode = groupDistrictCode;
    }

    public int getResourceType() {
        return resourceType;
    }

    public void setResourceType(int resourceType) {
        this.resourceType = resourceType;
    }

    public List<OtherLicensesBean> getOtherLicenses() {
        return otherLicenses;
    }

    public void setOtherLicenses(List<OtherLicensesBean> otherLicenses) {
        this.otherLicenses = otherLicenses;
    }

    public static class OtherLicensesBean implements Parcelable{

        private int otherLicenseType;
        private String otherLicenseName;

        public int getOtherLicenseType() {
            return otherLicenseType;
        }

        public void setOtherLicenseType(int otherLicenseType) {
            this.otherLicenseType = otherLicenseType;
        }

        public String getOtherLicenseName() {
            return otherLicenseName;
        }

        public void setOtherLicenseName(String otherLicenseName) {
            this.otherLicenseName = otherLicenseName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.otherLicenseType);
            dest.writeString(this.otherLicenseName);
        }

        public OtherLicensesBean() {
        }

        protected OtherLicensesBean(Parcel in) {
            this.otherLicenseType = in.readInt();
            this.otherLicenseName = in.readString();
        }

        public static final Creator<OtherLicensesBean> CREATOR = new Creator<OtherLicensesBean>() {
            @Override
            public OtherLicensesBean createFromParcel(Parcel source) {
                return new OtherLicensesBean(source);
            }

            @Override
            public OtherLicensesBean[] newArray(int size) {
                return new OtherLicensesBean[size];
            }
        };
    }

    public GroupInfoResp() {
    }

}
