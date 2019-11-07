package com.hll_sc_app.app.info.license;

import android.os.Parcel;
import android.os.Parcelable;

import com.hll_sc_app.bean.groupInfo.GroupInfoResp;
import com.hll_sc_app.bean.user.CertifyReq;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/7
 */

public class InfoLicenseParam implements Parcelable {
    public static final Creator<InfoLicenseParam> CREATOR = new Creator<InfoLicenseParam>() {
        @Override
        public InfoLicenseParam createFromParcel(Parcel in) {
            return new InfoLicenseParam(in);
        }

        @Override
        public InfoLicenseParam[] newArray(int size) {
            return new InfoLicenseParam[size];
        }
    };
    private String businessNo;
    private String startTime;
    private String endTime;
    private String licenseName;
    private String licencePhotoUrl;
    private boolean editable;

    private InfoLicenseParam(String businessNo, String startTime, String endTime, String licenseName, String licencePhotoUrl, boolean editable) {
        this.businessNo = businessNo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.licenseName = licenseName;
        this.licencePhotoUrl = licencePhotoUrl;
        this.editable = editable;
    }

    protected InfoLicenseParam(Parcel in) {
        businessNo = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        licenseName = in.readString();
        licencePhotoUrl = in.readString();
        editable = in.readByte() != 0;
    }

    public static InfoLicenseParam createFromCertifyReq(CertifyReq req) {
        return new InfoLicenseParam(req.getBusinessNo(), req.getStartTime(), req.getEndTime(),
                req.getLicenseName(), req.getLicencePhotoUrl(), req.isCertified() != GroupInfoResp.PASS);
    }

    public void inflateToCertifyReq(CertifyReq req) {
        req.setStartTime(startTime);
        req.setEndTime(endTime);
        req.setBusinessNo(businessNo);
        req.setLicenseName(licenseName);
        req.setLicencePhotoUrl(licencePhotoUrl);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(businessNo);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(licenseName);
        dest.writeString(licencePhotoUrl);
        dest.writeByte((byte) (editable ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLicenseName() {
        return licenseName;
    }

    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }

    public String getLicencePhotoUrl() {
        return licencePhotoUrl;
    }

    public void setLicencePhotoUrl(String licencePhotoUrl) {
        this.licencePhotoUrl = licencePhotoUrl;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
