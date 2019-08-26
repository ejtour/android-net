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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.groupCityCode);
        dest.writeString(this.reason);
        dest.writeString(this.examInfo);
        dest.writeString(this.entityIDNo);
        dest.writeInt(this.isActive);
        dest.writeString(this.licencePhotoUrl);
        dest.writeString(this.onLineTime);
        dest.writeInt(this.operationModel);
        dest.writeInt(this.action);
        dest.writeString(this.groupCity);
        dest.writeString(this.certifyReason);
        dest.writeString(this.groupProvinceCode);
        dest.writeString(this.fax);
        dest.writeString(this.brandName);
        dest.writeInt(this.wareHourseStatus);
        dest.writeInt(this.isCertified);
        dest.writeString(this.groupID);
        dest.writeString(this.licenseName);
        dest.writeString(this.linkman);
        dest.writeByte(this.receiptOnly ? (byte) 1 : (byte) 0);
        dest.writeString(this.extGroupID);
        dest.writeString(this.groupName);
        dest.writeString(this.groupProvince);
        dest.writeString(this.frontImg);
        dest.writeString(this.groupAddress);
        dest.writeString(this.groupPhone);
        dest.writeString(this.operationGroupID);
        dest.writeString(this.groupDistrict);
        dest.writeString(this.groupArea);
        dest.writeString(this.actionTime);
        dest.writeInt(this.groupType);
        dest.writeInt(this.businessModel);
        dest.writeInt(this.isOnline);
        dest.writeString(this.businessEntity);
        dest.writeString(this.groupMail);
        dest.writeString(this.businessNo);
        dest.writeString(this.createby);
        dest.writeString(this.groupLogoUrl);
        dest.writeString(this.operationGroupName);
        dest.writeString(this.startTime);
        dest.writeString(this.actionBy);
        dest.writeString(this.mobile);
        dest.writeInt(this.isTestData);
        dest.writeString(this.createTime);
        dest.writeString(this.extData);
        dest.writeString(this.endTime);
        dest.writeInt(this.isSelfOperated);
        dest.writeString(this.groupDistrictCode);
        dest.writeInt(this.resourceType);
        dest.writeTypedList(this.otherLicenses);
        dest.writeString(this.supplierShopName);
    }

    protected GroupInfoResp(Parcel in) {
        this.groupCityCode = in.readString();
        this.reason = in.readString();
        this.examInfo = in.readString();
        this.entityIDNo = in.readString();
        this.isActive = in.readInt();
        this.licencePhotoUrl = in.readString();
        this.onLineTime = in.readString();
        this.operationModel = in.readInt();
        this.action = in.readInt();
        this.groupCity = in.readString();
        this.certifyReason = in.readString();
        this.groupProvinceCode = in.readString();
        this.fax = in.readString();
        this.brandName = in.readString();
        this.wareHourseStatus = in.readInt();
        this.isCertified = in.readInt();
        this.groupID = in.readString();
        this.licenseName = in.readString();
        this.linkman = in.readString();
        this.receiptOnly = in.readByte() != 0;
        this.extGroupID = in.readString();
        this.groupName = in.readString();
        this.groupProvince = in.readString();
        this.frontImg = in.readString();
        this.groupAddress = in.readString();
        this.groupPhone = in.readString();
        this.operationGroupID = in.readString();
        this.groupDistrict = in.readString();
        this.groupArea = in.readString();
        this.actionTime = in.readString();
        this.groupType = in.readInt();
        this.businessModel = in.readInt();
        this.isOnline = in.readInt();
        this.businessEntity = in.readString();
        this.groupMail = in.readString();
        this.businessNo = in.readString();
        this.createby = in.readString();
        this.groupLogoUrl = in.readString();
        this.operationGroupName = in.readString();
        this.startTime = in.readString();
        this.actionBy = in.readString();
        this.mobile = in.readString();
        this.isTestData = in.readInt();
        this.createTime = in.readString();
        this.extData = in.readString();
        this.endTime = in.readString();
        this.isSelfOperated = in.readInt();
        this.groupDistrictCode = in.readString();
        this.resourceType = in.readInt();
        this.otherLicenses = in.createTypedArrayList(OtherLicensesBean.CREATOR);
        this.supplierShopName = in.readString();
    }

    public static final Creator<GroupInfoResp> CREATOR = new Creator<GroupInfoResp>() {
        @Override
        public GroupInfoResp createFromParcel(Parcel source) {
            return new GroupInfoResp(source);
        }

        @Override
        public GroupInfoResp[] newArray(int size) {
            return new GroupInfoResp[size];
        }
    };
}
