package com.hll_sc_app.bean.user;

import android.text.TextUtils;

import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.groupInfo.GroupInfoResp;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/6
 */

public class CertifyReq {
    private String businessEntity;
    private String businessNo;
    private String endTime;
    private String entityIDNo;
    private String frontImg;
    private String groupID = UserConfig.getGroupID();
    private String licencePhotoUrl;
    private String licenseName;
    private List<GroupInfoResp.OtherLicensesBean> otherLicenses;
    private String startTime;
    private transient boolean initial;
    private transient int isCertified;

    public boolean enable() {
        return !TextUtils.isEmpty(businessEntity) &&
                !TextUtils.isEmpty(entityIDNo) &&
                !TextUtils.isEmpty(frontImg) && licenseEnable();
    }

    public boolean licenseEnable() {
        return !TextUtils.isEmpty(businessNo) &&
                !TextUtils.isEmpty(endTime) &&
                !TextUtils.isEmpty(licencePhotoUrl) &&
                !TextUtils.isEmpty(licenseName) &&
                !TextUtils.isEmpty(startTime);
    }

    public void inflate(GroupInfoResp resp) {
        if (!initial) {
            initial = true;
            businessEntity = resp.getBusinessEntity();
            businessNo = resp.getBusinessNo();
            endTime = resp.getEndTime();
            entityIDNo = resp.getEntityIDNo();
            frontImg = resp.getFrontImg();
            licencePhotoUrl = resp.getLicencePhotoUrl();
            licenseName = resp.getLicenseName();
            otherLicenses = resp.getOtherLicenses();
            startTime = resp.getStartTime();
        }
        isCertified = resp.getIsCertified();
    }

    public String getBusinessEntity() {
        return businessEntity;
    }

    public void setBusinessEntity(String businessEntity) {
        this.businessEntity = businessEntity;
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEntityIDNo() {
        return entityIDNo;
    }

    public void setEntityIDNo(String entityIDNo) {
        this.entityIDNo = entityIDNo;
    }

    public String getFrontImg() {
        return frontImg;
    }

    public void setFrontImg(String frontImg) {
        this.frontImg = frontImg;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getLicencePhotoUrl() {
        return licencePhotoUrl;
    }

    public void setLicencePhotoUrl(String licencePhotoUrl) {
        this.licencePhotoUrl = licencePhotoUrl;
    }

    public String getLicenseName() {
        return licenseName;
    }

    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }

    public List<GroupInfoResp.OtherLicensesBean> getOtherLicenses() {
        return otherLicenses;
    }

    public void setOtherLicenses(List<GroupInfoResp.OtherLicensesBean> otherLicenses) {
        this.otherLicenses = otherLicenses;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int isCertified() {
        return isCertified;
    }

    public void setCertified(int certified) {
        isCertified = certified;
    }
}
