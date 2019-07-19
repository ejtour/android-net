package com.hll_sc_app.bean.cooperation;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * OtherLicensesBean
 *
 * @author zhuyingsong
 * @date 2019-07-17
 */
public class OtherLicensesBean implements Parcelable {
    private String otherLicenseType;
    private String otherLicenseName;

    public String getOtherLicenseType() {
        return otherLicenseType;
    }

    public void setOtherLicenseType(String otherLicenseType) {
        this.otherLicenseType = otherLicenseType;
    }

    public String getOtherLicenseName() {
        return otherLicenseName;
    }

    public void setOtherLicenseName(String otherLicenseName) {
        this.otherLicenseName = otherLicenseName;
    }

    public static final Parcelable.Creator<OtherLicensesBean> CREATOR = new Parcelable.Creator<OtherLicensesBean>() {
        @Override
        public OtherLicensesBean createFromParcel(Parcel source) {
            return new OtherLicensesBean(source);
        }

        @Override
        public OtherLicensesBean[] newArray(int size) {
            return new OtherLicensesBean[size];
        }
    };

    public OtherLicensesBean() {
    }

    protected OtherLicensesBean(Parcel in) {
        this.otherLicenseType = in.readString();
        this.otherLicenseName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.otherLicenseType);
        dest.writeString(this.otherLicenseName);
    }
}
