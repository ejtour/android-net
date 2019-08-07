package com.hll_sc_app.bean.goods;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;

import java.util.List;

/**
 * 采购商Bean
 *
 * @author zhuyingsong
 * @date 2019-07-04
 */
public class PurchaserBean implements Parcelable {
    public static final Creator<PurchaserBean> CREATOR = new Creator<PurchaserBean>() {
        @Override
        public PurchaserBean createFromParcel(Parcel source) {
            return new PurchaserBean(source);
        }

        @Override
        public PurchaserBean[] newArray(int size) {
            return new PurchaserBean[size];
        }
    };
    private String readStatus;
    private String uniformSocialCreditCode;
    private String unRelationProductNum;
    private String auditBy;
    private String mail;
    private String erpShopName;
    private String relationProductNum;
    private String licenceGroupName;
    private String licencePhotoUrl;
    private String purchaserID;
    private String legalPerson;
    private String plateSupplierID;
    private String action;
    private String groupCity;
    private String erpShopID;
    private String id;
    private String cancelReason;
    private String fax;
    private String auditDate;
    private String groupID;
    private String identityCard;
    private String message;
    private String linkman;
    @SerializedName(value = "name", alternate = {"purchaserName"})
    private String purchaserName;
    private String groupName;
    private String operateModel;
    private String groupProvince;
    private String createTime;
    private String frontImg;
    private String groupAddress;
    private String groupPhone;
    private String shopID;
    private String applyDate;
    private String groupDistrict;
    private String otherLicense;
    private String resourceType;
    private String status;
    private boolean select;
    private List<PurchaserShopBean> shopList;
    private String logoUrl;
    private String shopCount;
    private String newShopNum;
    private String mobile;
    private String groupArea;
    private String shopNum;
    private String groupNum;
    private int businessModel;

    public PurchaserBean() {
    }

    protected PurchaserBean(Parcel in) {
        this.readStatus = in.readString();
        this.uniformSocialCreditCode = in.readString();
        this.unRelationProductNum = in.readString();
        this.auditBy = in.readString();
        this.mail = in.readString();
        this.erpShopName = in.readString();
        this.relationProductNum = in.readString();
        this.licenceGroupName = in.readString();
        this.licencePhotoUrl = in.readString();
        this.purchaserID = in.readString();
        this.legalPerson = in.readString();
        this.plateSupplierID = in.readString();
        this.action = in.readString();
        this.groupCity = in.readString();
        this.erpShopID = in.readString();
        this.id = in.readString();
        this.cancelReason = in.readString();
        this.fax = in.readString();
        this.auditDate = in.readString();
        this.groupID = in.readString();
        this.identityCard = in.readString();
        this.message = in.readString();
        this.linkman = in.readString();
        this.purchaserName = in.readString();
        this.groupName = in.readString();
        this.operateModel = in.readString();
        this.groupProvince = in.readString();
        this.createTime = in.readString();
        this.frontImg = in.readString();
        this.groupAddress = in.readString();
        this.groupPhone = in.readString();
        this.shopID = in.readString();
        this.applyDate = in.readString();
        this.groupDistrict = in.readString();
        this.otherLicense = in.readString();
        this.resourceType = in.readString();
        this.status = in.readString();
        this.select = in.readByte() != 0;
        this.shopList = in.createTypedArrayList(PurchaserShopBean.CREATOR);
        this.logoUrl = in.readString();
        this.shopCount = in.readString();
        this.newShopNum = in.readString();
        this.mobile = in.readString();
        this.groupArea = in.readString();
        this.shopNum = in.readString();
        this.groupNum = in.readString();
        this.businessModel = in.readInt();
    }

    public String getShopNum() {
        return shopNum;
    }

