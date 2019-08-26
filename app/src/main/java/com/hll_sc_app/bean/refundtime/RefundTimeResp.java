package com.hll_sc_app.bean.refundtime;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class RefundTimeResp implements Parcelable {
    private List<RefundTimeBean> records;

    private Integer level;

    public List<RefundTimeBean> getRecords() {
        return records;
    }

    public void setRecords(List<RefundTimeBean> records) {
        this.records = records;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.records);
        dest.writeValue(this.level);
    }

    public RefundTimeResp() {
    }

    protected RefundTimeResp(Parcel in) {
        this.records = new ArrayList<RefundTimeBean>();
        in.readList(this.records, RefundTimeBean.class.getClassLoader());
        this.level = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<RefundTimeResp> CREATOR = new Creator<RefundTimeResp>() {
        @Override
        public RefundTimeResp createFromParcel(Parcel source) {
            return new RefundTimeResp(source);
        }

        @Override
        public RefundTimeResp[] newArray(int size) {
            return new RefundTimeResp[size];
        }
    };
}
