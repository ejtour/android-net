package com.hll_sc_app.bean.report.voucherconfirm;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/9/23
 */
public class VoucherGroupBean implements Parcelable {
    private String extGroupID;
    private String purchaserName;
    private int num;
    private String startDate;
    private String endDate;

    protected VoucherGroupBean(Parcel in) {
        extGroupID = in.readString();
        purchaserName = in.readString();
        num = in.readInt();
        startDate = in.readString();
        endDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(extGroupID);
        dest.writeString(purchaserName);
        dest.writeInt(num);
        dest.writeString(startDate);
        dest.writeString(endDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VoucherGroupBean> CREATOR = new Creator<VoucherGroupBean>() {
        @Override
        public VoucherGroupBean createFromParcel(Parcel in) {
            return new VoucherGroupBean(in);
        }

        @Override
        public VoucherGroupBean[] newArray(int size) {
            return new VoucherGroupBean[size];
        }
    };

    public String getExtGroupID() {
        return extGroupID;
    }

    public void setExtGroupID(String extGroupID) {
        this.extGroupID = extGroupID;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
