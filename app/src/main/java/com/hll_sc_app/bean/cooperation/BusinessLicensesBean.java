package com.hll_sc_app.bean.cooperation;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * BusinessLicensesBean
 *
 * @author zhuyingsong
 * @date 2019-07-17
 */
public class BusinessLicensesBean implements Parcelable {
    public static final Parcelable.Creator<BusinessLicensesBean> CREATOR =
        new Parcelable.Creator<BusinessLicensesBean>() {
            @Override
            public BusinessLicensesBean createFromParcel(Parcel source) {
                return new BusinessLicensesBean(source);
            }

            @Override
            public BusinessLicensesBean[] newArray(int size) {
                return new BusinessLicensesBean[size];
            }
        };
    private String businessNo;
    private String endTime;
    private String licencePhotoUrl;
    private String licenseName;
    private String startTime;

    public BusinessLicensesBean() {
    }

    protected BusinessLicensesBean(Parcel in) {
        this.businessNo = in.readString();
        this.endTime = in.readString();
        this.licencePhotoUrl = in.readString();
        this.licenseName = in.readString();
        this.startTime = in.readString();
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.businessNo);
        dest.writeString(this.endTime);
        dest.writeString(this.licencePhotoUrl);
        dest.writeString(this.licenseName);
        dest.writeString(this.startTime);
    }
}