    public void setShopNum(String shopNum) {
        this.shopNum = shopNum;
    }

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }

    public String getGroupArea() {
        return groupArea;
    }

    public void setGroupArea(String groupArea) {
        this.groupArea = groupArea;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getShopCount() {
        return shopCount;
    }

    public void setShopCount(String shopCount) {
        this.shopCount = shopCount;
    }

    public String getNewShopNum() {
        return newShopNum;
    }

    public void setNewShopNum(String newShopNum) {
        this.newShopNum = newShopNum;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getUniformSocialCreditCode() {
        return uniformSocialCreditCode;
    }

    public void setUniformSocialCreditCode(String uniformSocialCreditCode) {
        this.uniformSocialCreditCode = uniformSocialCreditCode;
    }

    public String getUnRelationProductNum() {
        return unRelationProductNum;
    }

    public void setUnRelationProductNum(String unRelationProductNum) {
        this.unRelationProductNum = unRelationProductNum;
    }

    public String getAuditBy() {
        return auditBy;
    }

    public void setAuditBy(String auditBy) {
        this.auditBy = auditBy;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getErpShopName() {
        return erpShopName;
    }

    public void setErpShopName(String erpShopName) {
        this.erpShopName = erpShopName;
    }

    public String getRelationProductNum() {
        return relationProductNum;
    }

    public void setRelationProductNum(String relationProductNum) {
        this.relationProductNum = relationProductNum;
    }

    public String getLicenceGroupName() {
        return licenceGroupName;
    }

    public void setLicenceGroupName(String licenceGroupName) {
        this.licenceGroupName = licenceGroupName;
    }

    public String getLicencePhotoUrl() {
        return licencePhotoUrl;
    }

    public void setLicencePhotoUrl(String licencePhotoUrl) {
        this.licencePhotoUrl = licencePhotoUrl;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getPlateSupplierID() {
        return plateSupplierID;
    }

    public void setPlateSupplierID(String plateSupplierID) {
        this.plateSupplierID = plateSupplierID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getGroupCity() {
        return groupCity;
    }

    public void setGroupCity(String groupCity) {
        this.groupCity = groupCity;
    }

    public String getErpShopID() {
        return erpShopID;
    }

    public void setErpShopID(String erpShopID) {
        this.erpShopID = erpShopID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(String auditDate) {
        this.auditDate = auditDate;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getOperateModel() {
        return operateModel;
    }

    public void setOperateModel(String operateModel) {
        this.operateModel = operateModel;
    }

    public String getGroupProvince() {
        return groupProvince;
    }

    public void setGroupProvince(String groupProvince) {
        this.groupProvince = groupProvince;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getGroupDistrict() {
        return groupDistrict;
    }

    public void setGroupDistrict(String groupDistrict) {
        this.groupDistrict = groupDistrict;
    }

    public String getOtherLicense() {
        return otherLicense;
    }

    public void setOtherLicense(String otherLicense) {
        this.otherLicense = otherLicense;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public List<PurchaserShopBean> getShopList() {
        return shopList;
    }

    public void setShopList(List<PurchaserShopBean> shopList) {
        this.shopList = shopList;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public int getBusinessModel() {
        return businessModel;
    }

    public void setBusinessModel(int businessModel) {
        this.businessModel = businessModel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.readStatus);
        dest.writeString(this.uniformSocialCreditCode);
        dest.writeString(this.unRelationProductNum);
        dest.writeString(this.auditBy);
        dest.writeString(this.mail);
        dest.writeString(this.erpShopName);
        dest.writeString(this.relationProductNum);
        dest.writeString(this.licenceGroupName);
        dest.writeString(this.licencePhotoUrl);
        dest.writeString(this.purchaserID);
        dest.writeString(this.legalPerson);
        dest.writeString(this.plateSupplierID);
        dest.writeString(this.action);
        dest.writeString(this.groupCity);
        dest.writeString(this.erpShopID);
        dest.writeString(this.id);
        dest.writeString(this.cancelReason);
        dest.writeString(this.fax);
        dest.writeString(this.auditDate);
        dest.writeString(this.groupID);
        dest.writeString(this.identityCard);
        dest.writeString(this.message);
        dest.writeString(this.linkman);
        dest.writeString(this.purchaserName);
        dest.writeString(this.groupName);
        dest.writeString(this.operateModel);
        dest.writeString(this.groupProvince);
        dest.writeString(this.createTime);
        dest.writeString(this.frontImg);
        dest.writeString(this.groupAddress);
        dest.writeString(this.groupPhone);
        dest.writeString(this.shopID);
        dest.writeString(this.applyDate);
        dest.writeString(this.groupDistrict);
        dest.writeString(this.otherLicense);
        dest.writeString(this.resourceType);
        dest.writeString(this.status);
        dest.writeByte(this.select ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.shopList);
        dest.writeString(this.logoUrl);
        dest.writeString(this.shopCount);
        dest.writeString(this.newShopNum);
        dest.writeString(this.mobile);
        dest.writeString(this.groupArea);
        dest.writeString(this.shopNum);
        dest.writeString(this.groupNum);
        dest.writeInt(this.businessModel);
    }
}
