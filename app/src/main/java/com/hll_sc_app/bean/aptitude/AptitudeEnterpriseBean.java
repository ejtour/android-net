package com.hll_sc_app.bean.aptitude;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/28
 */

public class AptitudeEnterpriseBean implements Parcelable {
    private String aptitudeName;
    private String aptitudeType;
    private String aptitudeUrl;
    private String endTime;
    private String groupID;

    public AptitudeEnterpriseBean() {
    }

    protected AptitudeEnterpriseBean(Parcel in) {
        aptitudeName = in.readString();
        aptitudeType = in.readString();
        aptitudeUrl = in.readString();
        endTime = in.readString();
        groupID = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(aptitudeName);
        dest.writeString(aptitudeType);
        dest.writeString(aptitudeUrl);
        dest.writeString(endTime);
        dest.writeString(groupID);
    }

    public AptitudeEnterpriseBean deepCopy() {
        Parcel parcel = Parcel.obtain();
        writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        return CREATOR.createFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AptitudeEnterpriseBean> CREATOR = new Creator<AptitudeEnterpriseBean>() {
        @Override
        public AptitudeEnterpriseBean createFromParcel(Parcel in) {
            return new AptitudeEnterpriseBean(in);
        }

        @Override
        public AptitudeEnterpriseBean[] newArray(int size) {
            return new AptitudeEnterpriseBean[size];
        }
    };

    public String getAptitudeName() {
        return aptitudeName;
    }

    public void setAptitudeName(String aptitudeName) {
        this.aptitudeName = aptitudeName;
    }

    public String getAptitudeType() {
        return aptitudeType;
    }

    public void setAptitudeType(String aptitudeType) {
        this.aptitudeType = aptitudeType;
    }

    public String getAptitudeUrl() {
        return aptitudeUrl;
    }

    public void setAptitudeUrl(String aptitudeUrl) {
        this.aptitudeUrl = aptitudeUrl;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
}
