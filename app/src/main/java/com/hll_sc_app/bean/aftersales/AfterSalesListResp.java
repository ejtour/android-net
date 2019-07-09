package com.hll_sc_app.bean.aftersales;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/8
 */

public class AfterSalesListResp implements Parcelable {
    private int total;
    private List<AfterSalesBean> records;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<AfterSalesBean> getRecords() {
        return records;
    }

    public void setRecords(List<AfterSalesBean> records) {
        this.records = records;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.total);
        dest.writeTypedList(this.records);
    }

    public AfterSalesListResp() {
    }

    protected AfterSalesListResp(Parcel in) {
        this.total = in.readInt();
        this.records = in.createTypedArrayList(AfterSalesBean.CREATOR);
    }

    public static final Creator<AfterSalesListResp> CREATOR = new Creator<AfterSalesListResp>() {
        @Override
        public AfterSalesListResp createFromParcel(Parcel source) {
            return new AfterSalesListResp(source);
        }

        @Override
        public AfterSalesListResp[] newArray(int size) {
            return new AfterSalesListResp[size];
        }
    };
}
